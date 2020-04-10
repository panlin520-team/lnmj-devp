package com.lnmj.k3cloud.entity.supplier.supplier.addSendParam;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class SupplierAddModel {

    @JSONField(name="FSupplierId")
    private int fsupplierid;
    @JSONField(name="FCreateOrgId")
    private Fcreateorgid fcreateorgid;
    @JSONField(name="FNumber")
    private String fnumber;
    @JSONField(name="FUseOrgId")
    private Fuseorgid fuseorgid;
    @JSONField(name="FName")
    private String fname;
    @JSONField(name="FShortName")
    private String fshortname;
    @JSONField(name="FGroup")
    private Fgroup fgroup;
    @JSONField(name="FCorrespondOrgId")
    private Fcorrespondorgid fcorrespondorgid;
    @JSONField(name="FDescription")
    private String fdescription;
    @JSONField(name="FForbiderId")
    private Fforbiderid fforbiderid;
    @JSONField(name="FForbidDate")
    private Date fforbiddate;
    @JSONField(name="FBaseInfo")
    private Fbaseinfo fbaseinfo;
    @JSONField(name="FBusinessInfo")
    private Fbusinessinfo fbusinessinfo;
    @JSONField(name="FFinanceInfo")
    private Ffinanceinfo ffinanceinfo;
    @JSONField(name="FBankInfo")
    private List<Fbankinfo> fbankinfo;
    @JSONField(name="FLocationInfo")
    private List<Flocationinfo> flocationinfo;
    @JSONField(name="FSupplierContact")
    private List<Fsuppliercontact> fsuppliercontact;


}