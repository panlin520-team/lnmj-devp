package com.lnmj.store.entity;


import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class Post extends BaseEntity {

    private Long postId;
    private Long postLevel;
    private Long postCategoryId;
    private String name;
    private Long postIndustryID;
    private Integer postAchievement;
    private Long departmentId ;
    private Long companyType;
    private Long companyId;
    private String k3Id;
    private String k3Number;
    @Transient
    private String postLevelName;
    @Transient
    private String postCategoryName;
    @Transient
    private String postIndustryName;
}
