package com.lnmj.k3cloud.entity.organization.organizationYeWuZuZhi.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2020-01-15 9:55:34
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Fbizrelationentry{

    @JSONField(name="FEntryID")
    private int fentryid;
    @JSONField(name="FOrgId")
    private Forgid forgid;
    @JSONField(name="FRelationOrgID")
    private Frelationorgid frelationorgid;
    @JSONField(name="FISDEFAULT")
    private String fisdefault;
    @JSONField(name="FIsdefaultsOrg")
    private String fisdefaultsorg;


}