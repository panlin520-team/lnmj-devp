package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SupplierStore extends BaseEntity {
    private Long supplierId;

    private Long storeId;
}