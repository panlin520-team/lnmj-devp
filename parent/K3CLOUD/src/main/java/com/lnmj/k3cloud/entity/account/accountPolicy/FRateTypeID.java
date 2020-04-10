package com.lnmj.k3cloud.entity.account.accountPolicy;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
@Data
public class FRateTypeID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public FRateTypeID() {
    }

    public FRateTypeID(String FNumber) {
        this.FNumber = FNumber;
    }
}
