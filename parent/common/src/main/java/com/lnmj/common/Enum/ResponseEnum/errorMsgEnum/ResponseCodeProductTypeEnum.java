package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeProductTypeEnum {

    //商品分类
    //商品专区
    PRODUCT_DIVISION_NOT_FIND(9,"商品专区没找到"),
    PRODUCT_DIVISION_IS_USE(9,"商品专区在使用"),
    NOT_PRODUCT_DIVISION(9,"没有商品专区"),
    DIVISION_ID_NULL(9,"专区id不能为空"),

    //商品品牌
    PRODUCT_BRAND_NOT_FIND(9,"商品品牌没找到"),
    PRODUCT_BRAND_IS_USE(9,"商品品牌在使用"),
    NOT_PRODUCT_BRAND(9,"没有商品品牌"),
    BRAND_ID_NULL(9,"品牌id不能为空"),
    //商品种类
    PRODUCT_KIND_NOT_FIND(9,"商品种类没找到"),
    PRODUCT_KIND_IS_USE(9,"商品种类在使用"),
    NOT_PRODUCT_KIND(9,"没有商品种类"),
    KIND_ID_NULL(9,"种类id不能为空"),
    //商品品类
    PRODUCT_CATEGORY_NOT_FIND(9,"商品品类没找到"),
    PRODUCT_CATEGORY_IS_USE(9,"商品品类在使用"),
    NOT_PRODUCT_CATEGORY(9,"没有商品品类"),
    CATEGORY_ID_NULL(9,"品类id不能为空"),
    //商品位置
    PRODUCT_SITE_NOT_FIND(9,"商品位置没找到"),
    PRODUCT_SITE_IS_USE(9,"商品位置在使用"),
    NOT_PRODUCT_SITE(9,"没有商品位置"),
    SITE_ID_NULL(9,"位置id不能为空"),
    //商品功效
    PRODUCT_EFFECT_NOT_FIND(9,"商品功效没找到"),
    PRODUCT_EFFECT_IS_USE(9,"商品功效在使用"),
    NOT_PRODUCT_EFFFECT(9,"没有商品功效"),
    EFFFECT_ID_NULL(9,"功效id不能为空"),
    //商品分类显示
    PRODUCT_TYPE_DISPLAY_NAME_NULL(9,"商品分类显示名称不能为空"),
    PRODUCT_TYPE_DIAPLAY_IS_USE(9,"商品分类显示在使用"),
    PRODUCT_TYPE_DISPLAY_ID_NULL(9,"商品分类显示id不能为空"),
    //商品类型
    NOT_PRODUCT_TYPE(3,"没有商品类型"),
    NOT_PRODUCT_TYPE_ID(3,"商品类型id不能为空"),

    NOT_PRODUCT_TYPE_NAME(3,"没有商品分类名"),
    NOT_PRODUCT_TYPE_ORDER(3,"没有商品分类排序"),
    NOT_PRODUCT_TYPE_DISPLAY(3,"没有商品分类显示"),
    NOT_PRODUCT_TYPE_PICCTURE(3,"没有商品分类图标"),
    NOT_INTEGRALRATIO_SERVICE(3,"没有积分比例(服务商)"),
    NOT_INTEGRALRATIO_UNION(3,"没有积分比例(联盟商)"),
    NOT_RETAILPRICE_VIP1(3,"积分比例VIP1"),
    NOT_RETAILPRICE_VIP2(3,"积分比例VIP2"),
    NOT_RETAILPRICE_VIP3(3,"积分比例VIP3"),
    NOT_RETAILPRICE_VIP4(3,"积分比例VIP4"),
    NOT_RETAILPRICE_VIP5(3,"积分比例VIP5"),
    NOT_CREATE_OPPERATOR(3,"创建人不能为空"),
    NOT_UPDATE_OPPERATOR(3,"修改人不能为空"),
    UNSUPPORTED_PICTURE_TYPE(3,"不支持的图片类型"),
    INSERT_PRODUCT_TYPE_FAILED(3,"新增商品分类失败"),
    PRODUCT_KIND_NAME_NULL(3,"商品种类名不能为空"),
    PRODUCT_KIND_ID_NULL(37,"商品种类Id不能为空"),
    ACCOUNT_NOT_FOUND(9,"用户不存在"),

    PRODUCT_TYPE_CATEGORY_ID_NULL(9,"商品分类类别id不能为空"),
    PRODUCT_TYPE_CATEGORY_NAME_NULL(9,"商品分类类别名不能为空"),
    PRODUCT_TYPE_ID_NULL(9,"商品分类id不能为空"),
    UPDATE_PRODUCT_TYPE_FAILED(9,"修改商品分类失败"),
    EXPORT_PRODUCT_TYPE_FAILED(9,"商品分类导出失败"),
    NOT_IMAGE_TYPE(9,"没有图片类型"),
    NOT_DISTINGUISH_ID(9,"没有分类区分字段"),
    ;


    private final int code;
    private final String desc;

    ResponseCodeProductTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
