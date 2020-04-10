package com.lnmj.product.entity.VO;


import com.lnmj.product.entity.*;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class ProductNumberStockVO{
        private String stock;
        private String productType;
        private String productCode;
        private String productName;
        private String unit;
        private Integer totalNumber;
        @Transient
        private String unitName;
        @Transient
        private List<Instorage> instorageList;
        @Transient
        private List<Outstorage> outstorageList;
        @Transient
        private List<StockProduct> stockProductList;
}