package com.lnmj.k3cloud.entity.employees.employees.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-10 19:37:19
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fpostdept {

    @JSONField(name ="FNumber")
    private String fnumber;

    public Fpostdept(String fnumber) {
        this.fnumber = fnumber;
    }

    public void setFnumber(String fnumber) {
         this.fnumber = fnumber;
     }
     public String getFnumber() {
         return fnumber;
     }

}