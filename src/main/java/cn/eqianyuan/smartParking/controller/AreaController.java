package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
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
public class AreaController {

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
}
