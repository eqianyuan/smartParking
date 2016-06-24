package cn.eqianyuan.smartParking.common.util.yamlMapper;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系统静态配置信息枚举类
 * <p>
 * Created by jason on 2016-06-02.
 */
public enum SystemConf {

    /**
     * 系统维护的线程池
     * 计算公式：单个CPU处理的线程数 * 设备可用有效CPU个数
     */
    POOL_BY_THREAD_SIZE(Executors.newFixedThreadPool(10 * Runtime.getRuntime().availableProcessors())),
    /**
     * 系统用户session对象
     */
    SYSTEM_SESSION_USER("systemUser"),
    /**
     * 验证码sessionkey
     */
    VERIFY_CODE("verifyCode"),
    /**
     * 平台字符集
     */
    PLATFORM_CHARSET("UTF-8"),

    /**
     * 系统登录页面位置
     */
    SYSTEM_MANAGE_LOGIN_BY_PAGE("login"),
    SYSTEM_MANAGE_HOME_BY_PAGE("home");

    private Object value;

    SystemConf(Object value) {
        this.value = value;
    }

    /**
     * 获取系统维护的线程池对象
     *
     * @return
     */
    public static ExecutorService getPoolByThreadSize() {
        return (ExecutorService) SystemConf.POOL_BY_THREAD_SIZE.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
