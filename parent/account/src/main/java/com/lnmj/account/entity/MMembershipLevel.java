package com.lnmj.account.entity;


import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:03
 * @Description: 用户会员等级表
 */
@Data
@MTable(name = "m_membershiplevel")
public class MMembershipLevel extends BaseEntity {
    @Id
    private Long membershipLevelId;         //会员等级Id
    private String membershipLevelName;   //会员等级名称
    private String erpName;               //ERP名称
    private Boolean autoUpgrade;             //是否自动升级
    private BigDecimal standardAmount;      //等级累计储值
    private BigDecimal bestieIntroduceAmount; //等级单次储值
    private BigDecimal upgradeStandardAmount;//消费标准
    private Integer upgradeStandardFans;            //拓客标准
    private String upgradingWays;                    //升级方式
    private BigDecimal membershipDiscount;       //等级折扣
    private BigDecimal priceDifferenceAllot; //差价分配
    private BigDecimal bonusAllot;          //奖金分配
    private BigDecimal nursingDifferenceAllot;//护理差价分配
    private BigDecimal nursingBonusAllot;  //护理奖金分配
    private BigDecimal firstRechargeBonus;  //首次充值奖金
    private String scopeOfUse;          //会员等级使用范围
    private Boolean isDivision;          //是否是股东
    private Boolean isDefault;          //是否默认等级（1 是  0否），默认等级是添加会员的等级

    private List<MMemberaccount> memberAccountList;
    private List<MMemberdivision> memberDivisionList;

}
