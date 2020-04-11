package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class BankAccount extends BaseEntity {
    private Long id;

    private String accountName;

    private String accountNumber;

    private String companyId;

    private String bankName;

    private String k3Id;

    private Integer accountType;

    @Transient
    private String accountTypeName;
    @Transient
    private String bankId;
}