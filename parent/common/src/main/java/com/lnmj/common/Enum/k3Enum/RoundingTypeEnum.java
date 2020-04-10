package com.lnmj.common.Enum.k3Enum;

import com.lnmj.common.Enum.base.BaseEnum;
import org.springframework.context.annotation.Bean;

/**
 * 〈舍入类型〉
 *
 * @Author renqingyun
 * @create 2019/12/5
 */

public enum RoundingTypeEnum  {
    ROUNDING("1", "四舍五入"),
    ROUNDING_OFF("2", "舍位"),
    ROUNDING_CARRY("3", "进位");

    private String code;
    private String desc;

    RoundingTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
