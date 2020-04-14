package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeStoreEnum {

    STOREID_ISNOTNULL(3,"门店id不能为空"),
    STORECODE_ISNOTNULL(3,"门店code不能为空"),
    STORECODE_AND_ID_ISNOTNULL(3,"门店code和id不能同时为空"),
    STORELIST_ISNOTEXICT(3,"门店不存在"),
    STORECATEGORYLIST_ISNOTEXICT(3,"门店分类列表不存在"),
    ADD_STORE_FAIL(3,"门店添加失败"),
    STORE_NAME_EXIT(3,"店铺名称已经存在"),
    STORE_CODE_EXIT(3,"店铺编号已经存在"),
    STORE_K3NUMBER_EXIT(3,"店铺k3编号已经存在"),
    STORE_UPDATE_FAIL(3,"店铺信息修改失败"),
    DELETE_STORE_FAIL(3,"店铺删除失败"),
    MOBILE_ILLEGAL(3,"电话号码格式不正确"),
    STORE_QUERY_CONDITION_NULL(3,"门店查询条件不能为空"),
    STORE_STORECATEGORYID(3,"门店分类不能为空"),
    STORE_STORECATEGORY_NAME_EXIST(3,"门店分类名称已经存在"),
    STORE_INDUSTRYID(3,"门店所属行业不能为空"),
    MEMO_NUM_NULL(3,"门店水单不存在"),
    MEMO_START_NUM(3,"门店水单号起始计数不能为空"),
    MEMO_END_NUM(3,"门店水单号结束计数不能为空"),
    MEMO_SCOPE_ORDER_NOT_EXIST(3,"水单范围内订单不存在");
    private final int code;
    private final String desc;

    ResponseCodeStoreEnum(int code, String desc) {
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
