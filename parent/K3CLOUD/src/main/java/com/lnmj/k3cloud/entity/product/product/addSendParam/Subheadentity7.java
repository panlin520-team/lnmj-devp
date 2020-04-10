package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Subheadentity7 {

    @JSONField(name="FEntryId")
    private int fentryid;
    @JSONField(name="FSubconUnitId")
    private Fsubconunitid fsubconunitid;
    @JSONField(name="FSubconPriceUnitId")
    private Fsubconpriceunitid fsubconpriceunitid;
    @JSONField(name="FSubBillType")
    private Fsubbilltype fsubbilltype;

    public Subheadentity7() {
    }

    public Subheadentity7(int fentryid, Fsubconunitid fsubconunitid, Fsubconpriceunitid fsubconpriceunitid, Fsubbilltype fsubbilltype) {
        this.fentryid = fentryid;
        this.fsubconunitid = fsubconunitid;
        this.fsubconpriceunitid = fsubconpriceunitid;
        this.fsubbilltype = fsubbilltype;
    }

    public void setFentryid(int fentryid) {
         this.fentryid = fentryid;
     }
     public int getFentryid() {
         return fentryid;
     }

    public void setFsubconunitid(Fsubconunitid fsubconunitid) {
         this.fsubconunitid = fsubconunitid;
     }
     public Fsubconunitid getFsubconunitid() {
         return fsubconunitid;
     }

    public void setFsubconpriceunitid(Fsubconpriceunitid fsubconpriceunitid) {
         this.fsubconpriceunitid = fsubconpriceunitid;
     }
     public Fsubconpriceunitid getFsubconpriceunitid() {
         return fsubconpriceunitid;
     }

    public void setFsubbilltype(Fsubbilltype fsubbilltype) {
         this.fsubbilltype = fsubbilltype;
     }
     public Fsubbilltype getFsubbilltype() {
         return fsubbilltype;
     }

}