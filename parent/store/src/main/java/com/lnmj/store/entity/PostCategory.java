package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;


@Data
public class PostCategory extends BaseEntity {
    private Long postCategoryId;

    private String name;

    private Long industryID;
    private Long companyType;
    private Long companyId;
    @Transient
    private String industryName;
}