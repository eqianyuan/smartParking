package cn.eqianyuan.smartParking.dao;

import cn.eqianyuan.smartParking.common.Page;
import cn.eqianyuan.smartParking.entity.MasterComputer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMasterComputerDao {
    int insertSelective(MasterComputer record);

    MasterComputer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MasterComputer record);

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
    List<MasterComputer> selectByPagination(@Param("page") Page page);

    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @return
     */
    int deleteById(@Param("id") String... id);
}