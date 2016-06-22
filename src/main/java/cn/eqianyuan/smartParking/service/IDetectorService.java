package cn.eqianyuan.smartParking.service;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.PageResponse;

/**
 * 探测器设备业务接口
 * Created by jason on 2016-06-20.
 */
public interface IDetectorService {

    /**
     * 根据分页条件获取探测器数据列表
     *
     * @param pageNo   分页页码
     * @param pageSize 根据分页条件获取探测器数据列表
     * @return
     */
    PageResponse getList(int pageNo, int pageSize);

    /**
     * 探测器设备信息删除
     *
     * @param id 设备序列编号
     * @return
     */
    void delete(String ... id) throws EqianyuanException;
}
