package com.lnmj.k3cloud.entity.organization.organizationKongZhiCeNue.addSendParam;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2020-01-16 14:16:35
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrganizationKongZhiCeNueAddModel {

    @JSONField(name="FPolicyID")
    private String fpolicyid;
    @JSONField(name="FBaseDataTypeId")
    private Fbasedatatypeid fbasedatatypeid;
    @JSONField(name="FCreateOrgId")
    private Fcreateorgid fcreateorgid;
    @JSONField(name="FModifierId")
    private Fmodifierid fmodifierid;
    @JSONField(name="FModifyDate")
    private Date fmodifydate;
    @JSONField(name="FCreateDate")
    private Date fcreatedate;
    @JSONField(name="FBillNo")
    private String fbillno;
    @JSONField(name="FCreatorId")
    private Fcreatorid fcreatorid;
    @JSONField(name="FCheckFields")
    private String fcheckfields;
    @JSONField(name="FTargetOrgEntrys")
    private List<Ftargetorgentrys> ftargetorgentrys;
 
}