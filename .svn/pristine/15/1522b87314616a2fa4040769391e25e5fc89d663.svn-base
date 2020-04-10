package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/5/21 16:57
 * @Description: 赠送 表
 */
@Data
public class Presentation extends BaseEntity {
    private Long presentationId;//Id
    private Integer orderType;//订单类型
    private String orderNumber;//订单号
    private String orderLink;//赠送者
    private String mobile;//赠送者电话
    private String cartNumber;//赠送者会员卡号
    private Long productId;//商品Id
    private String productName;//赠送商品
    private String contacts;//联系人
    private String phone;//联系电话
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date presentationTime;//赠送时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date presentationEndTime;//有效时间
    private BigDecimal payPrice;//支付价格
    private Long storeId;//护理门店
    private String nurseStore;//护理门店
    private String beautician;//美容师
    private String beauticianId;//美容师
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nurseTime;//护理时间
    private String achievementProportion;//业绩占比
    private Integer presentationStatus;//业务状态
}
