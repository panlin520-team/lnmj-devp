package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum AchievementTypeEnum implements BaseEnum {
    LAODONG_YEJI (1, "劳动业绩"),
    XIAOSHOU_YEJI(2, "销售业绩"),
    CUZHI_YEJI (3, "储值业绩");
    private Integer code;
    private String desc;
    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public void setCode(Integer code) {
        this.code = code;
    }
    @Override
    public String getDesc() {
        return desc;
    }
    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    AchievementTypeEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
