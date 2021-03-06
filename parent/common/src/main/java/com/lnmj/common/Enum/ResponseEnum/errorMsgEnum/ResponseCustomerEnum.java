package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2020/2/25 13:46
 * @Description:
 */
public enum ResponseCustomerEnum {
    CUSTOMER_LIST_NULL(3,"客户列表不存在"),
    CUSTOMER_ADD_FAIL(3,"客户添加失败"),
    CUSTOMER_ADD_FAIL_DEFUALT_EXIT(3,"客户添加失败，已经存在默认客户"),
    CUSTOMER_NAME_EXIT(3,"客户名称已经存在"),
    CUSTOMER_DELETE_FAIL(3,"客户删除失败"),
    CUSTOMER_OUTSTORAGE_USE(3,"客户正在被出库单使用，无法删除"),
    CUSTOMER_UPDATE_FAIL(3,"客户修改失败");

    private final int code;
    private final String desc;

    ResponseCustomerEnum(int code, String desc) {
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
