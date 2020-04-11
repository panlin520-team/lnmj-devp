package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fentityauxpty {

    @JSONField(name="FEntryID")
    private int fentryid;
    @JSONField(name="FAuxPropertyId")
    private Fauxpropertyid fauxpropertyid;
    @JSONField(name="FIsEnable1")
    private String fisenable1;
    @JSONField(name="FIsComControl")
    private String fiscomcontrol;
    @JSONField(name="FIsAffectPrice1")
    private String fisaffectprice1;
    @JSONField(name="FIsAffectPlan1")
    private String fisaffectplan1;
    @JSONField(name="FIsAffectCost1")
    private String fisaffectcost1;
    @JSONField(name="FIsMustInput")
    private String fismustinput;
    @JSONField(name="FValueType")
    private String fvaluetype;

    public Fentityauxpty(int fentryid, Fauxpropertyid fauxpropertyid, String fisenable1, String fiscomcontrol, String fisaffectprice1, String fisaffectplan1, String fisaffectcost1, String fismustinput, String fvaluetype) {
        this.fentryid = fentryid;
        this.fauxpropertyid = fauxpropertyid;
        this.fisenable1 = fisenable1;
        this.fiscomcontrol = fiscomcontrol;
        this.fisaffectprice1 = fisaffectprice1;
        this.fisaffectplan1 = fisaffectplan1;
        this.fisaffectcost1 = fisaffectcost1;
        this.fismustinput = fismustinput;
        this.fvaluetype = fvaluetype;
    }

    public void setFentryid(int fentryid) {
         this.fentryid = fentryid;
     }
     public int getFentryid() {
         return fentryid;
     }

    public void setFauxpropertyid(Fauxpropertyid fauxpropertyid) {
         this.fauxpropertyid = fauxpropertyid;
     }
     public Fauxpropertyid getFauxpropertyid() {
         return fauxpropertyid;
     }

    public void setFisenable1(String fisenable1) {
         this.fisenable1 = fisenable1;
     }
     public String getFisenable1() {
         return fisenable1;
     }

    public void setFiscomcontrol(String fiscomcontrol) {
         this.fiscomcontrol = fiscomcontrol;
     }
     public String getFiscomcontrol() {
         return fiscomcontrol;
     }

    public void setFisaffectprice1(String fisaffectprice1) {
         this.fisaffectprice1 = fisaffectprice1;
     }
     public String getFisaffectprice1() {
         return fisaffectprice1;
     }

    public void setFisaffectplan1(String fisaffectplan1) {
         this.fisaffectplan1 = fisaffectplan1;
     }
     public String getFisaffectplan1() {
         return fisaffectplan1;
     }

    public void setFisaffectcost1(String fisaffectcost1) {
         this.fisaffectcost1 = fisaffectcost1;
     }
     public String getFisaffectcost1() {
         return fisaffectcost1;
     }

    public void setFismustinput(String fismustinput) {
         this.fismustinput = fismustinput;
     }
     public String getFismustinput() {
         return fismustinput;
     }

    public void setFvaluetype(String fvaluetype) {
         this.fvaluetype = fvaluetype;
     }
     public String getFvaluetype() {
         return fvaluetype;
     }

}