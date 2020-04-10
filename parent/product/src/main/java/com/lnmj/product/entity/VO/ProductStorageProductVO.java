package com.lnmj.product.entity.VO;


import lombok.Data;

import java.util.Date;

@Data
public class ProductStorageProductVO {
        private String type;    //类型
        private Long storageProductId;   //单据号
        private String batchNumber;     //批号
        private String productCode;     //商品编码
        private String productName;     //商品名
        private Integer number;         //数量
        private String unit;            //单位
        private String stock;           //仓库

}