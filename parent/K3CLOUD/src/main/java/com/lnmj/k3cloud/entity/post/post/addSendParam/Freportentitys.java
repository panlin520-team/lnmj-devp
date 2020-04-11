package com.lnmj.k3cloud.entity.post.post.addSendParam;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-11 19:8:51
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Freportentitys {

    @JSONField(name="FPOSTREPORTID")
    private int fpostreportid;
    @JSONField(name="FSupiorIsValid")
    private String fsupiorisvalid;
    @JSONField(name="FSuperiorReportType")
    private Fsuperiorreporttype fsuperiorreporttype;
    @JSONField(name="FSuperiorPost")
    private Fsuperiorpost fsuperiorpost;
    @JSONField(name="FSuperiorDescaription")
    private String fsuperiordescaription;
    @JSONField(name="FSuperiorStime")
    private Date fsuperiorstime;
    @JSONField(name="FSuperiorEtime")
    private Date fsuperioretime;
    @JSONField(name="FIsHrCreate")
    private String fishrcreate;
    public void setFpostreportid(int fpostreportid) {
         this.fpostreportid = fpostreportid;
     }
     public int getFpostreportid() {
         return fpostreportid;
     }

    public void setFsupiorisvalid(String fsupiorisvalid) {
         this.fsupiorisvalid = fsupiorisvalid;
     }
     public String getFsupiorisvalid() {
         return fsupiorisvalid;
     }

    public void setFsuperiorreporttype(Fsuperiorreporttype fsuperiorreporttype) {
         this.fsuperiorreporttype = fsuperiorreporttype;
     }
     public Fsuperiorreporttype getFsuperiorreporttype() {
         return fsuperiorreporttype;
     }

    public void setFsuperiorpost(Fsuperiorpost fsuperiorpost) {
         this.fsuperiorpost = fsuperiorpost;
     }
     public Fsuperiorpost getFsuperiorpost() {
         return fsuperiorpost;
     }

    public void setFsuperiordescaription(String fsuperiordescaription) {
         this.fsuperiordescaription = fsuperiordescaription;
     }
     public String getFsuperiordescaription() {
         return fsuperiordescaription;
     }

    public void setFsuperiorstime(Date fsuperiorstime) {
         this.fsuperiorstime = fsuperiorstime;
     }
     public Date getFsuperiorstime() {
         return fsuperiorstime;
     }

    public void setFsuperioretime(Date fsuperioretime) {
         this.fsuperioretime = fsuperioretime;
     }
     public Date getFsuperioretime() {
         return fsuperioretime;
     }

    public void setFishrcreate(String fishrcreate) {
         this.fishrcreate = fishrcreate;
     }
     public String getFishrcreate() {
         return fishrcreate;
     }

}