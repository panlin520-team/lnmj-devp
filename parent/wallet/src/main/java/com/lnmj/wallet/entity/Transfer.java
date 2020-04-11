package com.lnmj.wallet.entity;

import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Transfer extends BaseEntity {
    private Long id;

    private Long capitalPoolId;

    private Long transferToStoreId;

    private Long transferFromStoreId;

    private BigDecimal amount;

    private Integer transferType;

    private String remarks;

    private String orderNumber;

    @Transient
    private String transferToStoreName;
    @Transient
    private String transferFromStoreName;

}