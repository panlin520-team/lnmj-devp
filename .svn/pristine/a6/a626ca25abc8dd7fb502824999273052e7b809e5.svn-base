package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/8/15 16:23
 * @Description: 会员使用范围
 */
public enum UseMethodEnum implements BaseEnum {
    SCOPE_OF_USE_ALL(1, "可请多人"),    //可接受多人请客
    SCOPE_OF_USE_NEW(2, "只可请一人"),    //规则有效期内，只接受一人请客
    SCOPE_OF_USE_OLD(3, "只可请一人一次"),   //账号只接受一人请客一次
    ;


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

    UseMethodEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
