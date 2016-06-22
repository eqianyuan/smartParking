package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.util.SessionUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return SystemConf.SYSTEM_MANAGE_HOME_BY_PAGE.toString();
    }

    @RequestMapping("/gotoPage")
    public String gotoPage(@RequestParam(name = "url") String url) throws Exception {
        if(StringUtils.isEmpty(url)){
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }
        return url;
    }

}
