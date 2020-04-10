/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.account.accountBook;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class AccountBookModel {
    @JSONField(name ="FBOOKID",ordinal = 1)
    private Integer FBOOKID;            //账薄id
    @JSONField(name ="FCRTYEARPERIOD",ordinal = 2)
    private String FCRTYEARPERIOD;
    @JSONField(name ="FCreateOrgId",ordinal = 3)
    private FCreateOrgId FCreateOrgId;
    @JSONField(name ="FNumber",ordinal = 4)
    private String FNumber;
    @JSONField(name ="FAcctSystemID",ordinal = 5)
    private FAcctSystemID FAcctSystemID;
    @JSONField(name ="FAccountOrgID",ordinal = 6)
    private FAccountOrgID FAccountOrgID;
    @JSONField(name ="FAcctPolicyID",ordinal = 7)
    private FAcctPolicyID FAcctPolicyID;
    @JSONField(name ="FSTARTPERIOD",ordinal = 8)
    private String FSTARTPERIOD;
    @JSONField(name ="FPeriodid",ordinal = 9)
    private FPeriodid FPeriodid;
    @JSONField(name ="FName",ordinal = 10)
    private String FName;
    @JSONField(name ="FBOOKTYPE",ordinal = 11)
    private String FBOOKTYPE;
    @JSONField(name ="FACCTTABLEID",ordinal = 12)
    private FACCTTABLEID FACCTTABLEID;
    @JSONField(name ="FCURRENCYID",ordinal = 13)
    private FCURRENCYID FCURRENCYID;
    @JSONField(name ="FRATETYPEID",ordinal =14 )
    private FRATETYPEID FRATETYPEID;
    @JSONField(name ="FSTARTYEAR",ordinal = 15)
    private String FSTARTYEAR;
    @JSONField(name ="FYEARANDPERIOD",ordinal = 16)
    private String FYEARANDPERIOD;
    @JSONField(name ="FAPFinType",ordinal = 17)
    private String FAPFinType;
    @JSONField(name ="FARFinType",ordinal = 18)
    private String FARFinType;
    @JSONField(name ="FCURRENTYEAR",ordinal = 19)
    private Integer FCURRENTYEAR;
    @JSONField(name ="FCURRENTPERIOD",ordinal = 20)
    private Integer FCURRENTPERIOD;
    @JSONField(name ="FEntity",ordinal = 21)
    private FEntity FEntity;
}