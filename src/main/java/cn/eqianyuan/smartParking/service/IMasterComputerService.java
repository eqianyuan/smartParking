package cn.eqianyuan.smartParking.service;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.request.masterComputer.MasterComputerRequest;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.vo.MasterComputerVo;

/**
 * 上位机设备业务接口
 * Created by jason on 2016-06-20.
 */
public interface IMasterComputerService {

    /**
     * 根据分页条件获取上位机数据列表
     *
     * @param pageNo   分页页码
     * @param pageSize 根据分页条件获取探测器数据列表
     * @return
     */
    PageResponse getList(int pageNo, int pageSize);

    /**
     * 上位机设备信息删除
     *
     * @param id 设备序列编号
     * @return
     */
    void delete(String... id) throws EqianyuanException;

    /**
     * 上位机设备信息添加
     *
     * @param masterComputerRequest 上位机添加数据请求封装对象
     * @return
     * @throws EqianyuanException
     */
    void add(MasterComputerRequest masterComputerRequest) throws EqianyuanException;

    /**
     * 上位机设备信息
     *
     * @param id
     * @return
     */
    MasterComputerVo object(String id) throws EqianyuanException;


    /**
     * 上位机设备信息修改
     *
     * @param masterComputerRequest 上位机修改请求数据封装对象
     * @throws EqianyuanException
     */
    void update(MasterComputerRequest masterComputerRequest) throws EqianyuanException;
}
