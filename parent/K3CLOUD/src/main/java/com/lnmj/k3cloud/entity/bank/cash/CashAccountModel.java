package com.lnmj.k3cloud.entity.bank.cash;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.bank.cash.Fcreatorid;
import lombok.Data;
/**
 * Auto-generated: 2020-01-13 14:47:23
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class CashAccountModel {
    @JSONField(name ="FID")
    private String fid;    //实体主键
    @JSONField(name ="FNumber")
    private String fnumber;     //现金账号
    @JSONField(name ="FCreateOrgId")
    private Fcreateorgid fcreateorgid;  //创建组织
    @JSONField(name ="FUseOrgId")
    private Fuseorgid fuseorgid;    //使用组织
    @JSONField(name ="FName")
    private String fname;   //账户名称
    @JSONField(name ="FDescription")
    private String fdescription;    //备注
    @JSONField(name ="FDEFCREATORID")
    private Fdefcreatorid fdefcreatorid;    //默认用户

    @JSONField(name ="FDocumentStatus")
    private String fdocumentstatus;    //数据状态
    @JSONField(name ="FForbidStatus")
    private String fforbidstatus;    //禁用状态
    @JSONField(name ="FCreatorId")
    private Fcreatorid fcreatorid;    //创建人
    @JSONField(name ="FModifierId")
    private Fcreatorid fmodifierid;    //修改人
    @JSONField(name ="FCreateDate")
    private String fcreatedate;    //创建日期
    @JSONField(name ="FModifyDate")
    private String fmodifydate;    //修改日期
    @JSONField(name ="FForbidDate")
    private String fforbiddate;    //禁用日期
    @JSONField(name ="FForbidderID")
    private Fcreatorid fforbidderid;    //禁用人
    @JSONField(name ="FApproverId")
    private Fcreatorid fapproverid;    //审核人
    @JSONField(name ="FApproveDate")
    private String fapprovedate;    //审核日期

}