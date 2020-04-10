package com.lnmj.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:33
 * @Description: 业绩表
 */
@Data
public class Performance extends BaseEntity {
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long achievementID;

    private Long achievementIndustryID;

    private String achievementName;

    private Integer achievementType;

    private Boolean isStaffReview;


    @Transient
    private String achievementIndustryName;
    @Transient
    private String achievementStoreName;

    @Transient
    private String achievementOrderTypeName;

    @Transient
    private List<PerformancePost> performancePostList;


}