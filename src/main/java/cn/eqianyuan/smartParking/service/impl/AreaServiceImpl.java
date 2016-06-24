package cn.eqianyuan.smartParking.service.impl;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.vo.ProvinceVo;
import cn.eqianyuan.smartParking.dao.IProvinceDao;
import cn.eqianyuan.smartParking.entity.Province;
import cn.eqianyuan.smartParking.service.IAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区信息接口实现类
 */
@Service
public class AreaServiceImpl implements IAreaService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IProvinceDao provinceDao;

    /**
     * 获取省数据集合
     *
     * @return
     */
    public List<ProvinceVo> getProvince() throws EqianyuanException {
        List<Province> provinces = provinceDao.selectByList();
        if (CollectionUtils.isEmpty(provinces)) {
            logger.info("get province is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        List<ProvinceVo> provinceVos = new ArrayList<ProvinceVo>();
        for (Province province : provinces) {
            ProvinceVo provinceVo = new ProvinceVo();
            BeanUtils.copyProperties(province, provinceVo);

            provinceVos.add(provinceVo);
        }
        return provinceVos;
    }
}
