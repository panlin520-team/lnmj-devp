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
public class Fuserid {

    @JSONField(name ="FUSERACCOUNT")
    private String fuseraccount;

    public void setFuseraccount(String fuseraccount) {
         this.fuseraccount = fuseraccount;
     }
     public String getFuseraccount() {
         return fuseraccount;
     }

}