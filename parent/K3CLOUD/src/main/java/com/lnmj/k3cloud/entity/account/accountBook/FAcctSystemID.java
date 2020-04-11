package com.lnmj.k3cloud.entity.account.accountBook;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
@Data
public class FAcctSystemID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public FAcctSystemID() {
    }

    public FAcctSystemID(String FNumber) {
        this.FNumber = FNumber;
    }
}
