package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/4/23 09:53
 * @Description:
 */
public enum UpgradingWaysEnum implements BaseEnum {
    UPGRADING_WAYS_1(1, "累计储值"),
    UPGRADING_WAYS_2(2, "一次性储值"),
    UPGRADING_WAYS_3(3, "累计消费"),
    UPGRADING_WAYS_4(4, "拓客");


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

    UpgradingWaysEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
