package cn.eqianyuan.smartParking.common.util.yamlMapper;

import cn.eqianyuan.smartParking.entity.SystemUser;
import org.springframework.util.ObjectUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.List;

/**
 * 系统用户yaml文件操作类
 * Created by jason on 2016/1/9.
 */
public class SystemUser4Yaml {

    /**
     * 私有化默认构造函数
     * 禁止外部构造，减少内存开销
     */
    private SystemUser4Yaml() {
    }

    /**
     * 系统服务用户集合对象
     */
    private static List<SystemUser> systemUserList;

    /**
     * 系统用户数据配置文件名称
     */
    private static final String YAML_NAME = "system-user.yaml";

    /**
     * 通过解析系统用户yaml配置文件，获取用户信息数据集合
     *
     * @return
     */
    public static List<SystemUser> getSystemUserList() {
        if (ObjectUtils.isEmpty(systemUserList)) {
            Yaml yaml = new Yaml();

            /**
             * 如果配置文件在jar包中，则需要使用流加载方式读取
             */
//            yaml.loadAs(this.getClass().getResourceAsStream("/user.yaml"), SystemUserBo.class);

            //从yaml配置文件中获取系统用户信息数据集合
            systemUserList = yaml.loadAs(SystemUser4Yaml.class.getClassLoader().getResourceAsStream(YAML_NAME), List.class);
        }
        return systemUserList;
    }

}
