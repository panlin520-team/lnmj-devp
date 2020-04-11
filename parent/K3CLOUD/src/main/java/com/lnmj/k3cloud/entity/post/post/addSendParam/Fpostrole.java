package com.lnmj.k3cloud.entity.post.post.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-11 19:8:51
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fpostrole {

    @JSONField(name = "FNumber")
    private String fnumber;

    public Fpostrole(String fnumber) {
        this.fnumber = fnumber;
    }

    public void setFnumber(String fnumber) {
         this.fnumber = fnumber;
     }
     public String getFnumber() {
         return fnumber;
     }

}