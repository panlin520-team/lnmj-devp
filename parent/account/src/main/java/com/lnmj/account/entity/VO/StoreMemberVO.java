package com.lnmj.account.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StoreMemberVO extends BaseEntity {
    private Long id;

    private Long storeId;

    private Integer sex;

    private String mobile;

    private String name;

    private String wxOpenId;

    private String idCard;

    private Integer lableId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastArriveDate;

    private Integer arriveTimes;

    private Long totalConsumption;

    private Integer enterChannel;

    private String MemberNum;

    private String Remark;

    private Integer newOldStatus;

    private Long beauticianId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastConsumeDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstConsumeDate;

    private Integer isLost;
}