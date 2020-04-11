package com.lnmj.k3cloud.entity.organization.organizationForm.addSendParam;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-09 15:2:35
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class FormAddModel {
    @JSONField(name ="FORGFORMID")
    private int forgformid;
    @JSONField(name ="FNumber")//必填
    private String fnumber;
    @JSONField(name ="FISSYSPRESET")
    private String fissyspreset;
    @JSONField(name ="FForbidDate")
    private Date fforbiddate;
    @JSONField(name ="FFORBIDORID")
    private Fforbidorid fforbidorid;
    @JSONField(name ="FAUDITDATE")
    private Date fauditdate;
    @JSONField(name ="FAUDITORID")
    private Fauditorid fauditorid;
    @JSONField(name ="FDescription")
    private String fdescription;
    @JSONField(name ="FModifierId")
    private Fmodifierid fmodifierid;
    @JSONField(name ="FName")//必填
    private String fname;
    @JSONField(name ="FCreatorId")
    private Fcreatorid fcreatorid;
    @JSONField(name ="FModifyDate")
    private Date fmodifydate;
    @JSONField(name ="FCreateDate")
    private Date fcreatedate;


}