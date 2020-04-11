package com.lnmj.account.entity;

import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Map;

@Data
public class Poslabel extends BaseEntity {
    private Long labelId;

    private String labelName;

    private String labelDescribe;

    private Integer orderType;

    @Transient
    private Map map;

}