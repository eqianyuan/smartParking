package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.common.Page;
import cn.eqianyuan.smartParking.entity.Detector;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDetectorDao {
    int deleteByPrimaryKey(String id);

    int insert(Detector record);

    int insertSelective(Detector record);

    Detector selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Detector record);

    int updateByPrimaryKey(Detector record);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination();

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<Detector> selectByPagination(@Param("page") Page page);
}