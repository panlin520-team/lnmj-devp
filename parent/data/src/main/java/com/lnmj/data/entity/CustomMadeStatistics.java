package com.lnmj.data.entity;

import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author renqingyun
 * @Date: 2019/6/11 14:02
 * @Description: 定制次数统计
 */

@Data
public class CustomMadeStatistics extends BaseEntity {
    private Long customMadeId;

    private String name;

    private String mobile;

    private Date orderTime;

    private String nurseProject;

    private String amount;

    private String payType;

    private String store;

    private String userCount;

}