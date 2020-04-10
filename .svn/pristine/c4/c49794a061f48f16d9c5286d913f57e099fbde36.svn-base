package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description: 客户评测
 */
public enum ResponseCodeEvaluatingEnum {
    //客户评测
    EVALUATING_NULL(1, "客户评测为空"),
    EVALUATING_ID_NOT_NULL(1, "客户评测id不能为空"),
    NOT_FIND_EVALUATING(1, "客户评测不存在"),
    CREATEOPERATOR_NOT_NULL(1, "添加人不能为空"),
    MODIFYOPERATOR_NOT_NULL(1, "修改人不能为空"),
    RECHARGE_TYPE_EVALUATING_EXIST(1, "充值测评已经存在"),
    UPDATE_FAILED(1, "更新失败"),
    INSERT_FAILED(1, "插入失败"),
    DELETE_FAILED(1, "删除失败"),
    //客户评测阶梯
    EVALUATING_LEVEL_NULL(1, "客户评测级别为空"),
    EVALUATING_LEVEL_ID_NOT_NULL(1, "客户评测级别id不能为空"),
    NOT_FIND_EVALUATING_LEVEL(1, "客户评测级别不存在"),
    //客户评测明细
    EVALUATING_DETAILED_NULL(1, "客户评测明细为空"),
    EVALUATING_DETAILED_ID_NOT_NULL(1, "客户评测明细id不能为空"),
    NOT_FIND_EVALUATING_DETAILED(1, "客户评测明细不存在"),
    //客户评测统计
    STATISTICDATE_EROOR(1, "统计日期有误"),
    USER_ID_NULL(1, "用户ID不能为空"),
    USER_ERROR(1, "用户没找到"),
    LIST_IS_NULL(1, "统计列表为空"),
    USER_EVALUATING_IS_NULL(1, "客户无测评等级"),
    //客户评测等级
    LEVEL_INFO_ISNULL(1,"等级信息为空"),
    ADD_LEVELINFO_FAIL(1,"新增等级信息失败"),
    UPDATE_LEVELINFO_FAIL(1,"新增等级信息失败"),
    DELETE_LEVELINFO_FAIL(1,"新增等级信息失败"),
    ;


    private final int code;
    private final String desc;

    ResponseCodeEvaluatingEnum(int code, String desc) {
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
