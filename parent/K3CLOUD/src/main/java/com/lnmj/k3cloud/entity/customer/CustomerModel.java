/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.customer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 14:30:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class CustomerModel {
    @JSONField(name ="FCUSTID")
    private int FCUSTID;
    @JSONField(name ="FCreateOrgId")
    private FCreateOrgId FCreateOrgId;
    @JSONField(name ="FNumber")
    private String FNumber;
    @JSONField(name ="FUseOrgId")
    private FUseOrgId FUseOrgId;
    @JSONField(name ="FName")
    private String FName;
    @JSONField(name ="FShortName")
    private String FShortName;
    @JSONField(name ="FCOUNTRY")
    private FCOUNTRY FCOUNTRY;
    @JSONField(name ="FPROVINCIAL")
    private FPROVINCIAL FPROVINCIAL;
    @JSONField(name ="Creator")
    private String FADDRESS;
    @JSONField(name ="Creator")
    private String FZIP;
    @JSONField(name ="Creator")
    private String FWEBSITE;
    @JSONField(name ="Creator")
    private String FTEL;
    @JSONField(name ="Creator")
    private String FFAX;
    @JSONField(name ="Creator")
    private FCompanyClassify FCompanyClassify;
    @JSONField(name ="Creator")
    private FCompanyNature FCompanyNature;
    @JSONField(name ="Creator")
    private FCompanyScale FCompanyScale;
    @JSONField(name ="Creator")
    private String FINVOICETITLE;
    @JSONField(name ="Creator")
    private String FTAXREGISTERCODE;
    @JSONField(name ="Creator")
    private String FINVOICEBANKNAME;
    @JSONField(name ="Creator")
    private String FINVOICETEL;
    @JSONField(name ="Creator")
    private String FINVOICEBANKACCOUNT;
    @JSONField(name ="Creator")
    private String FINVOICEADDRESS;
    @JSONField(name ="Creator")
    private FSUPPLIERID FSUPPLIERID;
    @JSONField(name ="Creator")
    private String FIsDefPayer;
    @JSONField(name ="Creator")
    private FGROUPCUSTID FGROUPCUSTID;
    @JSONField(name ="Creator")
    private String FIsGroup;
    @JSONField(name ="Creator")
    private FCustTypeId FCustTypeId;
    @JSONField(name ="Creator")
    private FGroup FGroup;
    @JSONField(name ="Creator")
    private FTRADINGCURRID FTRADINGCURRID;
    @JSONField(name ="Creator")
    private FCorrespondOrgId FCorrespondOrgId;
    @JSONField(name ="Creator")
    private String FDescription;
    @JSONField(name ="Creator")
    private FSALDEPTID FSALDEPTID;
    @JSONField(name ="Creator")
    private FSELLER FSELLER;
    @JSONField(name ="Creator")
    private FSETTLETYPEID FSETTLETYPEID;
    @JSONField(name ="Creator")
    private FRECCONDITIONID FRECCONDITIONID;
    @JSONField(name ="Creator")
    private FDISCOUNTLISTID FDISCOUNTLISTID;
    @JSONField(name ="Creator")
    private FPRICELISTID FPRICELISTID;
    @JSONField(name ="Creator")
    private int FTRANSLEADTIME;
    @JSONField(name ="Creator")
    private String FInvoiceType;
    @JSONField(name ="Creator")
    private FTaxType FTaxType;
    @JSONField(name ="Creator")
    private FRECEIVECURRID FRECEIVECURRID;
    @JSONField(name ="Creator")
    private int FPriority;
    @JSONField(name ="Creator")
    private FTaxRate FTaxRate;
    @JSONField(name ="Creator")
    private String FISCREDITCHECK;
    @JSONField(name ="Creator")
    private String FIsTrade;
    @JSONField(name ="Creator")
    private FT_BD_CUSTOMEREXT FT_BD_CUSTOMEREXT;
    @JSONField(name ="Creator")
    private List<FT_BD_CUSTLOCATION> FT_BD_CUSTLOCATION;
    @JSONField(name ="Creator")
    private List<FT_BD_CUSTBANK> FT_BD_CUSTBANK;
    @JSONField(name ="Creator")
    private List<FT_BD_CUSTCONTACT> FT_BD_CUSTCONTACT;
    @JSONField(name ="Creator")
    private List<FT_BD_CUSTORDERORG> FT_BD_CUSTORDERORG;

}