package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeBeauticianEnum {

    BEAUTICIAN_LIST_NULL(3,"员工列表不存在"),
    BEAUTICIAN_NULL(3,"员工不存在"),
    STAFFNUNBER_NULL(3,"员工编号不存在"),
    POST_LEVEL_ID_NULL(3,"职位等级不能为空"),
    BEAUTICIAN_ID_NULL(3,"美容师ID不能为空"),
    BEAUTICIAN_ADD_FAIL(3,"美容师添加失败"),
    GROUPID_NULL_FENZUYEJI(3,"所选职位为分组业绩，请选择分组"),
    POSTID_NULL(3,"美容师所属职位不能空"),
    SORT_NULL(3,"美容师排序不能为空"),
    GRADEID_NULL(3,"美容师等级不能为空"),
    STOREID_NULL(3,"美容师所属店铺不能为空"),
    ENTRYTIME_NULL(3,"美容师入职时间不能为空"),
    GENDER_NULL(3,"美容师性别不能为空"),
    POST_ID_NULL(3,"职位不能为空"),
    MOBILE_NULL(3,"美容师电话不能为空"),
    MOBILE_ILLEGAL(3,"美容师电话格式错误"),
    STAFF_NAME_IS_EXIST(3,"员工名字已经存在"),
    MOBILE_EXIST(3,"此电话已经存在"),
    MOBILE_USER_NULL(3,"指定手机号码用户不存在"),
    UPDATE_BEAUTICIAN_FAIL(3,"修改美容师失败"),
    DELETE_BEAUTICIAN_FAIL(3,"删除美容师失败"),
    ORDER_WORK_FAIL(3,"排班失败"),
    ORDERWORKDATE_NULL(3,"排班日期不能为空"),
    ORDERWORK_NULL_CANNOT_DELETE(3,"此美容师暂未排班，无法删除"),
    TIMENODES_NULL(3,"排班时间节点不能为空"),
    ORDERWORK_NULL(3,"此美容师暂无排班"),
    ORDERWORK_EXIST(3,"此美容师已经有排班了"),
    PROJECT_NULL(3,"授权项目不能为空"),
    ADD_PROJECT_NULL(3,"未进行任何授权"),
    BEAUTICIAN_PROJECT_NULL(3,"此美容师暂无授权项目"),
    SELECT_POST_FAIL(3,"查询职位失败"),
    SELECT_POST_CATEGORY_FAIL(3,"查询职位分类失败"),
    SELECT_GROUP_FAIL(3,"查询分组失败"),
    DELETE_FAIL_USING_POST(3,"此职位正在使用中，无法删除"),
    DELETE_FAIL_USING_GROUP(3,"此分组正在使用中，无法删除"),
    GROUP_NAME_IS_EXIST(3,"分组名称已经存在"),
    GROUP_LEADER_IS_EXIST(3,"所选组长已经担任了组长"),
    POST_LEVEL_LIST_NULL(3,"职位等级列表不能为空，职位等级列表不能为空"),
    POST_PART_TIME_POST_SAME(3,"所属职位与兼职职位不能相同"),
    POST_PART_NO_BASIC_SALARY(3,"员工为兼职无法选择计算底薪"),
    ADD_POST_FAIL(3,"添加职位失败"),
    POST_NO_PROJECTS(3,"此职位下还没有授权项目");
    private final int code;
    private final String desc;

    ResponseCodeBeauticianEnum(int code, String desc) {
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
