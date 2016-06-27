package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICityDao {
    /**
     * 根据省编号查找数据集合
     *
     * @param provinceId 省编号
     * @return
     */
    List<City> selectByList(String provinceId);

    /**
     * 根据地区编号查询数据
     *
     * @param provinceId    省编号
     * @param cityId        市编号
     * @return
     */
    City selectById(@Param("provinceId") String provinceId, @Param("cityId") String cityId);
}