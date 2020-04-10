/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.base;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-11 13:51:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class BaseQueryBean {
    @JSONField(name = "FormId")
    private String FormId;  //必填：业务对象表单Id
    @JSONField(name = "FieldKeys")
    private String FieldKeys;   //必填：需查询的字段key集合，格式："key1,key2,..."
    @JSONField(name = "FilterString")
    private String FilterString;
    @JSONField(name = "OrderString")
    private String OrderString;
    @JSONField(name = "TopRowCount")
    private Integer TopRowCount;
    @JSONField(name = "StartRow")
    private Integer StartRow;
    @JSONField(name = "Limit")
    private Integer Limit;
}