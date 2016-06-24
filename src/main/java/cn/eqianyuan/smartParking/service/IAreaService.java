package cn.eqianyuan.smartParking.service;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.vo.ProvinceVo;

import java.util.List;

/**
 * 地区信息接口
 */
public interface IAreaService {

    /**
     * 获取省数据集合
     *
     * @return
     */
    List<ProvinceVo> getProvince() throws EqianyuanException;
}
