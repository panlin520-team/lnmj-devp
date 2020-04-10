package com.lnmj.common.Enum.k3Enum;

import com.lnmj.common.Enum.base.BaseEnum;
import lombok.Data;

/**
 * 〈舍入类型〉
 *
 * @Author renqingyun
 * @create 2019/12/5
 */

public enum UnitGroupTypeEnum {
    CAPACITY("CAPACITY", "容量"),
    TIME("TIME", "时间"),
    QUANTITY("QUANTITY", "数量"),
    LENGTH("LENGTH", "长度"),
    WEIGHT("WEIGHT", "重量"),
    ELECTRONIC("ELECTRONIC", "资产单位_电子设备"),
    BUILDING("BUILDING", "资产单位_房屋建设"),
    MACHINE("MACHINE", "资产单位_机器设备"),
    TRANSPORT("TRANSPORT", "资产单位_运输设备");

    private String code;
    private String desc;


    UnitGroupTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
