package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.entity.County;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICountyDao {
    /**
     * 根据市编号查找区数据集合
     *
     * @param cityId 市编号
     * @return
     */
    List<County> selectByList(String cityId);

    /**
     * 根据地区编号查询数据
     *
     * @param cityId   市编号
     * @param countyId 区编号
     * @return
     */
    County selectById(@Param("cityId") String cityId, @Param("countyId") String countyId);

}