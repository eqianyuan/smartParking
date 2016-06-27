package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.request.masterComputer.MasterComputerRequest;
import cn.eqianyuan.smartParking.common.response.PageResponse;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.response.vo.MasterComputerVo;
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
     * 上位机设备信息列表
     *
     * @param pageNo   分页页码
     * @param pageSize 分页每页显示条数
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

    /**
     * 上位机设备信息添加
     *
     * @param masterComputerRequest 上位机添加数据请求封装对象
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        masterComputerService.add(masterComputerRequest);
        return new ServerResponse();
    }

    /**
     * 上位机设备信息
     *
     * @param id 上位机序列编号
     * @return
     */
    @RequestMapping("/object")
    @ResponseBody
    public ServerResponse object(@RequestParam(value = "id") String id) throws EqianyuanException {
        MasterComputerVo masterComputerVo = masterComputerService.object(id);
        return new ServerResponse.ResponseBuilder().data(masterComputerVo).build();
    }

    /**
     * 上位机设备信息修改
     *
     * @param masterComputerRequest 上位机修改数据请求封装对象
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(MasterComputerRequest masterComputerRequest) throws EqianyuanException {
        masterComputerService.update(masterComputerRequest);
        return new ServerResponse();
    }
}
