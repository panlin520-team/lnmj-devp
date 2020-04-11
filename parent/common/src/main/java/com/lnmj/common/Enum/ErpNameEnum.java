package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/4/19 16:00
 * @Description: ERP名称枚举
 */
public enum ErpNameEnum implements BaseEnum {
    ERP_NAME_VIP1(0, "VIP1"),
    ERP_NAME_VIP2(1, "VIP2"),
    ERP_NAME_VIP3(2, "VIP3"),
    ERP_NAME_VIP4(3, "VIP4"),
    ERP_NAME_VIP5(4, "VIP5"),
    ERP_NAME_VIP6(5, "VIP6"),//普通会员
    ERP_NAME_VIP_GoldenCard(6, "金卡"),
    ERP_NAME_VIP_Franchisee(7, "VIP加盟商"),
    ERP_NAME_VIP_Facilitator(8, "VIP服务商"),
    ERP_NAME_VIP_Alliance(9, "VIP联盟商");

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

    ErpNameEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
