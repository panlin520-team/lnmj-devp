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
public class Forgid1 {

    @JSONField(name="FNumber")
    private String fnumber;

    public Forgid1(String fnumber) {
        this.fnumber = fnumber;
    }
}