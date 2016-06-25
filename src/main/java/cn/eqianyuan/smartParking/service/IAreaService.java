package cn.eqianyuan.smartParking.service;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.vo.CityVo;
import cn.eqianyuan.smartParking.common.response.vo.CountyVo;
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

    /**
     * 根据省编号获取对应市数据集合
     *
     * @param provinceId    省编号
     * @return
     */
    List<CityVo> getCity(String provinceId) throws EqianyuanException;

    /**
     * 根据省编号获取对应市数据集合
     *
     * @param cityId    市编号
     * @return
     */
    List<CountyVo> getCounty(String cityId) throws EqianyuanException;
}
