package com.lnmj.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.data.entity.VO.PostCategoryVO;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 14:34
 * @Description: 商品小类
 */
@Data
public class Subclass extends BaseEntity {
    private Long subclassID;

    private String subclassName;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subclassAchievementID;

    private Long commodityTypeID;

    private String postCategoryIds;

    private Long subclassEvaluatingID;
    @Transient
    private List<PostCategoryVO> postCategoryVOList;
    @Transient
    private String subclassEvaluatingName;
    @Transient
    private String commodityTypeName;
    @Transient
    private String industryName;
}

