package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

/**
 * @Author: yilihua
 * @Date: 2019/7/24 10:55
 * @Description:  设置表
 */
@Data
public class OptionValue extends BaseEntity {
    private Long userOptionId;//Id
    private Long userId;//用户id
    private Long optionId;//属性id
    private String cardNumber;//会员卡号
    private String userValue;//属性值
}
