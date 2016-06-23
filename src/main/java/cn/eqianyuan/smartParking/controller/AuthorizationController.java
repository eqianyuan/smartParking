package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.response.vo.SystemUserVo;
import cn.eqianyuan.smartParking.common.util.SessionUtil;
import cn.eqianyuan.smartParking.common.util.VerifyCodeUtils;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemConf;
import cn.eqianyuan.smartParking.entity.SystemUser;
import cn.eqianyuan.smartParking.service.IAuthorizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户授权
 * 主要功能：
 * 获取验证码
 * 用户登录
 * 用户登出
 * <p>
 * Created by jason on 2016-05-17.
 */
@Controller
public class AuthorizationController extends BaseController {

    @Autowired
    private IAuthorizationService authorizationService;

    /**
     * 获取验证码
     *
     * @param verifyCodeLength 验证码内容字符长度
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(@RequestParam(name = "verify_code_length", required = false, defaultValue = "4") Integer verifyCodeLength,
                           HttpServletResponse response) throws EqianyuanException, IOException {
        /**
         * 控制验证码生成数量，避免构建图片时出现内存不足问题
         */
        if (verifyCodeLength > 10) {
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_CONTENT_LENGTH_TO0_LONG);
        }

        String verifyCode = VerifyCodeUtils.random(verifyCodeLength);

        /**
         * 将验证码内容写入session
         */
        SessionUtil.setAttribute(SystemConf.VERIFY_CODE.toString(), verifyCode);

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        VerifyCodeUtils.render(verifyCode, sos, verifyCodeLength * 30, 30);
        sos.close();
    }

    /**
     * 带验证码登录
     *
     * @param userName   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/system-manage/login")
    @ResponseBody
    public ServerResponse login(@RequestParam(value = "user_name") String userName,
                                @RequestParam String password,
                                @RequestParam(value = "verify_code") String verifyCode) throws EqianyuanException {
        SystemUser systemUser = authorizationService.login(userName, password, verifyCode);
        SystemUserVo systemUserVo = new SystemUserVo();
        BeanUtils.copyProperties(systemUser, systemUserVo);

        /**
         * 将用户VO对象写入session
         */
        SessionUtil.setAttribute(SystemConf.SYSTEM_SESSION_USER.toString(), systemUserVo);
        return new ServerResponse();
    }

    /**
     * 退出登录
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/system-manage/logout")
    public String logout() throws EqianyuanException {
        /**
         * 将用户VO对象从session中移除
         */
        SessionUtil.removeAttribute(SystemConf.SYSTEM_SESSION_USER.toString());
        return SystemConf.SYSTEM_MANAGE_LOGIN_BY_PAGE.toString();
    }

}
