package com.lnmj.common.Enum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 10:27
 * @Description:
 */
//     请求返回状态枚举
public enum AckCodeTypeEnum {
    SUCCESS(1, "Success"),
    FAILURE(2, "Failure"),
    WARNING(3, "Warning"),
    PARTIAL_FAILURE(4, "PartialFailure");
    private final int code;
    private final String desc;

    AckCodeTypeEnum(int code, String desc) {
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
