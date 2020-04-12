package com.lnmj.data.entity;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:33
 * @Description: 统计
 */
@Data
public class Statistic extends BaseEntity {

    private Long statisticID;
    private Long salesmanID;
    //统计时间(日期或日期期间)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statisticDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statisticDateEnd;
    private String ladderDetailed;
    private BigDecimal numberPerformance;
    private BigDecimal amountPerformance;
    private BigDecimal score;
    private BigDecimal baseSalary;
    private Long storeId;
    private BigDecimal salary;  //薪资
    private Boolean salesmanConfirm;
    private BigDecimal takePercentage; //提成
    private Boolean managementConfirm;
    @Transient
    private HashMap<String,Object> performanceMap;
    @Transient
    private HashMap<String,Object> performanceMapAllStore;
    @Transient
    private HashMap<String,Object> performanceMapGroup;
    @Transient
    private Integer postAchievement;//业绩统计方式 0个人 1全店 2分组
    @Transient
    private BigDecimal numberPerformanceAllStore;
    @Transient
    private BigDecimal amountPerformanceAllStore;
    @Transient
    private BigDecimal numberPerformanceGroup;
    @Transient
    private BigDecimal amountPerformanceGroup;
    @Transient
    private String ladderDetailedAllStore;
    @Transient
    private String ladderDetailedGroup;
    @Transient
    private BigDecimal scoreAllStore;
    @Transient
    private BigDecimal scoreGroup;
    private BigDecimal numberPerformanceAll;
    private BigDecimal amountPerformanceAll;

}