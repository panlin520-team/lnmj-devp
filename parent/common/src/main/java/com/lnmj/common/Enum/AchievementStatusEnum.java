package com.lnmj.common.Enum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 业绩统计方式
 */
public enum AchievementStatusEnum {
    NORMAL(1, "正常"),
    DELETE(2, "删除");
    private final int code;
    private final String desc;

    AchievementStatusEnum(int code, String desc) {
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
