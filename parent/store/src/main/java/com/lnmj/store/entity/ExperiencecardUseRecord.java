package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExperiencecardUseRecord extends BaseEntity {
    private Long recordId;

    private Long experiencecardProductUserId;

    private String productCode;

    private String productName;

    private String linkName;

    private String linkMobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date bookingTime;

    private String bookingBeauticianIds;

    private String bookingBeauticianName;

    private String performanceRatio;

    private Integer storeId;

    private String storeName;

    private String experiencecardNum;

    private Integer recordStatus;

    private String outStorageIdQiTa;

    private String outStorageIdXiaoShou;
}