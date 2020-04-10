package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;


/**
 * @Author: yilihua
 * @Date: 2019/5/8 10:43
 * @Description: 图片类型枚举类
 */
public enum PictureTypeEnum implements BaseEnum {
    PNG(1,"PNG"),
    JPG(2,"JPG"),
    BMP(3,"BMP"),
    JPEG(4,"JPEG"),
    PSD(0,"PSD"),
    JIF(5,"JIF");

    private  Integer code;
    private  String desc;
    PictureTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
