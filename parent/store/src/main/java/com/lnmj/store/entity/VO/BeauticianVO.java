package com.lnmj.store.entity.VO;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BeauticianVO extends BaseEntity {
    private Long beauticianId;
    private String name;
    private Integer sort;
    private String headUrl;
    private String introduction;
    private Integer isPartTime;


}