package com.lnmj.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@Data
public class Score extends BaseEntity {
    private Long scoreID;

    private String scoreName;

    private Integer scoreMode;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long scoreAchievementID;

    private Long scorePostID;

    private BigDecimal scoreHigh;

    private BigDecimal scoreLow;

    private BigDecimal scoreDefault;

    private BigDecimal scoreBase;

    private BigDecimal scoreProportion;

    @Transient
    private String postName;
    @Transient
    private String achievementName;//业绩名称
}