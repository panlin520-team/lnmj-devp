package com.lnmj.k3cloud.entity.account;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
@Data
public class FDeprPolicyID {
    private String FNumber;

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
}
