package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;


@Data
public class Complainttrack extends BaseEntity {
    private Long id;

    private Long handleId;

    private String userName;

    private String phoneNumber;

    private String complaintor;

    private String consumptionStore;

    private String handle;


}