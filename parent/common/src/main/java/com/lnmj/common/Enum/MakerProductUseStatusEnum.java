package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 17:24
 * @Description:  使用状态
 */
public enum MakerProductUseStatusEnum implements BaseEnum {

    SEND(1, "已发货"),
    APPOINTMENT(2, "预约"),
    RETURN(3, "退货"),
    CONFIRM(4, "确认"),
    CANCEL(5, "取消"),
    END(6, "过期"),
    TO_SEND(7, "待发货"),
    RECEIVED(8, "已收货"),
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

    MakerProductUseStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
