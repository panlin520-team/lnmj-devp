package com.lnmj.k3cloud.entity.account.accountPolicy;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
public class FWorkLoadUnitID {
    @JSONField(name ="FNumber")
    private int FNumber;

    public int getFNumber() {
        return FNumber;
    }

    public void setFNumber(int FNumber) {
        this.FNumber = FNumber;
    }
}
