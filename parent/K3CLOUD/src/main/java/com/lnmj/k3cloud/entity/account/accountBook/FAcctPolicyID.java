package com.lnmj.k3cloud.entity.account.accountBook;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
public class FAcctPolicyID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public FAcctPolicyID() {
    }

    public FAcctPolicyID(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
}
