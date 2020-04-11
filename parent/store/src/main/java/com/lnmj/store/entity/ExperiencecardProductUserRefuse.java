package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ExperiencecardProductUserRefuse extends BaseEntity {
    private Long id;

    private Long experiencecardProductUserId;

    private Integer refuseTimes;

}