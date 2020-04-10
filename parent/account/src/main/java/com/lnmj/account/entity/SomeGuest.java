package com.lnmj.account.entity;

import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SomeGuest extends BaseEntity {
    private Long someGuestID;

    private String someGuest;

    private Long industryID;

    private Long storeID;

    private Integer numberToShop;

    private Integer sameStaffNumber;

    private Integer sameStaffContinueNumber;

    private Integer continueOtherNumber;

    private Integer lossDay;

    private String remark;

}