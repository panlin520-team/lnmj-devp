package com.lnmj.k3cloud.entity.stock.MarketReturnSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;
/**
 * Auto-generated: 2019-11-13 10:5:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Subheadentity {

    @JSONField(name ="FEntryId")
    private Integer FEntryId;
    @JSONField(name ="FSettleCurrId")
    private FSettleCurrID FSettleCurrId;
    @JSONField(name ="FThirdBillNo")
    private String FThirdBillNo;
    @JSONField(name ="FThirdBillId")
    private String FThirdBillId;
    @JSONField(name ="FThirdSrcType")
    private String FThirdSrcType;
    @JSONField(name ="FSettleOrgId")
    private FSettleOrgID FSettleOrgId;
    @JSONField(name ="FSettleTypeId")
    private FSettleTypeID FSettleTypeId;
    @JSONField(name ="FChageCondition")
    private FChageCondition FChageCondition;
    @JSONField(name ="FPriceListId")
    private FPriceListId FPriceListId;
    @JSONField(name ="FDiscountListId")
    private FDiscountListId FDiscountListId;
    @JSONField(name ="FLocalCurrId")
    private FLocalCurrId FLocalCurrId;
    @JSONField(name ="FExchangeTypeId")
    private FExchangeTypeID FExchangeTypeId;
    @JSONField(name ="FExchangeRate")
    private Integer FExchangeRate;
    @JSONField(name ="FBuyerNick")
    private String FBuyerNick;
    @JSONField(name ="FReceiverAddress")
    private String FReceiverAddress;
    @JSONField(name ="FReceiverName")
    private String FReceiverName;
    @JSONField(name ="FReceiverMobile")
    private String FReceiverMobile;
    @JSONField(name ="FReceiverCountry")
    private String FReceiverCountry;
    @JSONField(name ="FReceiverState")
    private String FReceiverState;
    @JSONField(name ="FReceiverCity")
    private String FReceiverCity;
    @JSONField(name ="FReceiverDistrict")
    private String FReceiverDistrict;
    @JSONField(name ="FReceiverPhone")
    private String FReceiverPhone;
}