package com.lnmj.k3cloud.entity.account.accountingSystem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
public class FCreatorId {
    @JSONField(name ="FUserID")
    private String FUserID;

    public String getFUserID() {
        return FUserID;
    }

    public void setFUserID(String FUserID) {
        this.FUserID = FUserID;
    }
}
