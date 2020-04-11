package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeAuthEnum {


    AUTH_BUTTON_NULL(3,"按钮级权限数据不存在"),
    AUTH_MENU_NULL(3,"菜单级权限数据不存在"),
    ROLE_CODE_NULL(3,"角色code不能为空"),
    PERMISSION_NULL(3,"权限code不能为空"),
    PERMISSION_ROLE_NULL(3,"该角色不具备这样的权限"),
    SET_ROLE_PERMI_NULL_FAIL(3,"清空角色权限失败"),
    SELECT_ROLE_FAIL(3,"角色不存在"),
    ROLE_DELETE_FAIL(3,"角色删除失败"),
    ROLE_NAME_NULL(3,"角色名称不能为空"),
    ROLE_NAME_EXIT(3,"角色名称已经存在"),
    USER_ROLE_NULL(3,"用户暂无任何权限"),
    SET_USER_ROLES_NULL_FAIL(3,"清空用户角色失败"),
    ROLE_MENU_NOT_EXIST(3,"菜单不存在，请登录超级管理员添加菜单"),
    USER_NO_ROLES(3,"此用户还未分配角色，请联系超级管理员添加"),
    ROLES_NO_MENU(3,"此用户拥有的角色还未分配菜单权限，请联系管理员添加");
    private final int code;
    private final String desc;

    ResponseCodeAuthEnum(int code, String desc) {
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
