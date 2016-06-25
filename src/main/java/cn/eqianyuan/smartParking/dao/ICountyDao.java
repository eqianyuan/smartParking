package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.entity.County;

import java.util.List;

public interface ICountyDao {
    /**
     * 根据市编号查找区数据集合
     *
     * @param cityId 市编号
     * @return
     */
    List<County> selectByList(String cityId);
}