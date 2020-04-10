package com.lnmj.account.entity;

import lombok.Data;

@Data
public class UserLabelInfo {
    private Long userId;
    private Long labelId;
    private Long isHairdressingOldUser;
    private Long isCosmetologyOldUser;
    private Long isLoseUser;
}