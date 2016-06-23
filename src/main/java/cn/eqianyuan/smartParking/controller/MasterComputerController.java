package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.request.masterComputer.MasterComputerRequest;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import cn.eqianyuan.smartParking.service.IMasterComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 上位机设备管理控制器
 * Created by jason on 2016-06-20.
 */
@Controller
@RequestMapping("/system-manage/masterComputer")
public class MasterComputerController extends BaseController {

    @Autowired
    private IMasterComputerService masterComputerService;

    /**
     * 上位机列表页面
     *
     * @return
     */
    @RequestMapping("/list")
    public String listByPage() {
        return SystemConf.MANAGE_MASTER_COMPUTER_LIST_BY_PAGE.toString();
    }

    /**
     * 上位机设备信息列表
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public ServerResponse dataList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize)
            throws EqianyuanException {

        PageResponse pageResponse = masterComputerService.getList(pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 上位机设备信息删除
     *
     * @param id 设备序列号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(@RequestParam(value = "id") String[] id) throws EqianyuanException {
        masterComputerService.delete(id);
        return new ServerResponse();
    }

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(@RequestParam(value = "name") String name,
                              @RequestParam(value = "code") Integer code) throws EqianyuanException {
        masterComputerService.add(name, code);
        return new ServerResponse();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        masterComputerService.update(masterComputerRequest);
        return new ServerResponse();
    }
}
