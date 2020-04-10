package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class Complainthandle extends BaseEntity {
    private Long id;

    private String userName;

    private Long complaintHandleId;

    private String consumptionStore;

    private String responsibility;

    private String complaintTitle;

    private String complaintReason;

    private String handleMethod;




}