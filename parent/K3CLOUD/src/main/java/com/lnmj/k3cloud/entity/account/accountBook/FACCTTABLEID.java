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
public class FACCTTABLEID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public FACCTTABLEID() {
    }

    public FACCTTABLEID(String FNumber) {
        this.FNumber = FNumber;
    }
}
