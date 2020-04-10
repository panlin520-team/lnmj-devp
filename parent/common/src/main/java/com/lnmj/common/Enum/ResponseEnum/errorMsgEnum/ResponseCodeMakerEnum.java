package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Author: yilihua
 * @Date: 2019/6/10 14:19
 * @Description:  创客参数
 */
public enum ResponseCodeMakerEnum {
    //创客等级字段
    MAKER_MEMBER_ID_NULL(1, "创客等级ID不能为空"),
    MAKER_MEMBER_NULL(1, "创客等级为空"),
    //创客用户字段
    MAKER_USER_ID_NULL(1, "创客用户ID不能为空"),
    MAKER_USER_NULL(1, "创客用户为空"),
    //创客产品字段
    MAKER_PRODUCT_ID_NULL(1, "创客产品ID不能为空"),
    MAKER_PRODUCT_NULL(1, "创客产品为空"),
    MAKER_PRODUCT_DETAIL_NULL(1, "创客产品项目为空"),
    MAKER_PRODUCT_DETAIL_ID_NULL(1, "创客产品项目ID不能为空"),
    NOT_IMAGE_TYPE(1, "图片类型不能为空"),
    MAKER_PRODUCT_NOT_FIND(1, "创客产品不存在"),
    //创客订单字段
    ENUM_NAME_NULL(37, "ENUM字段名字不能为空"),
    ENUM_NAME_ERROR(37, "ENUM字段名字错误"),
    MAKER_ORDER_NULL(37, "创客订单没找到"),
    MAKER_ORDER_ID_NULL(37, "创客订单ID不能为空"),
    MAKER_ORDER_USE_NULL(37, "订单使用没找到"),
    MAKER_ORDER_USE_ID_NULL(37, "订单使用ID不能为空"),
    //基本字段
    NOT_UPDATE_OPPERATOR(1, "修改人不能为空"),
    NOT_CREATE_OPPERATOR(1, "创建人不能为空"),
    ;

    private final int code;
    private final String desc;

    ResponseCodeMakerEnum(int code, String desc) {
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
