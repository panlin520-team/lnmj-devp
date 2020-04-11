/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.PurchaseInstorageSaveParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

/**
 * Auto-generated: 2019-11-09 16:19:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FInStockFin {
    @JSONField(name = "FEntryId")
    private Integer FEntryId;
    @JSONField(name = "FSettleOrgId")
    private FSettleOrgID FSettleOrgId;  //结算组织
    @JSONField(name = "FSettleTypeId")
    private FSettleTypeID FSettleTypeId;    //币别必录
    @JSONField(name = "FPayConditionId")
    private FPayConditionId FPayConditionId;
    @JSONField(name = "FSettleCurrId")
    private FSettleCurrID FSettleCurrId;    //结算币别
    @JSONField(name = "FIsIncludedTax")
    private String FIsIncludedTax;
    @JSONField(name = "FPriceTimePoint")
    private String FPriceTimePoint;     //定价时点
    @JSONField(name = "FPriceListId")
    private FPriceListId FPriceListId;
    @JSONField(name = "FDiscountListId")
    private FDiscountListId FDiscountListId;
    @JSONField(name = "FLocalCurrId")
    private FLocalCurrId FLocalCurrId;
    @JSONField(name = "FExchangeTypeId")
    private FExchangeTypeID FExchangeTypeId;
    @JSONField(name = "FExchangeRate")
    private Integer FExchangeRate;
    @JSONField(name = "FISPRICEEXCLUDETAX")
    private String FISPRICEEXCLUDETAX;

}