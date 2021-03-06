package com.lnmj.order.entity;

import com.lnmj.order.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class CustomProjectUserRecord extends BaseEntity {
    private Long recordId;

    private Long customProjectUserId;

    private String productCode;

    private String productName;

    private String linkName;

    private String linkMobile;

    private Date useDate;

    private Date bookingTime;

    private String bookingBeauticianIds;

    private String bookingBeauticianName;

    private String performanceRatio;

    private Long storeId;

    private String storeName;

    private Integer recordStatus;

    private String outStorageIdQiTa;

    private String outStorageIdXiaoShou;
}