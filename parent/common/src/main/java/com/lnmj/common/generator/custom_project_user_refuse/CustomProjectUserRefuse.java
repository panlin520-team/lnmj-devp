package com.lnmj.common.generator.custom_project_user_refuse;

import java.util.Date;

public class CustomProjectUserRefuse {
    private Long id;

    private Long customProjectUserId;

    private Integer refuseTimes;

    private Integer status;

    private String createOperator;

    private Date createTime;

    private String modifyOperator;

    private Date dataChange_LastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomProjectUserId() {
        return customProjectUserId;
    }

    public void setCustomProjectUserId(Long customProjectUserId) {
        this.customProjectUserId = customProjectUserId;
    }

    public Integer getRefuseTimes() {
        return refuseTimes;
    }

    public void setRefuseTimes(Integer refuseTimes) {
        this.refuseTimes = refuseTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator == null ? null : createOperator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyOperator() {
        return modifyOperator;
    }

    public void setModifyOperator(String modifyOperator) {
        this.modifyOperator = modifyOperator == null ? null : modifyOperator.trim();
    }

    public Date getDataChange_LastTime() {
        return dataChange_LastTime;
    }

    public void setDataChange_LastTime(Date dataChange_LastTime) {
        this.dataChange_LastTime = dataChange_LastTime;
    }
}