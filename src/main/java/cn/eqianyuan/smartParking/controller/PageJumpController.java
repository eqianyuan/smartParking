package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.util.SessionUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * 主要功能：
 * 通过后台方法跳转到界面
 * <p>
 * Created by jason on 2016-05-17.
 */
@Controller
@RequestMapping("/system-manage")
public class PageJumpController extends BaseController {

    /**
     * 进入后台首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        if (ObjectUtils.isEmpty(SessionUtil.getAttribute(SystemConf.SYSTEM_SESSION_USER.toString()))) {
            return SystemConf.SYSTEM_MANAGE_LOGIN_BY_PAGE.toString();
        }
        return SystemConf.MANAGE_DETECTOR_LIST_BY_PAGE.toString();
    }

}
