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
import cn.eqianyuan.smartParking.dao.ICityDao;
import cn.eqianyuan.smartParking.dao.ICountyDao;
import cn.eqianyuan.smartParking.dao.IMasterComputerDao;
import cn.eqianyuan.smartParking.dao.IProvinceDao;
import cn.eqianyuan.smartParking.entity.City;
import cn.eqianyuan.smartParking.entity.County;
import cn.eqianyuan.smartParking.entity.MasterComputer;
import cn.eqianyuan.smartParking.entity.Province;
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

    @Autowired
    private IProvinceDao provinceDao;

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountyDao countyDao;

    private Logger logger = Logger.getLogger(this.getClass());

    //上位机名称内容最大字节长度
    private static final int MASTER_COMPUTER_NAME_CONTENT_MAX_LENGTH = 100;
    //上位机通信代码最大内容字符长度
    private static final int MASTER_COMPUTER_CODE_CONTENT_MAX_LENGTH = 11;
    //上位机详细地址内容最大字节长度
    private static final int MASTER_COMPUTER_ADDRESS_CONTENT_MAX_LENGTH = 300;


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

            //获取上位机状态明文
            String statusCN = getStatusCN(masterComputerDataMap, masterComputer.getId(), masterComputer.getStatus().toString());

            MasterComputerVo masterComputerVo = new MasterComputerVo();
            BeanUtils.copyProperties(masterComputer, masterComputerVo);
            //设置上位机VO对象状态明文
            masterComputerVo.setStatusCN(statusCN);
            //设置上位机VO对象地区信息
            setAreaInfo(masterComputer, masterComputerVo);

            masterComputerVos.add(masterComputerVo);
        }

        return new PageResponse(pageNo, pageSize, dataCount, masterComputerVos);
    }

    /**
     * 上位机设备信息对象
     *
     * @param id 上位机设备序列编号
     * @return
     */
    public MasterComputerVo object(String id) throws EqianyuanException {
        if (StringUtils.isEmpty(id)) {
            logger.info("get object empty , because query id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.DETECTOR_ID_IS_EMPTY);
        }

        MasterComputer masterComputer = masterComputerDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(masterComputer) ||
                StringUtils.isEmpty(masterComputer.getId())) {
            logger.info("get object empty , because id [" + id + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_INFO_NO_EXISTS);
        }

        MasterComputerVo masterComputerVo = new MasterComputerVo();
        BeanUtils.copyProperties(masterComputer, masterComputerVo);

        //从数据常量MAP对象中获取上位机数据MAP
        Map<String, Object> masterComputerDataMap = (Map<String, Object>) YamlForMapHandleUtil.getMapByKey(DataMap.getMap()
                , DataMap.Key.MASTER_COMPUTER.toString());

        //获取上位机状态明文
        String statusCN = getStatusCN(masterComputerDataMap, masterComputer.getId(), masterComputer.getStatus().toString());

        //设置上位机VO对象状态明文
        masterComputerVo.setStatusCN(statusCN);
        //设置上位机VO对象地区信息
        setAreaInfo(masterComputer, masterComputerVo);

        return masterComputerVo;
    }

    /**
     * 上位机设备信息添加
     *
     * @param masterComputerRequest 上位机添加数据请求封装对象
     * @throws EqianyuanException
     */
    public void add(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        //数据写库校验
        editValidation(masterComputerRequest);

        //根据地区区和code查询数据，校验省市区地理位置下的设备code是否已经存在
        MasterComputer masterComputerByQuery = masterComputerDao.selectByCode(masterComputerRequest.getCounty(), masterComputerRequest.getCode());
        if (!ObjectUtils.isEmpty(masterComputerByQuery)) {
            logger.info("add fail , because select by code [" + masterComputerRequest.getCode() + "] , county [" + masterComputerRequest.getCounty() + "] data is exists");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_DATA_IS_EXISTS);
        }

        //封装DB插入对象
        MasterComputer masterComputer = getMasterComputer(masterComputerRequest);
        masterComputerDao.insertSelective(masterComputer);
    }

    /**
     * 上位机设备信息修改
     *
     * @param masterComputerRequest 上位机修改请求数据封装对象
     * @throws EqianyuanException
     */
    public void update(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        //判断数据主键序列编号是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getId())) {
            logger.info("update fail , because update data id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_ID_IS_EMPTY);
        }

        //DB写库数据校验
        editValidation(masterComputerRequest);

        //根据设备主键序列号查询设备数据
        MasterComputer masterComputerByOriginal = masterComputerDao.selectByPrimaryKey(masterComputerRequest.getId());

        //判断根据设备主键序列号查询的设备数据是否存在
        if (ObjectUtils.isEmpty(masterComputerByOriginal) ||
                StringUtils.isEmpty(masterComputerByOriginal.getId())) {
            logger.info("get object empty , because id [" + masterComputerRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_INFO_NO_EXISTS);
        }

        //检查待修改数据中的设备code是否和原数据设备code相同，不相同的情况下需要校验地区下CODE是否重复
        if (!StringUtils.equals(masterComputerRequest.getCode(), String.valueOf(masterComputerByOriginal.getCode()))) {
            //根据地区区和code查询数据，校验省市区地理位置下的设备code是否已经存在
            MasterComputer masterComputerByQuery = masterComputerDao.selectByCode(masterComputerRequest.getCounty(), masterComputerRequest.getCode());
            if (!ObjectUtils.isEmpty(masterComputerByQuery)) {
                logger.info("add fail , because select by code [" + masterComputerRequest.getCode() + "] , county [" + masterComputerRequest.getCounty() + "] data is exists");
                throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_DATA_IS_EXISTS);
            }
        }

        //封装获取DB操作对象
        MasterComputer masterComputer = getMasterComputer(masterComputerRequest);
        masterComputerDao.updateByPrimaryKeySelective(masterComputer);
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
     * 设置上位机VO对象地区信息
     *
     * @param masterComputer   上位机DB数据对象
     * @param masterComputerVo 上位机VO对象
     */
    private void setAreaInfo(MasterComputer masterComputer, MasterComputerVo masterComputerVo) {
        //根据省编号获取省地址
        Province province = provinceDao.selectById(masterComputer.getProvince());
        if (!ObjectUtils.isEmpty(province)) {
            masterComputerVo.setProvinceName(province.getProvinceName());
        }

        //根据市编号获取市地址
        City city = cityDao.selectById(masterComputer.getProvince(), masterComputer.getCity());
        if (!ObjectUtils.isEmpty(city)) {
            masterComputerVo.setCityName(city.getCityName());
        }

        //根据区编号获取区地址
        County county = countyDao.selectById(masterComputer.getCity(), masterComputer.getCounty());
        if (!ObjectUtils.isEmpty(county)) {
            masterComputerVo.setCountyName(county.getCountyName());
        }
    }

    /**
     * 根据请求入参对象获得DB操作对象
     *
     * @param masterComputerRequest
     * @return
     */
    private MasterComputer getMasterComputer(MasterComputerRequest masterComputerRequest) {
        MasterComputer masterComputer = new MasterComputer();
        masterComputer.setId(masterComputerRequest.getId());
        masterComputer.setName(masterComputerRequest.getName());
        masterComputer.setCode(Integer.parseInt(masterComputerRequest.getCode()));
        masterComputer.setProvince(masterComputerRequest.getProvince());
        masterComputer.setCity(masterComputerRequest.getCity());
        masterComputer.setCounty(masterComputerRequest.getCounty());
        masterComputer.setAddress(masterComputerRequest.getAddress());
        masterComputer.setDescription(masterComputerRequest.getDescription());
        return masterComputer;
    }

    /**
     * DB数据编辑校验
     *
     * @param masterComputerRequest
     * @throws EqianyuanException
     */
    private void editValidation(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        //判断设备名称是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getName())) {
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_IS_EMPTY);
        }

        //判断设备通信代码是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getCode())) {
            logger.info("add fail , because code is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_CODE_IS_EMPTY);
        }

        //判断省是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getProvince())) {
            logger.info("add fail , because province is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_ID_IS_EMPTY);
        }

        //判断市是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getCity())) {
            logger.info("add fail , because city is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_ID_IS_EMPTY);
        }

        //判断区是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getCounty())) {
            logger.info("add fail , because county is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_ID_IS_EMPTY);
        }

        //判断详细地址是否为空
        if (StringUtils.isEmpty(masterComputerRequest.getAddress())) {
            logger.info("add fail , because address is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_ADDRESS_IS_EMPTY);
        }

        //判断设备名称内容字节长度是否超出DB许可长度范围
        try {
            if (masterComputerRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MASTER_COMPUTER_NAME_CONTENT_MAX_LENGTH) {
                logger.info("add fail , because name content too long");
                throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_NAME_CONTENT_TOO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name content getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //判断设备通信代码内容长度是否超出DB许
        if (masterComputerRequest.getCode().length() > MASTER_COMPUTER_CODE_CONTENT_MAX_LENGTH) {
            logger.warn("add fial , because code content too long");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_CODE_CONTENT_TOO_LONG);
        }

        //判断设备通信代码值是否可以正确转换为int类型
        try {
            Integer.parseInt(masterComputerRequest.getCode());
        } catch (NumberFormatException e) {
            logger.warn("add fail , because code format from String to Integer error");
            throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_CODE_IS_NOT_NUMBER);
        }

        //根据省编号查询省数据，检查编号正确性
        Province province = provinceDao.selectById(masterComputerRequest.getProvince());
        if (ObjectUtils.isEmpty(province)) {
            logger.info("add fail , because select province by province id [" + masterComputerRequest.getProvince() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        //根据市编号查询市数据，检查编号正确性
        City city = cityDao.selectById(masterComputerRequest.getProvince(), masterComputerRequest.getCity());
        if (ObjectUtils.isEmpty(city)) {
            logger.info("add fail , because select city by city id [" + masterComputerRequest.getCity() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        //根据区编号查询区数据，检查编号正确性
        County county = countyDao.selectById(masterComputerRequest.getCity(), masterComputerRequest.getCounty());
        if (ObjectUtils.isEmpty(county)) {
            logger.info("add fail , because select county by county id [" + masterComputerRequest.getCounty() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }

        //判断详细地址内容长度是否超出DB许可长度
        try {
            if (masterComputerRequest.getAddress().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MASTER_COMPUTER_ADDRESS_CONTENT_MAX_LENGTH) {
                logger.info("add fail , because address content too long");
                throw new EqianyuanException(ExceptionMsgConstant.MASTER_COMPUTER_ADDRESS_CONTENT_TOO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because address content getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }
    }

    /**
     * 获取上位机状态明文
     *
     * @param masterComputerDataMap 上位机数据静态资源MAP对象
     * @param id                    上位机序列编号
     * @param status                上位机状态
     * @return
     */
    private String getStatusCN(Map<String, Object> masterComputerDataMap, String id, String status) {
        //从上位机数据MAP中获取出上位机状态数据字符串
        String statusCN = YamlForMapHandleUtil.getValueBykey(masterComputerDataMap, DataMap.Key.STATUS.toString(), status);

        if (StringUtils.isEmpty(statusCN)) {
            logger.info("master computer : the id is  [" + id + "] object status [" + status + "] cn is null");
            statusCN = StringUtils.EMPTY;
        }
        return statusCN;
    }
}
