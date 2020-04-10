package com.lnmj.k3cloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.common.BaseEntity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Incident extends BaseEntity {
    private Long incidentId;

    private String incidentProject;

    private String incidentClass;

    private String incidentName;

    private String incidentJSON;

    private String excuteIncidentProject;

    private String excuteIncidentClass;

    private String excuteIncidentName;

    private String excuteIncidentJSON;

    private Long parentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date incidentDate;

    private Long templateId;

    private Long k3Id;

    private String remark;

}