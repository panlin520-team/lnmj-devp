package com.lnmj.data.entity;

import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:33
 * @Description: 业绩阶梯
 */
@Data
public class Ladder extends BaseEntity {
    private Long rownum;

    private Long ladderID;

    private Long ladderAchievementID;

    private Long ladderAchievementPostID;

    private String ladderName;

    private Long ladderLower;

    private BigDecimal ladderProportion;

    private BigDecimal ladderBonus;

    @Transient
    private Integer ishaveParentAch;
}