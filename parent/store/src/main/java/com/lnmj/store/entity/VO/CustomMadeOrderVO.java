package com.lnmj.store.entity.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/8/5
 */
@Data
public class CustomMadeOrderVO {
    private Integer orderType;

    private String orderLink;

    private String mobile;

    private String cardNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private Long customMadeOrderDetailId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNumber;

    private Long serviceProductId;

    private String projectName;

    private Integer customMadeCount;

    private Integer userCount;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer integral;

    private BigDecimal manualCost;

    private String validitytime;

    private BigDecimal royaltyAmount;

    private String attribute;

    private Integer customMadeStatus;

}
