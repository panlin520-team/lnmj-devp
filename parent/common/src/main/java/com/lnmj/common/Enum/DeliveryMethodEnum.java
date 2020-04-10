package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: renqingyun
 * @Date: 2019/4/19 16:00
 * @Description: 配送方式
 */
public enum DeliveryMethodEnum implements BaseEnum {
    ZERO(1,"物流和到店"),
    ONE(2,"仅到店"),
    TWO(3,"仅到店且免费运");

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

    DeliveryMethodEnum(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

}
