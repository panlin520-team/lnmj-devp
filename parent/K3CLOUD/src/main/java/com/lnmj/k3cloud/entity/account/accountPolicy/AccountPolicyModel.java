/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.account.accountPolicy;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 会计政策model
 */
@Data
public class AccountPolicyModel {
    @JSONField(name ="FACCTPOLICYID")
    private Integer facctpolicyid;
    @JSONField(name ="FNumber")
    private String fnumber;
    @JSONField(name ="FName")
    private String fname;
    @JSONField(name ="FCurrencyID")
    private FCurrencyID fcurrencyid;
    @JSONField(name ="FAcctCalendarID")
    private FAcctCalendarID facctcalendarid;
    @JSONField(name ="FAcctGroupID")
    private FAcctGroupID facctgroupid;
    @JSONField(name ="FRateTypeID")
    private FRateTypeID fratetypeid;
    @JSONField(name ="FDescription")
    private String FDescription;
    @JSONField(name ="FIsCalByExpItem")
    private Boolean FIsCalByExpItem;
    @JSONField(name ="FISTAXAMOUNT")
    private Boolean FISTAXAMOUNT;
    @JSONField(name ="FCreateOrgId")
    private FCreateOrgId FCreateOrgId;
    @JSONField(name ="FUseOrgId")
    private FUseOrgId FUseOrgId;
    @JSONField(name ="FISCOSTACCOUNT")
    private Boolean FISCOSTACCOUNT;
    @JSONField(name ="FAcctSystem")
    private FAcctSystem FAcctSystem;
    @JSONField(name ="FAcctPolicyAssetEntity")
    private List<FAcctPolicyAssetEntity> FAcctPolicyAssetEntityList;
    @JSONField(name ="FAcctPolicyOrgEntity")
    private List<FAcctPolicyOrgEntity> FAcctPolicyOrgEntityList;
}