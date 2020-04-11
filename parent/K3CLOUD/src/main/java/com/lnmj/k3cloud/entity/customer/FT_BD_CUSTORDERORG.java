/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.customer;

/**
 * Auto-generated: 2019-11-11 14:30:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FT_BD_CUSTORDERORG {

    private int FEntryID;
    private FOrderOrgId FOrderOrgId;
    private String FIsDefaultOrderOrg;
    public void setFEntryID(int FEntryID) {
         this.FEntryID = FEntryID;
     }
     public int getFEntryID() {
         return FEntryID;
     }

    public void setFOrderOrgId(FOrderOrgId FOrderOrgId) {
         this.FOrderOrgId = FOrderOrgId;
     }
     public FOrderOrgId getFOrderOrgId() {
         return FOrderOrgId;
     }

    public void setFIsDefaultOrderOrg(String FIsDefaultOrderOrg) {
         this.FIsDefaultOrderOrg = FIsDefaultOrderOrg;
     }
     public String getFIsDefaultOrderOrg() {
         return FIsDefaultOrderOrg;
     }

}