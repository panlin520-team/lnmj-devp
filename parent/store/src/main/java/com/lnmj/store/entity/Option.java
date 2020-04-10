package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/7/24 10:55
 * @Description:  设置表
 */
@Data
public class Option extends BaseEntity {
    private Long optionId;//Id
    private String optionTitle;//标题
    private String optionSort;//排序
    private String optionValue;//设置的值(html的标签)
    private String remark;//备注
}
