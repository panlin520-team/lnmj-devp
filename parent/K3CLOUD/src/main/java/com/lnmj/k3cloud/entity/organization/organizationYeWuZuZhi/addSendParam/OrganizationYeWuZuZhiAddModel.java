package com.lnmj.k3cloud.entity.organization.organizationYeWuZuZhi.addSendParam;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.print.DocFlavor;

/**
 * Auto-generated: 2020-01-15 9:55:34
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrganizationYeWuZuZhiAddModel {

    @JSONField(name="FBIZRELATIONID")
    private String fbizrelationid;
    @JSONField(name="FBRTypeId")
    private Fbrtypeid fbrtypeid;
    @JSONField(name="FDescription")
    private String fdescription;
/*    @JSONField(name="FBizrelationEntry")
    private List<Fbizrelationentry> fbizrelationentry;*/
    @JSONField(name="FBizrelationEntry1")
    private List<Fbizrelationentry1> fbizrelationentry1;


}