package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 库存方向
 */
public enum InventoryWayEnum implements BaseEnum {

    NORMAL(1, "GENERAL"),
    RETURN(2, "RETURN"),
    ;

    private Integer code;
    private String desc;

    InventoryWayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
