package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class Experiencecard extends BaseEntity {


    private Long cardId;

    private Double account;

    private String cardNum;

    private String storeId;

    private String cardName;

    private Long subordBuyLimitId;

    private Long achievementPostId;

    private Long achievementId;

    private List<ExperiencecardProductUser> experiencecardProductUserList;

    private String logoImage;

    private String moreContent;

    private Integer stockNum;

    private Integer salesVolume;


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDeadline;

    @Transient
    private String achievementPostName;
    @Transient
    private String achievementName;
}