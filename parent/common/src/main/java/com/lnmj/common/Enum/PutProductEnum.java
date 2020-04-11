package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/4/19 16:00
 * @Description: ERP名称枚举
 */
public enum PutProductEnum implements BaseEnum {
    ZERO(0,"下架"),
    ONE(1,"上架");

    private Integer code;
    private String desc;

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
    }

    PutProductEnum(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

}
