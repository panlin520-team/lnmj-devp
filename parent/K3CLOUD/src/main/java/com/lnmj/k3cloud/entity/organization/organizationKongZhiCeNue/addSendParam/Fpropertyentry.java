package com.lnmj.k3cloud.entity.organization.organizationKongZhiCeNue.addSendParam;
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
public class Fpropertyentry {

    @JSONField(name="FPropEntryId")
    private String fpropentryid;
    @JSONField(name="FPropertyGroup")
    private String fpropertygroup;
    @JSONField(name="FKey")
    private String fkey;
    @JSONField(name="FPropertyName")
    private String fpropertyname;
    @JSONField(name="FControlTypeId")
    private String fcontroltypeid;
    @JSONField(name="FLocked")
    private String flocked;
    @JSONField(name="FDefaultValue")
    private String fdefaultvalue;
  

}