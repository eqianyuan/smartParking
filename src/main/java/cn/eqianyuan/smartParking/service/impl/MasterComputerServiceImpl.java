package cn.eqianyuan.smartParking.service.impl;

import cn.eqianyuan.smartParking.common.Page;
import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.request.masterComputer.MasterComputerRequest;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.vo.MasterComputerVo;
import cn.eqianyuan.smartParking.common.util.YamlForMapHandleUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.DataMap;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import cn.eqianyuan.smartParking.dao.IMasterComputerDao;
import cn.eqianyuan.smartParking.entity.MasterComputer;
import cn.eqianyuan.smartParking.service.IMasterComputerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 上位机设备业务具体实现类
 * Created by jason on 2016-06-20.
 */
@Service
public class MasterComputerServiceImpl implements IMasterComputerService {

    @Autowired
    private IMasterComputerDao masterComputerDao;

    private Logger logger = Logger.getLogger(this.getClass());

    //上位机名称内容最大字节长度
    private static final int MASTER_COMPUTER_NAME_CONTENT_MAX_LENGTH = 100;

    /**
     * 根据分页条件获取上位机数据列表
     * <p>
     * 1.获取数组总数，如果结果为空，则返回空分页对象
     * 2.获取数据分页结果，如果为空，则返回空分页对象
     * 3.将数据集状态转为明文
     *
     * @param pageNo   分页页码
     * @param pageSize 根据分页条件获取探测器数据列表
     * @return
     */
    public PageResponse getList(int pageNo, int pageSize) {
        //获取数据总数
        Long dataCount = masterComputerDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("master computer : get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);

        //获取分页数据集合
        List<MasterComputer> dataList = masterComputerDao.selectByPagination(page);
        if (CollectionUtils.isEmpty(dataList)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<MasterComputerVo> masterComputerVos = new ArrayList<MasterComputerVo>();

        //从数据常量MAP对象中获取探测器数据MAP
        Map<String, Object> masterComputerDataMap = (Map<String, Object>) YamlForMapHandleUtil.getMapByKey(DataMap.getMap()
                , DataMap.Key.MASTER_COMPUTER.toString());

        for (MasterComputer masterComputer : dataList) {
            if (ObjectUtils.isEmpty(masterComputer.getStatus())) {
                logger.info("master computer : the id is  [" + masterComputer.getId() + "] object status is empty");
                continue;
            }

            MasterComputerVo masterComputerVo = new MasterComputerVo();
            BeanUtils.copyProperties(masterComputer, masterComputerVo);

            //从探测器数据MAP中获取出探测器状态数据字符串
            String statusCN = YamlForMapHandleUtil.getValueBykey(masterComputerDataMap, DataMap.Key.STATUS.toString(), masterComputer.getStatus().toString());
            if (StringUtils.isEmpty(statusCN)) {
                logger.info("master computer : the id is  [" + masterComputer.getId() + "] object status [" + masterComputer.getStatus() + "] cn is null");
                statusCN = StringUtils.EMPTY;
            }
            masterComputerVo.setStatusCN(statusCN);
            masterComputerVos.add(masterComputerVo);
        }

        return new PageResponse(pageNo, pageSize, dataCount, masterComputerVos);
    }

    /**
     * 上位机设备信息删除
     *
     * @param id 设备序列编号
     * @return
     */
    public void delete(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("delete fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.DETECTOR_ID_IS_EMPTY);
        }

        masterComputerDao.deleteById(id);
    }

    /**
     * 上位机设备信息添加
     *
     * @param name 设备名称
     * @param code 设备代码
     */
    public void add(String name, Integer code) throws EqianyuanException {
        if (StringUtils.isEmpty(name)) {
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_IS_EMPTY);
        }

        if (ObjectUtils.isEmpty(code)) {
            logger.info("add fail , because code is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_CODE_IS_EMPTY);
        }

        try {
            if (name.getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MASTER_COMPUTER_NAME_CONTENT_MAX_LENGTH) {
                logger.info("add fail , because name content too long");
                throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_CONTENT_TOO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name content getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        MasterComputer masterComputer = new MasterComputer();
        masterComputer.setName(name);
        masterComputer.setCode(code);
        masterComputerDao.insertSelective(masterComputer);
    }

    /**
     * 上位机设备信息修改
     *
     * @param masterComputerRequest 上位机修改请求数据封装对象
     * @throws EqianyuanException
     */
    public void update(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        if (StringUtils.isEmpty(masterComputerRequest.getId())) {
            logger.info("update fail , because update data id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_ID_IS_EMPTY);
        }

        if (StringUtils.isEmpty(masterComputerRequest.getName())) {
            logger.info("update fail , because update data name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_IS_EMPTY);
        }

        if (ObjectUtils.isEmpty(masterComputerRequest.getCode())) {
            logger.info("update fail , because update data code is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_CODE_IS_EMPTY);
        }

        try {
            if (masterComputerRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MASTER_COMPUTER_NAME_CONTENT_MAX_LENGTH) {
                logger.info("update fail , because update data name content too long");
                throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_CONTENT_TOO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("update fail , because update data name content getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        MasterComputer masterComputer = new MasterComputer();
        BeanUtils.copyProperties(masterComputerRequest, masterComputer);
        masterComputerDao.updateByPrimaryKeySelective(masterComputer);
    }
}
