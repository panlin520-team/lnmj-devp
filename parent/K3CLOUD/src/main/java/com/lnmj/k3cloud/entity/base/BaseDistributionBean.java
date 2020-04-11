package com.lnmj.k3cloud.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-09 16:24:31
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class BaseDistributionBean {

    @JSONField(name = "PkIds")//（使用被分配的基础资料内码集合必录）
    private String pkIds;
    @JSONField(name ="TOrgIds")//（使用目标组织内码集合必录）
    private String tOrgIds;
    @JSONField(name ="IsAutoSubmitAndAudit")
    private boolean isAutoSubmitAndAudit;


}