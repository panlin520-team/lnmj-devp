package com.lnmj.k3cloud.entity.account.accountPolicy;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.account.accountBook.FAcctSystemID;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
@Data
public class FAcctPolicyOrgEntity {
    @JSONField(name ="FEntryID")
    private int FEntryID;
    @JSONField(name ="FIsDefault")
    private Boolean FIsDefault;
    @JSONField(name ="FAcctSystemId")
    private FAcctSystemID FAcctSystemId;
    @JSONField(name ="FAcctOrgId")
    private FAcctOrgId FAcctOrgId;
}
