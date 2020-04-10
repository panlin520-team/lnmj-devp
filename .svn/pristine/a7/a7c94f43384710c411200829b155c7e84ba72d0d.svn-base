package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2020/2/25 13:46
 * @Description:
 */
public enum ResponseDepartmentEnum {
    DEPARTMENT_LIST_NULL(3,"部门列表不存在"),
    DEPARTMENT_ADD_FAIL(3,"部门添加失败"),
    DEPARTMENT_QITA_INSTORAGE_USE(3,"部门正被其他入库单使用，无法删除"),
    DEPARTMENT_DELETE_FAIL(3,"部门删除失败"),
    DEPARTMENT_ADD_FAIL_DEFUALT_EXIT(3,"部门添加失败，已经存在默认部门"),
    DEPARTMENT_NAME_EXIT(3,"部门名称已经存在"),
    DEPARTMENT_UPDATE_FAIL(3,"部门修改失败");

    private final int code;
    private final String desc;

    ResponseDepartmentEnum(int code, String desc) {
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
