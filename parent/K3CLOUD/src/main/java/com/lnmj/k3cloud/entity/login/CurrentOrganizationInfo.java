/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.login;
import java.util.List;

/**
 * Auto-generated: 2019-11-08 19:53:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CurrentOrganizationInfo {

    private Integer ID;
    private String AcctOrgType;
    private String Name;
    private List<Integer> FunctionIds;
    public void setID(Integer ID) {
         this.ID = ID;
     }
     public Integer getID() {
         return ID;
     }

    public void setAcctOrgType(String AcctOrgType) {
         this.AcctOrgType = AcctOrgType;
     }
     public String getAcctOrgType() {
         return AcctOrgType;
     }

    public void setName(String Name) {
         this.Name = Name;
     }
     public String getName() {
         return Name;
     }

    public void setFunctionIds(List<Integer> FunctionIds) {
         this.FunctionIds = FunctionIds;
     }
     public List<Integer> getFunctionIds() {
         return FunctionIds;
     }

}