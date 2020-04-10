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
public class Fparentid {

    @JSONField(name = "FNumber")
    private String fnumber;

    public Fparentid(String fnumber) {
        this.fnumber = fnumber;
    }
}