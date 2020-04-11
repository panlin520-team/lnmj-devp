package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 出库单据类型
 */
public enum OutStorageInvoicesTypeEnum implements BaseEnum {

    //单据类型(销售出库：标准销售出库、寄售出库、零售出库、分销购销销售出库、WMI出库、B2C销售出库
    //其他出库：标准其他出库、资产出库、VMI出库、费用物料出库)
    STANDARD_MARKET_OUTSTORAGE(1, "标准销售出库"),
    CONSIGN_OUTSTORAGE(2, "寄售出库"),
    RETAIL_OUTSTORAGE(3, "零售出库"),
    DISTRIBUTION_MARKET_OUTSTORAGE(4, "分销购销销售出库"),
    WMI_MARKET_OUTSTORAGE(5, "WMI出库"),
    B2C_MARKET_OUTSTORAGE(6, "B2C销售出库"),
    STANDARD_OTHER_OUTSTORAGE(11, "标准其他出库"),
    CAPITAL_OUTSTORAGE(12, "资产出库"),
    WMI_OTHER_OUTSTORAGE(13, "VMI出库"),
    COST_ITEM_OUTSTORAGE(14, "费用物料出库"),
    ;

    private Integer code;
    private String desc;

    OutStorageInvoicesTypeEnum(Integer code, String desc) {
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
