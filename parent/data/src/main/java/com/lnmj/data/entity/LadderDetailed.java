package com.lnmj.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:34
 * @Description: 业绩明细
 */
@Data
public class LadderDetailed extends BaseEntity {
    private Long rownum;

    private Long ladderDetailedID;

    private Long ladderDetailedAchievementID;

    private Long ladderDetailedStoreId;

    private BigDecimal ladderDetailedAmount;

    private Long ladderDetailedBeauticianId;
    //业绩状态 1为正常 2为删除
    private Integer ladderDetailedState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ladderDetailedDatetime;

    private String ladderDetailedOrderId;

    private Integer orderType;

    private BigDecimal ladderDetailedNumber;

    private Boolean salesmanConfirm;

    private Boolean managementConfirm;

    private Integer ladderDetailedType;
    @Transient
    private String storeIdList;
    @Transient
    private String[] list;
    @Transient
    private String achievementName;
    @Transient
    private String beauticianName;
    @Transient
    private String storeName;
    @Transient
    private String orderTypeName;
}