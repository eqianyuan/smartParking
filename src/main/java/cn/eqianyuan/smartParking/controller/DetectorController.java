package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import cn.eqianyuan.smartParking.service.IDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 探测器设备管理控制器
 * Created by jason on 2016-06-20.
 */
@Controller
@RequestMapping("/system-manage/detector")
public class DetectorController extends BaseController {

    @Autowired
    private IDetectorService detectorService;

    /**
     * 探测器设备信息列表
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public ServerResponse dataList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)
            throws EqianyuanException {

        PageResponse pageResponse = detectorService.getList(pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 探测器设备信息删除
     *
     * @param id 设备序列号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(@RequestParam(value = "id") String[] id) throws EqianyuanException {
        detectorService.delete(id);
        return new ServerResponse();
    }

    public ServerResponse add(){
        return null;
    }
}
