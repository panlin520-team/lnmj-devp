package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author renqingyun
 * @Date: 2019/7/13 16:14
 * @Description:
 */
public enum ProductStatusEnum implements BaseEnum {
    ON(1,"已上架"),
    DOWN (2,"已下架"),
    DELETE (0,"已删除");

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

    ProductStatusEnum(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

}
