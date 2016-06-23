package cn.eqianyuan.smartParking.common.request.masterComputer;

/**
 * 上位机对象，用于封装request请求数据
 */
public class MasterComputerRequest {
    //序列编号
    private String id;

    //设备代码
    private Integer code;

    //设备名称
    private String name;

    //设备状态
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}