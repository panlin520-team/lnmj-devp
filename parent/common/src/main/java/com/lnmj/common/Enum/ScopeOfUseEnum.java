package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/8/15 16:23
 * @Description: 会员使用范围
 */
public enum ScopeOfUseEnum implements BaseEnum {
    SCOPE_OF_USE_ALL(1, "所有用户"),
    SCOPE_OF_USE_NEW(2, "新用户"),
    SCOPE_OF_USE_OLD(3, "老用户"),
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

    ScopeOfUseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
