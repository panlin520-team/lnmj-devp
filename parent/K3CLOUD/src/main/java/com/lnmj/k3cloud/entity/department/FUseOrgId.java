/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.department;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-11 18:16:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FUseOrgId {

    @JSONField(name ="FNumber")
    private String FNumber;

    public FUseOrgId(String FNumber) {
        this.FNumber = FNumber;
    }

    public FUseOrgId() {
    }

    public void setFNumber(String FNumber) {
         this.FNumber = FNumber;
     }
     public String getFNumber() {
         return FNumber;
     }

}