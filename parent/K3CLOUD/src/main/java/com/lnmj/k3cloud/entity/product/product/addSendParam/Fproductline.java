package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fproductline {

    @JSONField(name="FNUMBER")
    private String fnumber;

    public Fproductline(String fnumber) {
        this.fnumber = fnumber;
    }

    public void setFnumber(String fnumber) {
         this.fnumber = fnumber;
     }
     public String getFnumber() {
         return fnumber;
     }

}