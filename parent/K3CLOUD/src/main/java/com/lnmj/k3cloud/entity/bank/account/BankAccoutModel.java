package com.lnmj.k3cloud.entity.bank.account;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/**
 * Auto-generated: 2020-01-13 10:5:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class BankAccoutModel {

    @JSONField(name ="FBANKACNTID")
    private String fbankacntid;    //实体主键
    @JSONField(name ="FCreateOrgId")
    private Fcreateorgid fcreateorgid;  //创建组织

    @JSONField(name ="FCreatorId")
    private Fcreatorid FCreatorId;  //创建人
    @JSONField(name ="FModifierId")
    private Fcreatorid FModifierId;  //修改人
    @JSONField(name ="FCreateDate")
    private String FCreateDate;  //创建日期
    @JSONField(name ="FModifyDate")
    private String FModifyDate;  //修改日期
    @JSONField(name ="FApproveDate")
    private String FApproveDate;  //审核日期
    @JSONField(name ="FApproverId")
    private Fcreatorid FApproverId;  //审核人
    @JSONField(name ="FForbidDate")
    private String FForbidDate;  //禁用日期
    @JSONField(name ="FForbidderID")
    private Fcreatorid FForbidderID;  //禁用人
    @JSONField(name ="FBANKADDRESS")
    private String FBANKADDRESS;  //银行地址
    @JSONField(name ="FCURRENCYID")
    private String FCURRENCYID;  //币别
    @JSONField(name ="FDocumentStatus")
    private String FDocumentStatus;  //数据状态
    @JSONField(name ="FForbidStatus")
    private String FForbidStatus;  //禁用状态

    @JSONField(name ="FNumber")
    private String fnumber;     //银行账号
    @JSONField(name ="FBANKID")
    private Fbankid fbankid;    //开户银行
    @JSONField(name ="FName")
    private String fname;   //账户名称
    @JSONField(name ="FInOrOut")
    private String finorout;    //账户收支属性
    @JSONField(name ="FACNTBRANCHNUMBER")
    private String facntbranchnumber;   // 开户银行联行号
    @JSONField(name ="FBankType")
    private Fbanktype fbanktype;    //银行
    @JSONField(name ="FProvince")
    private Fprovince fprovince;    //省
    @JSONField(name ="FCity")
    private Fcity fcity;    //市
    @JSONField(name ="FDistrict")
    private Fdistrict fdistrict;    //区
    @JSONField(name ="FUseOrgId")
    private Fuseorgid fuseorgid;    //使用组织
    @JSONField(name ="FACNTAREACODE")
    private String facntareacode;   //账户地区码
    @JSONField(name ="FACNTAREANAME")
    private String facntareaname;   //账户地区名
    @JSONField(name ="FIsCancel")
    private String fiscancel;       //是否销户
    @JSONField(name ="FIsFundUp")
    private String fisfundup;       //资金上划
    @JSONField(name ="FISDEFAULTBANK")
    private String fisdefaultbank;  //默认账号
    @JSONField(name ="FIsSupBank")
    private String fissupbank;      //支持网银
    @JSONField(name ="FINNERACCOUNTID")
    private Finneraccountid finneraccountid;    //内部账户
    @JSONField(name ="FDescription")
    private String fdescription;        //备注
    @JSONField(name ="FSRCBILLID")
    private String fsrcbillid;     //源单内码
    @JSONField(name ="FUpType")
    private String fuptype;     //上划方式
    @JSONField(name ="FUpAmount")
    private String fupamount;      //上划金额
    @JSONField(name ="FBaseAmount")
    private String fbaseamount;    //留底金额
    @JSONField(name ="FMinUpAmount")
    private String fminupamount;   //最小上划金额
    @JSONField(name ="FACCOUNTTYPE")
    private String faccounttype;    //账号类型
    @JSONField(name ="FLICENSEKEY")
    private String flicensekey;     //授权密码
    @JSONField(name ="FPUBLICACNT")
    private Fpublicacnt fpublicacnt;        //对公账号
    @JSONField(name ="FBankServiceLoginId")
    private String fbankserviceloginid;     //登录配置编号
    @JSONField(name ="FBankServiceLoginDesc")
    private String fbankservicelogindesc;   //银行版本
    @JSONField(name ="FIsSupBBC")
    private String fissupbbc;       //支持BBC分销门户显示

}