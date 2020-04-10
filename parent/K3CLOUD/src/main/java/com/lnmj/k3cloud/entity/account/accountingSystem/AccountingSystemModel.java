/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.account.accountingSystem;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class AccountingSystemModel {
    @JSONField(name ="FACCTSYSTEMID")
    private Integer FACCTSYSTEMID;
    @JSONField(name ="FVersionsNO")
    private String FVersionsNO;
    @JSONField(name ="FNumber")
    private String FNumber;
    @JSONField(name ="FName")
    private String FName;
    @JSONField(name ="FDescription")
    private String FDescription;
    @JSONField(name ="FISDefault")
    private Boolean FISDefault;
    @JSONField(name ="FCreatorId")
    private FCreatorId FCreatorId;
    @JSONField(name ="FIsOriginal")
    private Boolean FIsOriginal;
    @JSONField(name ="FVersionsSeq")
    private Integer FVersionsSeq;
    @JSONField(name ="FCreateDate")
    private String FCreateDate;
    @JSONField(name ="FNewVersionsId")
    private Integer FNewVersionsId;
    @JSONField(name ="FIsCorporate")
    private Boolean FIsCorporate;
    @JSONField(name ="FAccountSystemEntry")
    private List<FAccountSystemEntry> fAccountSystemEntryList;
}