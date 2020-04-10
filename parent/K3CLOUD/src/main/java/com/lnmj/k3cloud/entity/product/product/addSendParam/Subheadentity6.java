package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */

@Data
public class Subheadentity6 {

    @JSONField(name="FEntryId")
    private int fentryid;
    @JSONField(name="FCheckIncoming")
    private Boolean fcheckincoming;
    @JSONField(name="FCheckProduct")
    private Boolean fcheckproduct;
    @JSONField(name="FCheckStock")
    private Boolean fcheckstock;
    @JSONField(name="FCheckReturn")
    private Boolean fcheckreturn;
    @JSONField(name="FCheckDelivery")
    private Boolean fcheckdelivery;
    @JSONField(name="FEnableCyclistQCSTK")
    private Boolean fenablecyclistqcstk;
    @JSONField(name="FStockCycle")
    private int fstockcycle;
    @JSONField(name="FEnableCyclistQCSTKEW")
    private Boolean fenablecyclistqcstkew;
    @JSONField(name="FEWLeadDay")
    private int fewleadday;
    @JSONField(name="FIncSampSchemeId")
    private Fincsampschemeid fincsampschemeid;
    @JSONField(name="FIncQcSchemeId")
    private Fincqcschemeid fincqcschemeid;
    @JSONField(name="FInspectGroupId")
    private Finspectgroupid finspectgroupid;
    @JSONField(name="FInspectorId")
    private Finspectorid finspectorid;
    @JSONField(name="FCheckEntrusted")
    private Boolean fcheckentrusted;
    @JSONField(name="FCheckOther")
    private Boolean fcheckother;

    public Subheadentity6() {
    }

    public Subheadentity6(int fentryid, Boolean fcheckincoming, Boolean fcheckproduct, Boolean fcheckstock, Boolean fcheckreturn, Boolean fcheckdelivery, Boolean fenablecyclistqcstk, int fstockcycle, Boolean fenablecyclistqcstkew, int fewleadday, Fincsampschemeid fincsampschemeid, Fincqcschemeid fincqcschemeid, Finspectgroupid finspectgroupid, Finspectorid finspectorid, Boolean fcheckentrusted, Boolean fcheckother) {
        this.fentryid = fentryid;
        this.fcheckincoming = fcheckincoming;
        this.fcheckproduct = fcheckproduct;
        this.fcheckstock = fcheckstock;
        this.fcheckreturn = fcheckreturn;
        this.fcheckdelivery = fcheckdelivery;
        this.fenablecyclistqcstk = fenablecyclistqcstk;
        this.fstockcycle = fstockcycle;
        this.fenablecyclistqcstkew = fenablecyclistqcstkew;
        this.fewleadday = fewleadday;
        this.fincsampschemeid = fincsampschemeid;
        this.fincqcschemeid = fincqcschemeid;
        this.finspectgroupid = finspectgroupid;
        this.finspectorid = finspectorid;
        this.fcheckentrusted = fcheckentrusted;
        this.fcheckother = fcheckother;
    }
}