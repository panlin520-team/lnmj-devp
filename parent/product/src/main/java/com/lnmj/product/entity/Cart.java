package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class Cart extends BaseEntity {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Integer checked;


    @Transient
    private List<String> productIdList;


}
