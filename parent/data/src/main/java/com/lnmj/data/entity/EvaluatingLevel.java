package com.lnmj.data.entity;

import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class EvaluatingLevel extends BaseEntity {
    private Long evaluatingLevelID;

    private String evaluatingLevelName;

    private Long evaluatingLevelEvaluatingID;

    private Integer evaluatingLevelLower;

    private Integer evaluatingLevelDate;

    private String remark;

    @Transient
    private String evaluatingName;

}