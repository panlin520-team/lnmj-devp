package com.lnmj.k3cloud.entity.supplier.supplier.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Ffinanceinfo {

    @JSONField(name="FEntryId")
    private int fentryid;
    @JSONField(name="FCustomerId")
    private Fcustomerid fcustomerid;
    @JSONField(name="FPayCurrencyId")
    private Fpaycurrencyid fpaycurrencyid;
    @JSONField(name="FPayCondition")
    private Fpaycondition fpaycondition;
    @JSONField(name="FSettleId")
    private Fsettleid fsettleid;
    @JSONField(name="FPayAdvanceAmount")
    private int fpayadvanceamount;
    @JSONField(name="FTaxType")
    private Ftaxtype ftaxtype;
    @JSONField(name="FTaxRegisterCode")
    private String ftaxregistercode;
    @JSONField(name="FChargeId")
    private Fchargeid fchargeid;
    @JSONField(name="FInvoiceType")
    private String finvoicetype;
    @JSONField(name="FTaxRateId")
    private Ftaxrateid ftaxrateid;
    @JSONField(name="FFinanceDesc")
    private String ffinancedesc;
    public void setFentryid(int fentryid) {
         this.fentryid = fentryid;
     }
     public int getFentryid() {
         return fentryid;
     }

    public void setFcustomerid(Fcustomerid fcustomerid) {
         this.fcustomerid = fcustomerid;
     }
     public Fcustomerid getFcustomerid() {
         return fcustomerid;
     }

    public void setFpaycurrencyid(Fpaycurrencyid fpaycurrencyid) {
         this.fpaycurrencyid = fpaycurrencyid;
     }
     public Fpaycurrencyid getFpaycurrencyid() {
         return fpaycurrencyid;
     }

    public void setFpaycondition(Fpaycondition fpaycondition) {
         this.fpaycondition = fpaycondition;
     }
     public Fpaycondition getFpaycondition() {
         return fpaycondition;
     }

    public void setFsettleid(Fsettleid fsettleid) {
         this.fsettleid = fsettleid;
     }
     public Fsettleid getFsettleid() {
         return fsettleid;
     }

    public void setFpayadvanceamount(int fpayadvanceamount) {
         this.fpayadvanceamount = fpayadvanceamount;
     }
     public int getFpayadvanceamount() {
         return fpayadvanceamount;
     }

    public void setFtaxtype(Ftaxtype ftaxtype) {
         this.ftaxtype = ftaxtype;
     }
     public Ftaxtype getFtaxtype() {
         return ftaxtype;
     }

    public void setFtaxregistercode(String ftaxregistercode) {
         this.ftaxregistercode = ftaxregistercode;
     }
     public String getFtaxregistercode() {
         return ftaxregistercode;
     }

    public void setFchargeid(Fchargeid fchargeid) {
         this.fchargeid = fchargeid;
     }
     public Fchargeid getFchargeid() {
         return fchargeid;
     }

    public void setFinvoicetype(String finvoicetype) {
         this.finvoicetype = finvoicetype;
     }
     public String getFinvoicetype() {
         return finvoicetype;
     }

    public void setFtaxrateid(Ftaxrateid ftaxrateid) {
         this.ftaxrateid = ftaxrateid;
     }
     public Ftaxrateid getFtaxrateid() {
         return ftaxrateid;
     }

    public void setFfinancedesc(String ffinancedesc) {
         this.ffinancedesc = ffinancedesc;
     }
     public String getFfinancedesc() {
         return ffinancedesc;
     }

}