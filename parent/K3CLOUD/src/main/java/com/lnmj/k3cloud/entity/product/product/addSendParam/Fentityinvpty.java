package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fentityinvpty {

    @JSONField(name="FEntryID")
    private int fentryid;
    @JSONField(name="FInvPtyId")
    private Finvptyid finvptyid;
    @JSONField(name="FIsEnable")
    private String fisenable;
    @JSONField(name="FIsAffectPrice")
    private String fisaffectprice;
    @JSONField(name="FIsAffectPlan")
    private String fisaffectplan;
    @JSONField(name="FIsAffectCost")
    private String fisaffectcost;

    public Fentityinvpty(int fentryid, Finvptyid finvptyid, String fisenable, String fisaffectprice, String fisaffectplan, String fisaffectcost) {
        this.fentryid = fentryid;
        this.finvptyid = finvptyid;
        this.fisenable = fisenable;
        this.fisaffectprice = fisaffectprice;
        this.fisaffectplan = fisaffectplan;
        this.fisaffectcost = fisaffectcost;
    }

    public void setFentryid(int fentryid) {
         this.fentryid = fentryid;
     }
     public int getFentryid() {
         return fentryid;
     }

    public void setFinvptyid(Finvptyid finvptyid) {
         this.finvptyid = finvptyid;
     }
     public Finvptyid getFinvptyid() {
         return finvptyid;
     }

    public void setFisenable(String fisenable) {
         this.fisenable = fisenable;
     }
     public String getFisenable() {
         return fisenable;
     }

    public void setFisaffectprice(String fisaffectprice) {
         this.fisaffectprice = fisaffectprice;
     }
     public String getFisaffectprice() {
         return fisaffectprice;
     }

    public void setFisaffectplan(String fisaffectplan) {
         this.fisaffectplan = fisaffectplan;
     }
     public String getFisaffectplan() {
         return fisaffectplan;
     }

    public void setFisaffectcost(String fisaffectcost) {
         this.fisaffectcost = fisaffectcost;
     }
     public String getFisaffectcost() {
         return fisaffectcost;
     }

}