package com.lnmj.data.entity;

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
 * @Date: 2019/10/25 10:41
 * @Description: 客户评测
 */
@Data
public class UserEvaluating extends BaseEntity {

    private Long userId;
    private Long evaluatingID;
    private String evaluatingName;
    private Long evaluatingLevelID;
    private String evaluatingLevelName;
    private BigDecimal evaluatingAmount;
    private BigDecimal evaluatingNumber;
    //统计时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evaluatingDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evaluatingDateEnd;
    private String evaluatingDetailed;
    private String remark;
}