package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 17:24
 * @Description:
 */
public enum ProductTypeDistinguishEnum implements BaseEnum {
    PRODUCT_EFFECT(1, "商品功效"),
    PRODUCT_BRAND(2, "商品品牌"),
    PRODUCT_CATEGORY(3, "商品品类"),
    PRODUCT_KIND(4, "商品种类"),
    PRODUCT_SITE(5, "商品产地"),
    PRODUCT_DIVISION(6, "商品专区");


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

    ProductTypeDistinguishEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
