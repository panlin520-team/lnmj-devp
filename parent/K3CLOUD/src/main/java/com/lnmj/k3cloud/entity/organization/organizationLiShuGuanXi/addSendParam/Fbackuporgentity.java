package com.lnmj.k3cloud.entity.organization.organizationLiShuGuanXi.addSendParam;
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
public class Fbackuporgentity {

    @JSONField(name ="FBackupOrg")
    private String fbackuporg;

    public Fbackuporgentity(String fbackuporg) {
        this.fbackuporg = fbackuporg;
    }
}