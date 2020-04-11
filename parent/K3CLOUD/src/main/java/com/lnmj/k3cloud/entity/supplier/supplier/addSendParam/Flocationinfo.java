package com.lnmj.k3cloud.entity.supplier.supplier.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Flocationinfo {

    @JSONField(name="FLocationId")
    private int flocationid;
    @JSONField(name="FLocName")
    private String flocname;
    @JSONField(name="FLocNewContact")
    private Flocnewcontact flocnewcontact;
    @JSONField(name="FLocAddress")
    private String flocaddress;
    @JSONField(name="FLocMobile")
    private String flocmobile;
    public void setFlocationid(int flocationid) {
         this.flocationid = flocationid;
     }
     public int getFlocationid() {
         return flocationid;
     }

    public void setFlocname(String flocname) {
         this.flocname = flocname;
     }
     public String getFlocname() {
         return flocname;
     }

    public void setFlocnewcontact(Flocnewcontact flocnewcontact) {
         this.flocnewcontact = flocnewcontact;
     }
     public Flocnewcontact getFlocnewcontact() {
         return flocnewcontact;
     }

    public void setFlocaddress(String flocaddress) {
         this.flocaddress = flocaddress;
     }
     public String getFlocaddress() {
         return flocaddress;
     }

    public void setFlocmobile(String flocmobile) {
         this.flocmobile = flocmobile;
     }
     public String getFlocmobile() {
         return flocmobile;
     }

}