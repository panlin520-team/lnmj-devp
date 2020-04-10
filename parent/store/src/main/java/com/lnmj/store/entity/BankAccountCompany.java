package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
@Data
public class BankAccountCompany extends BaseEntity {
    private Long id;

    private Long bankCashAccountId;

    private String companyId;

    private String companyType;


}