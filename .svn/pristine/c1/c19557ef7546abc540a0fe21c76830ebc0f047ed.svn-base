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
public class AccountUseRule extends BaseEntity {
      private Long accountUseRuleId;
      private Long accountType;     //账户类型
      private Long productType;     //商品类型（产品或服务）
      private String productTypeName;//商品类型
      private String payPriceType;  //使用价格类型    //1.原价 2.零售价 3.折扣价
//      private BigDecimal discount;  //折扣
//      private Long memberLevel;     //会员等级id
//      private String memberLevelName;//会员等级
//      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
//      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//      private Date accountUseStartTime;   //账号有效期
//      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
//      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//      private Date accountUseEndTime;     //账号有效期
      private Integer  useAccount;        //请客账号类型（1所有用户，2老用户，3新用户）
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date startTime;       //开始时间、结束时间（新用户开始算的时间，老用户结束时间）
      private Integer  useSum;      //使用总次数
      private BigDecimal useMax;    //使用总价格
      private Integer productUseDay;//接收者必须在多少天内使用
      private Integer useMethod;    //使用方式：1 可请多人（可接受多人请客）；2 只可请一人（规则有效期内，只接受一人请客）；3 只可请一人一次（账号只接受一人请客一次）
//      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
//      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//      private Date ruleStartTime;   //规则有效期
//      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
//      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//      private Date ruleEndTime;     //规则有效期
      private String remark;        //备注
      @Transient
      private String IndustryJson;

      @Transient
      private List<IndustryAccount> accountList;

}
