package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum EnterStoreChannelEnum implements BaseEnum {
    FRIEND(1, "朋友介绍"),
    ADVERTISING_FLYER(2, "广告传单"),
    TV_FLYER(3, "电视广告"),
    WORD_MOUTH (4, "口碑");
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

    EnterStoreChannelEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
