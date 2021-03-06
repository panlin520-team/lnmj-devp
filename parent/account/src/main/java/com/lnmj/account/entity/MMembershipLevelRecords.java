package com.lnmj.account.entity;


import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:04
 * @Description: 用户升级记录表
 */
@Data
@MTable(name = "m_membershiplevelrecords")
public class MMembershipLevelRecords implements Serializable {
    private static final long serialVersionUID = 1L;//transient 关键字表示不用序列化
    @Id
    private Long membershiplevelrecordsId;         //Id
    private Integer membershiplevelrecordsType;   //记录类型
    private String memberNum;               //会员编号
    private String name;               //用户名
    private Long afterMembershipLevelId;      //升级前会员等级
    private Long laterMembershipLevelId;      //升级后会员等级
    private String operateDesc;     //操作描述
    private BigDecimal amount;         //本次金额
    private BigDecimal totalAmount;    //总金额
    private Integer status;                //状态
    private String createOperator;      //创建人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;            //创建时间

    @Transient
    String membershipLevelNameAfter;
    @Transient
    String membershipLevelNameLater;
}
