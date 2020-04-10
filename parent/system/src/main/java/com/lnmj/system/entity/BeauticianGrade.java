package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BeauticianGrade extends BaseEntity {

    private Long beauticianGradeId;   //  美容师等级id
    private Integer beauticianGrade;  //  美容师等级
    private String memberShipLevel;   //  会员等级

}
