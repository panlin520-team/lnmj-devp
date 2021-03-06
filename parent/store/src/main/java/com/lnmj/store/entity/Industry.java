package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;
import java.util.Map;

/*
    行业
 */

@Data
public class Industry extends BaseEntity {
    private Long industryID;

    private String industryName;

    @Transient
    private List<PostCategory> postCategoryList;
    @Transient
    private List<Map> performanceList;
}