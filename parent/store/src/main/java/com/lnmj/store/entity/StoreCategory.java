package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class StoreCategory extends BaseEntity {
    private Long storeCategoryId;

    private String storeCategoryName;
    private Integer companyType;
    private Long companyId;
}