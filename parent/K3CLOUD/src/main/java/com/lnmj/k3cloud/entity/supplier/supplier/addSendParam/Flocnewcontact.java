package com.lnmj.k3cloud.entity.supplier.supplier.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Flocnewcontact {

    @JSONField(name="FNUMBER")
    private String fnumber;

    public Flocnewcontact(String fnumber) {
        this.fnumber = fnumber;
    }
}