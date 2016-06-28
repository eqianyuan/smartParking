package cn.eqianyuan.smartParking.common.response.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class MasterComputerVo {
    //序列编号
    private String id;

    //设备代码
    private Integer code;

    //设备名称
    private String name;

    //设备状态
    private Integer status;

    @JSONField(name = "status_cn")
    private String statusCN;

    //设备归属详细地址
    private String address;

    //设备所属省
    private String province;

    //设备所属市
    private String city;

    //设备所属区
    private String county;

    //设备描述
    private String description;

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

    public String getStatusCN() {
        return statusCN;
    }

    public void setStatusCN(String statusCN) {
        this.statusCN = statusCN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}