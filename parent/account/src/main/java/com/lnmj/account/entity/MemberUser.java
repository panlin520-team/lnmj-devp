package com.lnmj.account.entity;

import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class MemberUser extends BaseEntity {
    private Long id;

    private String headImgUrl;

    private String memberNum;

    private String userName;

    private String password;

    private String birthday;

    private Long storeId;

    private Integer sex;

    private String mobile;

    private String name;

    private String wxOpenId;

    private String idCard;

    private String lableId;

    private String lastArriveDate;

    private Integer arriveTimes;

    private Long totalConsumption;

    private Integer enterChannel;

    private String parentMemberNum;

    private String remark;

    private Integer newOldStatus;

    private Long beauticianId;

    private Date lastConsumeDate;

    private Date firstConsumeDate;

    private Integer isLost;

    private Long membershipLevelId;

    private String membershipLevelName;

    private Integer memberAddType;
    @Transient
    private String enterChannelName;
    @Transient
    private String MemberAddTypeName;
    @Transient
    private String lableName;
}