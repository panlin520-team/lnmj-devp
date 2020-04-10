package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 17:24
 * @Description:  赠送状态
 */
public enum AppointmentTimeStatusEnum implements BaseEnum {

    AREST(1, "休息"),
    IDLE(2, "空闲"),
    BUSY(3, "忙碌"),
    PAST(4, "过期"),
    CHECK(5, "选中"),
    OVERLAP(6, "重叠");

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

    AppointmentTimeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
