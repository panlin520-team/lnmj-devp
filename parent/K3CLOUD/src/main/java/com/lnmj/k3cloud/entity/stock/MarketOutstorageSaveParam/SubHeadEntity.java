/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.MarketOutstorageSaveParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

/**
 * Auto-generated: 2019-11-11 17:28:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SubHeadEntity {
    @JSONField(name = "FEntryId")
    private Integer FEntryId;
    @JSONField(name = "FSettleCurrID")
    private FSettleCurrID FSettleCurrID;
    @JSONField(name = "FThirdBillNo")
    private String FThirdBillNo;
    @JSONField(name = "FThirdBillId")
    private String FThirdBillId;
    @JSONField(name = "FThirdSrcType")
    private String FThirdSrcType;
    @JSONField(name = "FSettleOrgID")
    private FSettleOrgID FSettleOrgID;
    @JSONField(name = "FSettleTypeID")
    private FSettleTypeID FSettleTypeID;
    @JSONField(name = "FReceiptConditionID")
    private FReceiptConditionID FReceiptConditionID;
    @JSONField(name = "FPriceListId")
    private FPriceListId FPriceListId;
    @JSONField(name = "FDiscountListId")
    private FDiscountListId FDiscountListId;
    @JSONField(name = "FIsIncludedTax")
    private Boolean FIsIncludedTax;
    @JSONField(name = "FLocalCurrID")
    private FLocalCurrId FLocalCurrID;
    @JSONField(name = "FExchangeTypeID")
    private FExchangeTypeID FExchangeTypeID;
    @JSONField(name = "FExchangeRate")
    private Integer FExchangeRate;
    @JSONField(name = "FIsPriceExcludeTax")
    private Boolean FIsPriceExcludeTax;
    @JSONField(name = "FBuyerNick")
    private String FBuyerNick;
    @JSONField(name = "FReceiverAddress")
    private String FReceiverAddress;
    @JSONField(name = "FReceiverName")
    private String FReceiverName;
    @JSONField(name = "FReceiverMobile")
    private String FReceiverMobile;
    @JSONField(name = "FReceiverCountry")
    private String FReceiverCountry;
    @JSONField(name = "FReceiverState")
    private String FReceiverState;
    @JSONField(name = "FReceiverCity")
    private String FReceiverCity;
    @JSONField(name = "FReceiverDistrict")
    private String FReceiverDistrict;
    @JSONField(name = "FReceiverPhone")
    private String FReceiverPhone;
}