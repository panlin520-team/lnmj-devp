package com.lnmj.common.Enum.base;

/**
 * @Auther: yilihua
 * @Date: 2019/4/22 16:25
 * @Description: 基本的枚举类
 */
public interface BaseEnum {
    Integer getCode();
    String getDesc();
    void setCode(Integer code);
    void setDesc(String desc);

}
