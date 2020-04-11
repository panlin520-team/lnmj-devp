package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

/**
 * @Author: yilihua
 * @Date: 2019/9/16 11:17
 * @Description:  金额方式
 */
public enum AmountMethodEnum implements BaseEnum {
        ONE_MONTH(0, " 按月发"),
        ALL(1, "一次性发");

        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        AmountMethodEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }


}
