package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;

@Data
public class Parameter extends BaseEntity {

    private Long parameterId; //  参数id
    private Long parameterTypeId; //   参数类型
    private String parameterName; //  参数名称
    private String parameterValue; // 参数值
    private Integer parameterSort; //  排序(0-9999)

}
