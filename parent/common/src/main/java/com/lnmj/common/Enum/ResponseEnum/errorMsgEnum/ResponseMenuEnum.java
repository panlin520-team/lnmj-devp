package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseMenuEnum {
    CAN_NOT_GET_NODE_ID(3,"无法获取节点id"),
    CAN_NOT_GET_NODE_OBJ(3,"无法获取节点对象"),
    MENU_EXIT(3,"当前菜单名已存在"),
    CAN_NOT_GET_PARENT_ID(3,"无法获取父级id"),
    THREE_MENU_CAN_NOT_ADD_CHILD_NODE(3,"3级菜单不可再添加子菜单"),
    ONE_MENU_NAME_CAN_NOT_NUMBER(3,"1级菜单的名字不可为纯数字"),
    DELETE_FAIL_NO_RECORD(3,"删除失败,无法找到该记录"),
    DELETE_FAIL(3,"删除失败"),
    SYSTEM_ERROR(3,"系统错误");
    private final int code;
    private final String desc;

    ResponseMenuEnum(int code, String desc) {
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
