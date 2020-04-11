package com.lnmj.k3cloud.entity.supplier.supplier.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fbankinfo {

    @JSONField(name="FBankId")
    private int fbankid;
    @JSONField(name="FBankCountry")
    private Fbankcountry fbankcountry;
    @JSONField(name="FOpenBankName")
    private String fopenbankname;
    @JSONField(name="FBankCode")
    private String fbankcode;
    @JSONField(name="FBankHolder")
    private String fbankholder;
    @JSONField(name="FBankTypeRec")
    private Fbanktyperec fbanktyperec;
    @JSONField(name="FTextBankDetail")
    private String ftextbankdetail;
    @JSONField(name="FOpenAddressRec")
    private String fopenaddressrec;
    @JSONField(name="FCNAPS")
    private String fcnaps;
    @JSONField(name="FSwiftCode")
    private String fswiftcode;
    @JSONField(name="FBankCurrencyId")
    private Fbankcurrencyid fbankcurrencyid;
    @JSONField(name="FBankIsDefault")
    private String fbankisdefault;
    @JSONField(name="FBankDesc")
    private String fbankdesc;
    public void setFbankid(int fbankid) {
         this.fbankid = fbankid;
     }
     public int getFbankid() {
         return fbankid;
     }

    public void setFbankcountry(Fbankcountry fbankcountry) {
         this.fbankcountry = fbankcountry;
     }
     public Fbankcountry getFbankcountry() {
         return fbankcountry;
     }

    public void setFopenbankname(String fopenbankname) {
         this.fopenbankname = fopenbankname;
     }
     public String getFopenbankname() {
         return fopenbankname;
     }

    public void setFbankcode(String fbankcode) {
         this.fbankcode = fbankcode;
     }
     public String getFbankcode() {
         return fbankcode;
     }

    public void setFbankholder(String fbankholder) {
         this.fbankholder = fbankholder;
     }
     public String getFbankholder() {
         return fbankholder;
     }

    public void setFbanktyperec(Fbanktyperec fbanktyperec) {
         this.fbanktyperec = fbanktyperec;
     }
     public Fbanktyperec getFbanktyperec() {
         return fbanktyperec;
     }

    public void setFtextbankdetail(String ftextbankdetail) {
         this.ftextbankdetail = ftextbankdetail;
     }
     public String getFtextbankdetail() {
         return ftextbankdetail;
     }

    public void setFopenaddressrec(String fopenaddressrec) {
         this.fopenaddressrec = fopenaddressrec;
     }
     public String getFopenaddressrec() {
         return fopenaddressrec;
     }

    public void setFcnaps(String fcnaps) {
         this.fcnaps = fcnaps;
     }
     public String getFcnaps() {
         return fcnaps;
     }

    public void setFswiftcode(String fswiftcode) {
         this.fswiftcode = fswiftcode;
     }
     public String getFswiftcode() {
         return fswiftcode;
     }

    public void setFbankcurrencyid(Fbankcurrencyid fbankcurrencyid) {
         this.fbankcurrencyid = fbankcurrencyid;
     }
     public Fbankcurrencyid getFbankcurrencyid() {
         return fbankcurrencyid;
     }

    public void setFbankisdefault(String fbankisdefault) {
         this.fbankisdefault = fbankisdefault;
     }
     public String getFbankisdefault() {
         return fbankisdefault;
     }

    public void setFbankdesc(String fbankdesc) {
         this.fbankdesc = fbankdesc;
     }
     public String getFbankdesc() {
         return fbankdesc;
     }

}