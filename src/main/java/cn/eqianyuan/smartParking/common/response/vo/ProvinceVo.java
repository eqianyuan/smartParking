package cn.eqianyuan.smartParking.common.response.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class ProvinceVo {
    private Integer id;

    @JSONField(name = "province_id")
    private String provinceId;

    @JSONField(name = "province_name")
    private String provinceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}