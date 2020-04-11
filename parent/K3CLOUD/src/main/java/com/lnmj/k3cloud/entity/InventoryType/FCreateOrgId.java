package com.lnmj.k3cloud.entity.InventoryType;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/12/23
 */
@Data
public class FCreateOrgId {

    @JSONField(name ="FNumber")
    private String FNumber;

    public FCreateOrgId() {
    }

    public FCreateOrgId(String FNumber) {
        this.FNumber = FNumber;
    }
}
