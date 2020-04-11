package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: panlin
 * @Date: 2019/7/1 16:00
 * @Description: 店铺护理类型枚举
 */
public enum NursingTypeEnum implements BaseEnum {
    BEAUTY(1,"美容"),
    HAIRDRESSING (2,"美发"),
    BEAUTY_HAIRDRESSING(3,"美容和美发");

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

    NursingTypeEnum(Integer code, String desc){
        this.code=code;
        this.desc=desc;
    }

}
