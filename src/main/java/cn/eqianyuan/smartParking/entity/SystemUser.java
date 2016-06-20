package cn.eqianyuan.smartParking.entity;

/**
 * 系统用户
 * Created by jason on 2016-05-19.
 */
public class SystemUser {
    //系统用户用户名
    private String userName;
    //用户密码
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
