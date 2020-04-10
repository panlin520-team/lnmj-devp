package com.lnmj.k3cloud.entity.post.post.addSendParam;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-11 19:8:51
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class PostAddModel {

    @JSONField(name="FPOSTID")
    private int fpostid;
    @JSONField(name="FCreateOrgId")
    private Fcreateorgid fcreateorgid;
    @JSONField(name="FNumber")
    private String fnumber;
    @JSONField(name="FUseOrgId")
    private Fuseorgid fuseorgid;
    @JSONField(name="FName")
    private String fname;
    @JSONField(name="FHelpCode")
    private String fhelpcode;
    @JSONField(name="FDept")
    private Fdept fdept;
    @JSONField(name="FEffectDate")
    private String feffectdate;
    @JSONField(name="FLapseDate")
    private String flapsedate;
    @JSONField(name="FDESCRIPTIONS")
    private String fdescriptions;
    @JSONField(name="FHRPostSubHead")
    private Fhrpostsubhead fhrpostsubhead;
    @JSONField(name="FSHRMapEntity")
    private Fshrmapentity fshrmapentity;
    @JSONField(name="FPostRoleEntitys")
    private List<Fpostroleentitys> fpostroleentitys;
    @JSONField(name="FSubReportEntity")
    private List<Fsubreportentity> fsubreportentity;
    @JSONField(name="FReportEntitys")
    private List<Freportentitys> freportentitys;

}