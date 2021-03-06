package com.lnmj.k3cloud.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * @Author: yilihua
 * @Date: 2019/5/21 16:57
 * @Description: 预约表
 */
@Data
public class DataCentre {
    String fDATACENTERID;
    String fNUMBER;
    String fDATABASENAME;
    @Transient
    Integer isUsed;
    @Transient
    String useOrg;
}
