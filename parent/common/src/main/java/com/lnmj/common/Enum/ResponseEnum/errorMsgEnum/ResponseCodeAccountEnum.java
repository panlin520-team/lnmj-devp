package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeAccountEnum {
    //用户信息
    ERROR(1, "ERROR"),
    SUCCESS(2, "SUCCESS"),
    NEED_LOGIN(3, "必须登录"),
    PARAMETER_TOKEN_ERROR(4, "参数错误，token参数有误"),
    PARAMETER_TOKEN_NULL(5, "参数错误，token不能为空"),
    PARAMETER_SYSTOKEN_ERROR(6, "sysToken不存在或者已经过期"),
    PASSWORD_UPDATE_SUCCESS(7, "密码修改成功"),
    PASSWORD_UPDATE_FAILUER(8, "密码修改成功"),
    ACCOUNT_NOT_FOUND(9, "用户不存在"),
    PASSWORD_ILLEGAL(10, "密码不合法,必须为8位以上并且包含英文和数字"),
    MOBILE_NUMBER_ILLEGAL(11, "手机号码不合法"),
    LOGIN_SUCCESS(12, "登录成功"),
    ACCOUNT_PASSWORD_ERR0R(13, "密码错误"),
    VALIDCODE_NULL(14, "验证码不能为空"),
    USERNAME_NULL(15, "用户不能为空"),
    PASSWORD_NULL(16, "密码不能为空"),
    LOGINTYPE_NULL(17, "登录方式不能为空"),
    REGISTER_NULL(18, "注册方式不能为空"),
    MOBILE_NUMBER_NULL(19, "账号手机号不能为空"),
    ACCOUNT_EXIST(20, "用户已经存在"),
    NAME_NULL(21, "姓名不能为空"),
    STORE_NULL(22, "所属店铺不能为空"),
    REGISTER_SUCCESS(23, "注册成功"),
    MEMBERSHIPLEVELID_NULL(24, "会员等级不能为空"),
    RECHARGEAMOUNT_NULL(25, "充值金额不能为空"),
    USERID_NULL(26, "用户ID不能为空"),
    NEW_PASSWORD_NULL(27, "新密码不能为空"),
    OLD_PASSWORD_NULL(28, "旧密码不能为空"),
    LOGIN_FAILURE(29, "登录失败"),
    REGISTER_FAILURE(30, "注册失败"),
    LOGINOUT_SUCCESS(31, "退出成功"),
    GET_ACCOUNT_INFO_SUCCESS(32, "获取用户信息成功"),
    GET_ACCOUNT_INFO_FAILUER(33, "用户未登录，无法获取用户信息"),
    EMAIL_EXIST(34, "Email已经存在"),
    ACCOUNT_UPDATE_SUCCESS(35, "用户修改成功"),
    ACCOUNT_UPDATE_FAILUER(35, "用户修改失败"),
    NOT_ADMIN(36, "不是管理员，无法登陆"),
    DELETE_FAILUER(37, "删除失败"),
    ADD_FAILUER(37, "添加失败"),
    UPDATE_FAILUER(37, "修改失败"),
    RECIPIENT_ADDRESS_NAME_NULL(37, "收货人姓名不能为空"),
    RECIPIENT_ADDRESS_MOBILE_NULL(37, "收货人电话不能为空"),
    RECIPIENT_ADDRESS_PROVINCEID_NULL(37, "收货地址省不能为空"),
    RECIPIENT_ADDRESS_CITYID_NULL(37, "收货地址市不能为空"),
    RECIPIENT_ADDRESS_COUNTYID_NULL(37, "收货地址区不能为空"),
    RECIPIENT_ADDRESSDETAIL_COUNTYID_NULL(37, "收货地址详情不能为空"),
    RECIPIENT_ADDRESS_ID_NULL(37, "收货地址id不能为空"),
    DELETE_RECIPIENT_ADDRESS_FAILURE(37, "地址为默认地址无法删除"),
    GET_ADDRESS_FAILUER(37, "无法获取用户地址"),
    EMAIL_ILLEGAL(37, "输入的邮箱不合法"),
    EMAIL_NULL(37, "邮箱不能为空"),
    CODE_NULL(37, "验证码不能为空"),
    CODE_ERROR(37, "验证码错误"),
    AUTHENTICATION_FAILURE(37, "认证失败"),
    MOBILE_EMAIL_NULL(37, "电话号码或邮箱不能为空"),
    PHONE_CODE_SEND_FAILURE(37, "验证码发送失败"),
    PHONE_EMAIL_ILLEGAL(37, "手机号或邮箱输入不合法"),
    TOKEN_NULL(37, "access_token不存在"),
    TOKEN_EXPIRE(37, "access_token已经过期"),
    OPERATOR_NULL(37, "操作人不能为空"),
    USER_MORE_STORE(37, "用户所属多个店铺"),
    NO_COMPANY_ID(37, "请选择公司"),
    SEX_NULL(37, "性别不能为空"),
    IDCARD_FAIL(37, "身份证输入有误"),
    //标签
    GET_ADDRESS_LIST_FAILUER(37, "获取用户列表失败"),
    GET_LABEL_FAILUER(37, "获取用户标签失败"),
    GET_LABEL_List_FAILUER(37, "获取用户标签列表失败"),
    LABEL_NAME_ISEXIST(37, "用户标签已经存在"),
    GET_LABEL_NAME_ISNOTEMPTY(37, "标签名不能为空"),
    GET_LABEL_NAME_DESCRIBE(37, "标签描述不能为空"),
    ADD_LABEL_FAILUER(37, "添加标签失败"),
    UPDATE_LABEL_FAILUER(37, "更新标签失败"),
    DELETE_LABEL_FAILUER(37, "删除标签失败"),
    DELETE_LABELCATEGORY_FAILUER(37, "删除标签类型失败"),
    ADD_LABELCATEGORY_FAILUER(37, "添加标签分类失败"),
    LABELCATEGORY_CHILDREN(37, "此标签下还有子类标签"),
    GET_LABELCATEGORY_FAILUER(37, "获取标签失败"),
    LABELCATEGORY_ID_NULL(37, "标签类型id不能为空"),
    LABELCATEGORY_IDS_NULL(37, "标签类型数组ids不能为空"),
    LABEL_ID_NULL(37, "标签类型id不能为空"),
    UPDATE_LABELCATEGORY_FAILUER(37, "更新标签类型失败"),
    LABEL_NAME_NULL(37, "标签名称不能为空"),
    LABEL_DESCRIBE_NULL(37, "标签描述不能为空"),
    LABEL_CATEGORY_ERROR(37, "标签类别有误"),
    LABEL_PARENTID_ISNULL(37, "标签父id不能为空"),
    LABEL_NUMBER_NULL(37, "标签数量不能为空"),
    LABELCATEGORY_NUMBER_NULL(37, "标签数量不能为空"),
    LABELCATEGORY_NAME_ISEXIST(37, "标签名称已经存在"),
    LABELCATEGORY_NAME_NULL(37, "标签类型名称不能为空"),
    LABELCATEGORY_PARENTID_ERROR(37, "标签类型名称不能为空"),
    LABELCATEGORY_DESCRIPTION_NULL(37, "标签类型描述不能为空"),


    //会员
    MEMBER_SHIP_LEVEL_ID_NULL(37, "会员等级Id不能为空"),
    MEMBER_SHIP_LEVEL_NAME_NULL(37, "会员等级名称不能为空"),
    ERP_NAME_NULL(37, "ERP名称不能为空"),
    AUTO_UPGRADE_NULL(37, "是否自动升级不能为空"),
    STANDARD_AMOUNT_NULL(37, "会员标准不能为空"),
    UPGRADE_STANDARD_AMOUNT_NULL(37, "升级标准(金额)不能为空"),
    UPGRADE_STANDARD_FANS_NULL(37, "升级标准(粉丝)不能为空"),
    UPGRADE_WAYS_NULL(37, "会员升级模式不能为空"),
    PRICE_DIFFERENCE_ALLOT_NULL(37, "差价分配不能为空"),
    BONUS_ALLOT_NULL(37, "奖金分配不能为空"),
    NURSING_BONUS_ALLOT_NULL(37, "护理奖金不能为空"),
    NURSING_DIFFERENCE_ALLOT_NULL(37, "护理差价分配不能为空"),
    FIRST_RECHARGE_BONUS_NULL(37, "首次充值奖金不能为空"),
    SHARED_ACCOUNT_NULL(37, "共享金账户不能为空"),
    GUEST_ACCOUNT_NULL(37, "请客账户不能为空"),
    ISDIVISION_NULL(37, "是否为股东"),
    BESTIE_INTRODUCE_AMOUNT_NULL(37, "闺蜜卡转介绍金额不能为空"),
    SCOPE_OF_USE_NULL(37, "会员等级使用范围不能为空"),
    CREATE_OPERATOR_NULL(37, "创建人不能为空"),
    MODIFY_OPERATOR_NULL(37, "修改人不能为空"),
    RECHARGE_AMOUNT_NULL(37, "充值/消费金额不能为空"),
    RECHARGE_AMOUNT_LESS_ZERO(37, "充值/消费金额不能小于0"),
    NO_MEMBER_UP_TYPE(37, "没有此种升级方式"),
    AMOUNT_NOT_UP_STANDAR(37, "充值不达标"),
    CONSUMPTION_NOT_UP_STANDAR(37, "消费不达标"),
    FANS_NOT_UP_STANDAR(37, "粉丝不达标"),
    NOT_AUTO_UPGRADE(37, "等级不是自动升级"),
    HIGHT_MEMBER(37, "最高等级"),
    CAN_NOT_DELETE_MEMBER_SHIP(37, "等级有用户不能删除"),
    NOT_MEMBER_SHIP_LEVEL(37, "找不到会员等级"),
    MEMBER_SHIP_REPEAT(37, "会员等级重复"),
    ENUM_NAME_NULL(37, "ENUM字段名字不能为空"),
    ENUM_NAME_ERROR(37, "ENUM字段名字错误"),
    NORMAL_USER_ONLINE_CAN_NOT_DO(37, "线上普通用户不能操作"),
    NORMAL_USER_OFFLINE_CAN_NOT_DO(37, "线下普通用户不能操作"),
    ERP_NAME_WRONGFUL(37, "ERPName不合法"),
    UPGRADING_WAYS_WRONGFUL(37, "充值升级方式不合法"),
    MEMBER_SHIP_LEVEL_NAME_REPEAT(37, "会员等级重复"),
    MEMBER_SHIP_LEVEL_TYPE_WRONGFUL(37, "会员升级类型不合法"),
    NOT_MEMBER_SHIP_LEVEL_UPDRADING_RECORDS(37, "找不到会员等级升级记录"),
    NOT_MEMBER_AMOUNT_UPDRADING_RECORDS(37, "找不到会员金额记录"),
    STORE_MEMBER_NULL(37, "门店会员不存在"),
    MEMBER_CODE_NULL(37, "会员号码不能为空"),
    MEMBER_NOT_UP(37, "会员暂未升级"),
    ;


    private final int code;
    private final String desc;

    ResponseCodeAccountEnum(int code, String desc) {
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
