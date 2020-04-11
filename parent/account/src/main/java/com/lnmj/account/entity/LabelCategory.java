package com.lnmj.account.entity;


import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;

@Data
public class LabelCategory extends BaseEntity {

    private Long labelCategoryId;//标签类别id
    private String categoryName;//类别名称

}
