package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class Customer extends BaseEntity {
    private Long id;

    private String name;

    private String k3Id;

    private String k3Number;

    private Integer customerType;

    private Integer isDefaultCust;

    private Integer relationSubCompanyType;

    private Long relationSubCompanyId;

    private Long zhongCompanyId;
    @Transient
    private String companyName;

    @Transient
    private String companyType;

    @Transient
    private String companyId;

}