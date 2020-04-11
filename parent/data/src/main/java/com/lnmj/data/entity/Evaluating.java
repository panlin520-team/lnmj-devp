package com.lnmj.data.entity;

import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class Evaluating extends BaseEntity {
    private Long evaluatingID;

    private String evaluatingName;

    private Long evaluatingIndustryID;

    private Integer evaluatingType;

    private Integer evaluatingMethods;

    private String remark;

    private List<EvaluatingLevel> evaluatingLevelList;

    @Transient
    private String industryName;

}