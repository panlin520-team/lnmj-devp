package com.lnmj.k3cloud.entity.account.accountingSystem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
public class FSUBORGID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
}
