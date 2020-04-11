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
public class FSHRMapEntity {

    @JSONField(name ="FSHRMapEntity")
    private int FMAPID;

    public FSHRMapEntity(int FMAPID) {
        this.FMAPID = FMAPID;
    }

    public FSHRMapEntity() {
    }

    public void setFMAPID(int FMAPID) {
         this.FMAPID = FMAPID;
     }
     public int getFMAPID() {
         return FMAPID;
     }

}