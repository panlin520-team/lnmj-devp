package com.lnmj.product.entity;


import com.lnmj.product.entity.VO.ProductNumberStockVO;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class ProductNumber extends BaseEntity {
        private Long id;
        private String stock;
        private String type;
        private String storageNumber;
        private String batchNumber;
        private String productType;
        private String productCode;
        private String productName;
        private String unit;
        private Integer number;
        private Integer aveailableNumber;
        private Integer oldNumber;
        private Integer totalNumber;
        private String remark;
        @Transient
        private String unitName;
        @Transient
        private Integer allStoreNumber;
        @Transient
        private List<Instorage> instorageList;
        @Transient
        private List<Outstorage> outstorageList;
        @Transient
        private List<StockProduct> stockProductList;
        @Transient
        private List<ProductNumberStockVO> productNumberStockVOList;
}