package cn.eqianyuan.smartParking.service.impl;

import cn.eqianyuan.smartParking.common.Page;
import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.vo.DetectorVo;
import cn.eqianyuan.smartParking.common.util.YamlForMapHandleUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.DataMap;
import cn.eqianyuan.smartParking.dao.IDetectorDao;
import cn.eqianyuan.smartParking.entity.Detector;
import cn.eqianyuan.smartParking.service.IDetectorService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 探测器设备业务具体实现类
 * Created by jason on 2016-06-20.
 */
@Service
public class DetectorServiceImpl implements IDetectorService {

    @Autowired
    private IDetectorDao detectorDao;

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据分页条件获取探测器数据列表
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
        Long dataCount = detectorDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("detector : get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);

        //获取分页数据集合
        List<Detector> dataList = detectorDao.selectByPagination(page);
        if (CollectionUtils.isEmpty(dataList)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<DetectorVo> detectorVos = new ArrayList<DetectorVo>();

        //从数据常量MAP对象中获取探测器数据MAP
        Map<String, Object> detectorDataMap = (Map<String, Object>) YamlForMapHandleUtil.getMapByKey(DataMap.getMap()
                , DataMap.Key.DETECTOR.toString());

        for (Detector detector : dataList) {
            if (ObjectUtils.isEmpty(detector.getStatus())) {
                logger.info("detector : the id is  [" + detector.getId() + "] object status is empty");
                continue;
            }

            DetectorVo detectorVo = new DetectorVo();
            BeanUtils.copyProperties(detector, detectorVo);

            //从探测器数据MAP中获取出探测器状态数据字符串
            String statusCN = YamlForMapHandleUtil.getValueBykey(detectorDataMap, DataMap.Key.STATUS.toString(), detector.getStatus().toString());
            if (StringUtils.isEmpty(statusCN)) {
                logger.info("detector : the id is  [" + detector.getId() + "] object status [" + detector.getStatus() + "] cn is null");
                statusCN = StringUtils.EMPTY;
            }
            detectorVo.setStatusCN(statusCN);
            detectorVos.add(detectorVo);
        }

        return new PageResponse(pageNo, pageSize, dataCount, detectorVos);
    }

    /**
     * 探测器设备信息删除
     *
     * @param id 设备序列编号
     * @return
     */
    public void delete(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("delete fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.DETECTOR_ID_IS_EMPTY);
        }

        detectorDao.deleteById(id);
    }
}
