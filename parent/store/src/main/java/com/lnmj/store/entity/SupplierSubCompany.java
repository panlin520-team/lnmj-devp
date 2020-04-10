package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;

@Data
public class SupplierSubCompany extends BaseEntity {
    private Long supplierId;

    private Long subCompanyId;
}