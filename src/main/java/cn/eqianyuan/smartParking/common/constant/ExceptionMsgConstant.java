package cn.eqianyuan.smartParking.common.constant;

/**
 * 自定义异常错误消息静态常量类
 * Created by jason on 2016-05-19.
 */
public class ExceptionMsgConstant {

    //系统错误
    public static final String SYSTEM_ERROR = "10000001";
    //缺少请求参数
    public static final String SYSTEM_LACK_OF_REQUEST_PARAMETER = "10000002";
    //获取字符串字节错误
    public static final String SYSTEM_GET_BYTE_FAIL = "10000003";

    //登录验证码是空
    public static final String LOGIN_VALIDATA_CODE_IS_EMPTY = "10010001";
    //登录验证码错误
    public static final String LOGIN_VALIDATA_CODE_IS_ERROR = "10010002";
    //登录用户名为空
    public static final String LOGIN_USER_NAME_IS_EMPTY = "10010003";
    //登录密码为空
    public static final String LOGIN_PASSWORD_IS_EMPTY = "10010004";
    //用户名或密码错误
    public static final String LOGIN_USER_NAME_OR_PASSWORD_ERROR = "10010005";
    //验证码内容超长
    public static final String VALIDATA_CODE_CONTENT_LENGTH_TO0_LONG = "10010006";

    //探测器设备序列号为空
    public static final String DETECTOR_ID_IS_EMPTY = "10020001";
    //上位机设备序列号为空
    public static final String MASTER_COMPUTER_ID_IS_EMPTY = "10030001";
    //上位机设备名称为空
    public static final String MASTER_COMPUTER_NAME_IS_EMPTY = "10030002";
    //上位机设备编号为空
    public static final String MASTER_COMPUTER_CODE_IS_EMPTY = "10030003";
    //上位机设备名称内容太长
    public static final String MASTER_COMPUTER_NAME_CONTENT_TOO_LONG = "10030004";
}
