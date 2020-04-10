package com.lnmj.product.entity.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ProductStorageVO {
        private String type;    //类型
        private String storageType;     //单据类型
        private String storageNumber;   //单据号
        private String batchNumber;     //批号
        private String operator;        //操作人
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date storageDate;        //操作时间
}