package com.lnmj.store.entity.VO;

import com.lnmj.store.entity.ExperiencecardProductUser;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class ExperiencecardVO extends BaseEntity {

    private Long cardId;


    private Double account;

    private String cardNum;

    private String storeId;

    private String cardName;

    private Long subordBuyLimitId;

    private Long subClassId;

    private List<ExperiencecardProductUser> experiencecardProductUserList;

    private String logoImage;

    private Long id;
}