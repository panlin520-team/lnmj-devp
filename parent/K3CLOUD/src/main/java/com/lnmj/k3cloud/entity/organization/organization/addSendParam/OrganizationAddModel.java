package com.lnmj.k3cloud.entity.organization.organization.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-09 18:1:55
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrganizationAddModel {
    @JSONField(name = "FOrgID")
    private int forgid;
    @JSONField(name ="FNumber")
    private String fnumber;
    @JSONField(name ="FName")
    private String fname;
    @JSONField(name ="FOrgFormID")
    private String forgformid;
    @JSONField(name ="FIsAccountOrg")
    private Boolean fisaccountorg;
    @JSONField(name ="FIsBusinessOrg")
    private Boolean fisbusinessorg;
    @JSONField(name ="FParentID")
    private Fparentid fparentid;
    @JSONField(name ="FOrgFunctions")
    private String forgfunctions;
    @JSONField(name ="FAcctOrgType")
    private String facctorgtype;
    @JSONField(name ="FSaleBox")
    private Boolean fSaleBox;//销售职能
    @JSONField(name ="FPurchaseBox")
    private Boolean fPurchaseBox;//采购职能
    @JSONField(name ="FStockBox")
    private Boolean fStockBox;//库存职能
    @JSONField(name ="FFactoryBox")
    private Boolean fFactoryBox;//工厂职能
    @JSONField(name ="FQualityBox")
    private Boolean fQualityBox;//质检职能
    @JSONField(name ="FClearingBox")
    private Boolean fClearingBox;//结算职能
    @JSONField(name ="FAssetBox")
    private Boolean fAssetBox;//资产职能
    @JSONField(name ="FCapitalBox")
    private Boolean fCapitalBox;//资金职能
    @JSONField(name ="FReceiptAndPayBox")
    private Boolean fReceiptAndPayBox;//收付职能
    @JSONField(name ="FMarketing")
    private Boolean fMarketing;//营销职能
    @JSONField(name ="FService")
    private Boolean fService;//服务职能
    @JSONField(name ="FShareCenter")
    private Boolean fShareCenter;//共享中心

}