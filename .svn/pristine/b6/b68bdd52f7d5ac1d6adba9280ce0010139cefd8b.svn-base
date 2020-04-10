package com.lnmj.k3cloud.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019-11-09 17:38
 * @Description:
 */
@Data
public class BaseData {
    @JSONField(name ="Creator")
    private String creator; //创建者内码（非必录）
    @JSONField(name ="NeedUpDateFields")
    private List<String> needupdatefields;  //需要更新的字段，数组类型，格式：[key1,key2,...]（非必录）
    @JSONField(name ="NeedReturnFields")
    private List<String> needreturnfields;  //需返回结果的字段集合，数组类型，格式：[key,entitykey.key,...]（非必录）
    @JSONField(name ="IsDeleteEntry")
    private String isdeleteentry;   //是否删除已存在的分录，布尔类型，默认true（非必录）
    @JSONField(name ="SubSystemId")
    private String subsystemid;     //表单所在的子系统内码，字符串类型（非必录）
    @JSONField(name ="IsVerifyBaseDataField")
    private String isverifybasedatafield;   //是否验证所有的基础资料有效性，布尔类，默认false（非必录）
    @JSONField(name ="IsEntryBatchFill")
    private String isentrybatchfill;    //是否批量填充分录，默认true（非必录）
    @JSONField(name ="ValidateFlag")
    private String validateflag;        //是否验证标志，布尔类型，默认true（非必录）
    @JSONField(name ="NumberSearch")
    private String numbersearch;        //是否用编码搜索基础资料，布尔类型，默认true（非必录）
    @JSONField(name ="InterationFlags")
    private String interationflags;     //交互标志集合，字符串类型，分号分隔，格式："flag1;flag2;..."（非必录）
    @JSONField(name ="IsAutoSubmitAndAudit")
    private Boolean isautosubmitandaudit;   //是否自动提交与审核，布尔类型，默认false，更新时不能为true
}
