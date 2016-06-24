package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.entity.Province;

import java.util.List;

public interface IProvinceDao {
    /**
     * 查找数据集合
     *
     * @return
     */
    List<Province> selectByList();
}