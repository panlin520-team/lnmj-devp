package com.lnmj.k3cloud.entity.cost.inventoryaccounting;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2020-01-14 14:14:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Fentity {
    /*
	 实体主键：FEntryID
	 仓库名称：FSTOCKNAME
	 库存组织名称：FSTOCKORGNAME
	 仓库编码：FSTOCKID
	 库存组织编码：FSTOCKORGID
	 货主编码：FOwner
	 货主名称：FOwnerName
	 货主类型：FOwnerType
     */
//    @JSONField(name ="FEntryID")
//    private Integer fentryid;    //实体主键
    @JSONField(name ="FOwnerType")
    private String fownertype;  //货主类型
    @JSONField(name ="FOwner")
    private Fowner fowner;  //货主编码
//    @JSONField(name ="FOwnerName")
//    private String fownername;    //货主名称
//    @JSONField(name ="FSTOCKID")
//    private Fstockid fstockid;  //仓库编码
//    @JSONField(name ="FSTOCKNAME")
//    private String fstockname;    //仓库名称
    @JSONField(name ="FSTOCKORGID")
    private Fstockorgid fstockorgid;    //库存组织编码
//    @JSONField(name ="FSTOCKORGNAME")
//    private String fstockorgname;    //库存组织名称

}