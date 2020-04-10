package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

@Data
public class CustomerStore extends BaseEntity {
    private Long customerId;

    private Long storeId;
}