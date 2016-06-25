package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.response.vo.CityVo;
import cn.eqianyuan.smartParking.common.response.vo.CountyVo;
import cn.eqianyuan.smartParking.common.response.vo.ProvinceVo;
import cn.eqianyuan.smartParking.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 地区控制器
 */
@Controller
public class AreaController extends BaseController{

    @Autowired
    private IAreaService areaService;

    /**
     * 获取省数据集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getProvince")
    @ResponseBody
    public ServerResponse getProvince() throws EqianyuanException {
        List<ProvinceVo> provinceVoList = areaService.getProvince();
        return new ServerResponse.ResponseBuilder().data(provinceVoList).build();
    }

    /**
     * 根据省信息获取下级市数据集合
     *
     * @param provinceId 省级编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getCity")
    @ResponseBody
    public ServerResponse getCity(String provinceId) throws EqianyuanException {
        List<CityVo> cityVos = areaService.getCity(provinceId);
        return new ServerResponse.ResponseBuilder().data(cityVos).build();
    }

    /**
     * 根据市信息获取下级区数据集合
     *
     * @param cityId 市级编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getCounty")
    @ResponseBody
    public ServerResponse getCounty(String cityId) throws EqianyuanException {
        List<CountyVo> countyVos = areaService.getCounty(cityId);
        return new ServerResponse.ResponseBuilder().data(countyVos).build();
    }
}
