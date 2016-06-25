package cn.eqianyuan.smartParking.common.response.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class CityVo {
    private Integer id;

    @JSONField(name = "province_id")
    private String provinceId;

    @JSONField(name = "city_id")
    private String cityId;

    @JSONField(name = "city_name")
    private String cityName;

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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}