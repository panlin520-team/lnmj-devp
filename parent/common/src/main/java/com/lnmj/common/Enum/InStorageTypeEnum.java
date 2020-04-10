package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 入库方式
 */
public enum InStorageTypeEnum implements BaseEnum {

    PURCHASE_INSTORAGE(1, "采购入库"),
    OTHER_INSTORAGE(2, "其他入库"),
    ;

    private Integer code;
    private String desc;

    InStorageTypeEnum(Integer code, String desc) {
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
