package com.lnmj.account.entity;

import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MMemberdivision  extends BaseEntity {
    private Long memberDivisionID;

    private String memberDivision;

    private Long memberLevel;

    private BigDecimal percentPage;

}