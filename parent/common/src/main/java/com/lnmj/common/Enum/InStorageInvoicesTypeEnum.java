package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 入库单据类型
 */
public enum InStorageInvoicesTypeEnum implements BaseEnum {

    STANDARD_PURCHASE_INSTORAGE(1, "标准采购入库"),
    REBATE_PURCHASE_INSTORAGE(2, "返利采购入库"),
    SCATTERED_PURCHASE_INSTORAGE(3, "零散采购入库"),
    OUTSOURCE_INSTORAGE(4, "委外入库单"),
    DISTRIBUTION_INSTORAGE(5, "分销购销入库"),
    CAPITAL_INSTORAGE(6, "资产入库"),
    COST_INSTORAGE(7, "费用入库"),
    WMI_INSTORAGE(8, "VMI入库"),
    STANDARD_OTHER_INSTORAGE(11, "标准其他入库"),
    ;

    private Integer code;
    private String desc;

    InStorageInvoicesTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
    }}
