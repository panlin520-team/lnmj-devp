package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/4/19 16:00
 * @Description: 会员形成途径
 */
public enum MemberAddTypeEnum implements BaseEnum {

    POS_ADD(1, "pos端添加"),
    MALL_ADD(2, "商城添加");


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

    MemberAddTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
