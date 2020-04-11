package com.lnmj.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:33
 * @Description: 业绩表-职位
 */
@Data
public class PerformancePost extends BaseEntity {
    private Long id;

    private String achievementPostName;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long achievementID;

    private Long achievementPostCategoryID;

    private Integer achievementMethods;

    private Integer achievementInterval;

    private Integer achievementStore;

    private Long achievementStoreId;

    private Integer isBasicSalary;

    private Integer isPartTime;

    @Transient
    private List<Ladder> ladderList;



    @Transient
    private String achievementPostCategoryName;
    @Transient
    private String achievementMethodsName;
    @Transient
    private String achievementStoreName;
    @Transient
    private Double ladderValue;
    @Transient
    private Double singlePrize;
    @Transient
    private Double zhongyejiRatio;
    @Transient
    private String achievementCategoryName;

    @Transient
    private String achievementStoreIdName;
    @Transient
    private String achievementName;
}