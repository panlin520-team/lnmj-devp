package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class ExperiencecardProductUser extends BaseEntity {
    private Long id;

    private String experiencecardNum;

    private String memberNum;

    private String experiencecardProductCode;

    private Long experiencecardUserId;

    private String useLimit;

    private Integer totalTimes;

    private Integer useTimes;

    private String cardOrderCode;
    @Transient
    private String experiencecardProductName;
    @Transient
    private Integer experiencecardProductType;
    @Transient
    private String experiencecardProductTypeName;
    @Transient
    private Long subClassId;
    @Transient
    private String subClassName;
    @Transient
    private Double account;
}