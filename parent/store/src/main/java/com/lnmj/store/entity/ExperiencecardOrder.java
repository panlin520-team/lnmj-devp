package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class ExperiencecardOrder extends BaseEntity {
    private Long cardOrderId;
    private String cardOrderCode;
    private String memoNum;
    private String memberNum;
    private String purchaserName;
    private String linkPhone;
    private Double account;
    private Integer storeId;
    private String cardNum;
    private String cardName;
    private Integer orderStatus;
    private String payTypeAndAmount;
    private String beauticians;
    private Integer orderType;

    @Transient
    private String orderStatusName;
}