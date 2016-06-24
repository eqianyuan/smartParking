package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.util.SessionUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping("/home")
    public String index() {
        if (ObjectUtils.isEmpty(SessionUtil.getAttribute(SystemConf.SYSTEM_SESSION_USER.toString()))) {
            return SystemConf.SYSTEM_MANAGE_LOGIN_BY_PAGE.toString();
        }
        return SystemConf.SYSTEM_MANAGE_HOME_BY_PAGE.toString();
    }

    /**
     * 公共页面跳转
     * 不带数据
     *
     * @param url 目的页面位置
     * @return
     * @throws Exception
     */
    @RequestMapping("/gotoPage")
    public String gotoPage(@RequestParam(name = "url") String url) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }
        return url;
    }

//    /**
//     * 公共页面跳转
//     * 带数据
//     *
//     * @param url 目的页面位置
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/jumpPage")
//    public String gotoPage(@RequestParam(name = "url") String url,
//                           HttpServletRequest request) throws Exception {
//        if (StringUtils.isEmpty(url)) {
//            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
//        }
//
//        //获取请求中所有的key
//        Enumeration enumeration = request.getParameterNames();
//        Map<String, Object> reqMap = new HashMap<String, Object>();
//        while (enumeration.hasMoreElements()) {
//            String key = (String) enumeration.nextElement();
//            if (StringUtils.equals("url", key)) {
//                continue;
//            }
//
//            reqMap.put(key, request.getParameter(key));
//        }
//
//        request.setAttribute("reqMap", reqMap);
//        return url;
//    }

}
