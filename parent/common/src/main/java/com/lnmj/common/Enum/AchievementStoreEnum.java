package com.lnmj.common.Enum;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 16:14
 * @Description: 业绩统计方式
 */
public enum AchievementStoreEnum {
    PERSONAL(0, "个人业绩"),
    STORE(1, "全店业绩");
    private final int code;
    private final String desc;

    AchievementStoreEnum(int code, String desc) {
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
