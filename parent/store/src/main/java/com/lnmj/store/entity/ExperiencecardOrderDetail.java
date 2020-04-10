package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class ExperiencecardOrderDetail extends BaseEntity {
    private Long cardOrderDetailId;
    private String cardOrderCode;
    private String productCode;
    private String productKind;
    private String productName;
    private String useLimit;
    private Integer useTotalTimes;
    private Integer useTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useDate;
    private Integer orderShop;
    private Integer orderBeautician;
    private Integer projectUseState;



}