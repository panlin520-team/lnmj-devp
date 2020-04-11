package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class Complaintrecord extends BaseEntity {
    private Long id;

    private Long handleId;

    private String userName;

    private String consumptionStore;

    private String consumptionProject;

    private String responsibility;

    private String complaintTitle;

    private Date complaintTime;

    private String complaintReason;

    private String handleMethod;

    private String result;


}