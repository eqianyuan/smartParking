package cn.eqianyuan.smartParking.controller;

import cn.eqianyuan.smartParking.common.constant.ExceptionMsgConstant;
import cn.eqianyuan.smartParking.common.exception.EqianyuanException;
import cn.eqianyuan.smartParking.common.response.ServerResponse;
import cn.eqianyuan.smartParking.common.util.YamlForMapHandleUtil;
import cn.eqianyuan.smartParking.common.util.yamlMapper.SystemErr;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by jason on 2016-05-22.
 */
public class BaseController {

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 用于统一处理异常信息
     *
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ServerResponse exception(Exception ex) {
        String messageCode = ExceptionMsgConstant.SYSTEM_ERROR;
        if (ex instanceof EqianyuanException) {
            logger.warn("BaseController catch exception info is :" +
                    YamlForMapHandleUtil.getValueBykey(SystemErr.getMap()
                            , ex.getMessage()
                            , SystemErr.Key.CN.toString()));
            messageCode = ex.getMessage();
        } else {
            logger.warn("BaseController catch exception info is :", ex);
        }

        Map<String, Object> systemErrMap = (Map<String, Object>) YamlForMapHandleUtil.getMapByKey(SystemErr.getMap(), messageCode);

        return new ServerResponse.ResponseBuilder()
                .code(systemErrMap.get(SystemErr.Key.CODE.toString()).toString())
                .message(systemErrMap.get(SystemErr.Key.CN.toString()).toString())
                .data(null).build();
    }

}
