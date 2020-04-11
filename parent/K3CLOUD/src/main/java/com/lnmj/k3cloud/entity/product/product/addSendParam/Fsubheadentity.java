package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fsubheadentity {

    @JSONField(name="FEntryId")
    private int fentryid;

    public Fsubheadentity(int fentryid) {
        this.fentryid = fentryid;
    }

    public void setFentryid(int fentryid) {
         this.fentryid = fentryid;
     }
     public int getFentryid() {
         return fentryid;
     }

}