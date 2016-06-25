package cn.eqianyuan.smartParking.service.impl;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.vo.CityVo;
import cn.eqianyuan.smartParking.common.response.vo.CountyVo;
import cn.eqianyuan.smartParking.common.response.vo.ProvinceVo;
import cn.eqianyuan.smartParking.dao.ICityDao;
import cn.eqianyuan.smartParking.dao.ICountyDao;
import cn.eqianyuan.smartParking.dao.IProvinceDao;
import cn.eqianyuan.smartParking.entity.City;
import cn.eqianyuan.smartParking.entity.County;
import cn.eqianyuan.smartParking.entity.Province;
import cn.eqianyuan.smartParking.service.IAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountyDao countyDao;

    /**
     * 获取省数据集合
     *
     * @return
     */
    public List<ProvinceVo> getProvince() throws EqianyuanException {
        List<Province> provinces = provinceDao.selectByList();
        if (CollectionUtils.isEmpty(provinces)) {
            logger.info("get province , result is null");
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

    /**
     * 根据省编号获取市数据集合
     *
     * @param provinceId 省编号
     * @return
     * @throws EqianyuanException
     */
    public List<CityVo> getCity(String provinceId) throws EqianyuanException {
        if (StringUtils.isEmpty(provinceId)) {
            logger.info("getCity fail , because query param provinceId is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_ID_IS_EMPTY);
        }

        List<City> cities = cityDao.selectByList(provinceId);
        if (CollectionUtils.isEmpty(cities)) {
            logger.info("get city by province id [" + provinceId + "] , result is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        List<CityVo> cityVos = new ArrayList<CityVo>();
        for (City city : cities) {
            CityVo cityVo = new CityVo();
            BeanUtils.copyProperties(city, cityVo);

            cityVos.add(cityVo);
        }

        return cityVos;
    }

    /**
     * 根据市级编号获取下级区数据集合
     *
     * @param cityId 市编号
     * @return
     * @throws EqianyuanException
     */
    public List<CountyVo> getCounty(String cityId) throws EqianyuanException {
        if (StringUtils.isEmpty(cityId)) {
            logger.info("getCounty fail , because query param cityId is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_ID_IS_EMPTY);
        }

        List<County> counties = countyDao.selectByList(cityId);
        if (CollectionUtils.isEmpty(counties)) {
            logger.info("get county by city id [" + cityId + "] , result is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }

        List<CountyVo> countyVos = new ArrayList<CountyVo>();
        for (County county : counties) {
            CountyVo countyVo = new CountyVo();
            BeanUtils.copyProperties(county, countyVo);

            countyVos.add(countyVo);
        }

        return countyVos;
    }
}
