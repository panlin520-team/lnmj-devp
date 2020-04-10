package com.lnmj.k3cloud.entity.supplier;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Allocate_Supplier {

    @JSONField(name="PkIds")
    private String PkIds;
    @JSONField(name="TOrgIds")
    private String TOrgIds;
    @JSONField(name="IsAutoSubmitAndAudit")
    private Boolean IsAutoSubmitAndAudit;

}