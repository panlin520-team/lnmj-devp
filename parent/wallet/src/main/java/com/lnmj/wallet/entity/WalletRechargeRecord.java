package com.lnmj.wallet.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WalletRechargeRecord extends BaseEntity {

    private Long rechargeRecordId;//充值记录id
    private String name;//联系人
    private String mobile;//联系方式
    private String cardNumber;//会员卡号
    private String giveAccount;//赠送账号
    private String orderNo;//订单号
    private String beauticianId;//美容师id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transactionTime;//下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;//到账时间
    private BigDecimal amount;//充值金额
    private String rechargeType;//充值类型
    private String payAccount;//支付账号
    private Long isRoyalty;//是否上级提成
    private Long isintegral;//是否上级提成
    private Long storeId;
    private String payee;//收款人
    private Long payType;//支付类型
    private Long payStatus;//支付状态
    private String failureCause;//失败原因
    private Integer rechargeChannel;//充值渠道
    private String remarks;//备注
    private Integer auditStatus;//审核状态
    private Long accountTypeId;//充值类型账户
    private String payTypeAndAmount;
    private Integer auditAmountStatus;
    @Transient
    private String rechargeChannelName;
    @Transient
    private String beauticianName;
    @Transient
    private Boolean isAbatementLadderDetailed;
    @Transient
    private String accountTypeName;
    @Transient
    private String industryId;
    @Transient
    private Double residueAmount;

}
