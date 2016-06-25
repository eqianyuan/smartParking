package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.entity.City;

import java.util.List;

public interface ICityDao {
    /**
     * 根据省编号查找数据集合
     *
     * @param provinceId 省编号
     * @return
     */
    List<City> selectByList(String provinceId);
}