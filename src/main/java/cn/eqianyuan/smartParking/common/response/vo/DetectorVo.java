package cn.eqianyuan.smartParking.common.response.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 面向视图层探测器对象
 */
public class DetectorVo {
    private String id;

    private Integer code;

    private Integer status;

    @JSONField(name = "parent_id")
    private String parentId;

    @JSONField(name = "status_cn")
    private String statusCN;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatusCN() {
        return statusCN;
    }

    public void setStatusCN(String statusCN) {
        this.statusCN = statusCN;
    }
}