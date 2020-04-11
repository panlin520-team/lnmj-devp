package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SupplierCategory extends BaseEntity {
    private Long supplierCategoryId;

    private String supplierCategoryName;

    private String companyType;

    private String companyId;

    private List<Supplier> supplierList;
}