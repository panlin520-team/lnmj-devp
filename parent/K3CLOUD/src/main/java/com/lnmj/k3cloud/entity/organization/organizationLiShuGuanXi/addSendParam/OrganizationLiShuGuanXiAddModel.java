package com.lnmj.k3cloud.entity.organization.organizationLiShuGuanXi.addSendParam;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2020-01-13 16:57:40
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrganizationLiShuGuanXiAddModel {
    @JSONField(name = "FAFFILIATIONID")
    private String faffiliationid;
/*    @JSONField(name = "FNumber")
    private String fnumber;
    @JSONField(name = "FName")
    private String fname;*/
/*    @JSONField(name = "FType")
    private Ftype ftype;*/
    @JSONField(name = "FRootOrgID")
    private Frootorgid frootorgid;
   /* @JSONField(name = "FStartDate")
    private String fstartdate;
    @JSONField(name = "FEndDate")
    private String fenddate;*/
    @JSONField(name = "FDescription")
    private String fdescription;
    @JSONField(name = "FIsDefault")
    private String fisdefault;
    @JSONField(name = "FAffiliationEntry")
    private List<Faffiliationentry> faffiliationentry;
    @JSONField(name = "FBackupOrgEntity")
    private List<Fbackuporgentity> fbackuporgentity;


}