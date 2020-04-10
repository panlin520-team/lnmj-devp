package com.lnmj.common.Enum.ResponseEnum;



/**
 * @Author: panlin
 * @Date: 2019/5/10 12:00
 * @Description: 图片枚举
 */
public enum ImageTypeEnum  {
    PRODUCT("product","商品图片"),
    USERHEADER ("userheader","用户头像图片"),
    PRODUCTACTIVITYIMAGE("productActivityImage","商品活动图"),
    SERVICEPRODUCT("serviceproduct","服务商品图"),
    SERVICEPRODUCTACTIVITYIMAGE("serviceproductActivityImage","服务商品活动图"),
    PRODUCTTYPE("productType","商品分类图"),
    BEAUTICIAN_HEAD("beauticianHeadType","美容师头像图片"),
    BACKUP("backup","备份文件"),
    MAKERPRODUCT("makerProduct","创客商品"),
    EXPERIENCECARD("experiencecardType","体验卡图片"),
    FUWENBENPIC("fuwenbenpic","富文本图片"),

    ;
    private String code;
    private String desc;

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
    }

    ImageTypeEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

}
