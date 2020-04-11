package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description: 商品大类
 */
public enum ResponseCodeCommodityTypeEnum {
    //商品大类
    COMMODITY_TYPE_NULL(1, "商品大类为空"),
    COMMODITY_TYPE_ID_NOT_NULL(1, "id不能为空"),
    NOT_FIND_COMMODITY_TYPE(1, "商品大类不存在"),
    CREATEOPERATOR_NOT_NULL(1, "添加人不能为空"),
    MODIFYOPERATOR_NOT_NULL(1, "修改人不能为空"),
    //商品小类
    SUBCLASS_NULL(1, "商品小类为空"),
    SUBCLASS_ID_NOT_NULL(1, "id不能为空"),
    NOT_FIND_SUBCLASS(1, "商品小类不存在"),
    ;


    private final int code;
    private final String desc;

    ResponseCodeCommodityTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
