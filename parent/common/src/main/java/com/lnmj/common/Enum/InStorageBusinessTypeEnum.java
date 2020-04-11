package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 采购入库业务类型（和入库的单据类型对应）
 */
public enum InStorageBusinessTypeEnum implements BaseEnum {

    //业务类型（标准采购、标准委外、分销购销、资产采购、费用采购、WMI采购）
    STANDARD_PURCHASE(1, "标准采购"),
    STANDARD_OUTSOURCE(4, "标准委外"),
    DISTRIBUTION(5, "分销购销"),
    CAPITAL_PURCHASE(6, "资产采购"),
    COST_PURCHASE(7, "费用采购"),
    WMI_PURCHASE(8, "WMI采购"),
    ;

    private Integer code;
    private String desc;

    InStorageBusinessTypeEnum(Integer code, String desc) {
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
