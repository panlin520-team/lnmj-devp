package com.lnmj.k3cloud.entity.stock.PurchaseReturnSaveParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

/**
 * Auto-generated: 2019-11-13 10:18:40
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class FPURMRBFIN {

    @JSONField(name ="FEntryId")
    private Integer FEntryId;
    @JSONField(name ="FSettleOrgId")
    private FSettleOrgID FSettleOrgId;
    @JSONField(name ="FSETTLETYPEID")
    private FSettleTypeID FSETTLETYPEID;
    @JSONField(name ="FSettleCurrId")
    private FSettleCurrID FSettleCurrId;
    @JSONField(name ="FPAYCONDITIONID")
    private FPAYCONDITIONID FPAYCONDITIONID;
    @JSONField(name ="FIsIncludedTax")
    private String FIsIncludedTax;
    @JSONField(name ="FPRICETIMEPOINT")
    private String FPRICETIMEPOINT;
    @JSONField(name ="FPRICELISTID")
    private FPriceListId FPRICELISTID;
    @JSONField(name ="FDISCOUNTLISTID")
    private FDiscountListId FDISCOUNTLISTID;
    @JSONField(name ="FLOCALCURRID")
    private FLocalCurrId FLOCALCURRID;
    @JSONField(name ="FEXCHANGETYPEID")
    private FExchangeTypeID FEXCHANGETYPEID;
    @JSONField(name ="FEXCHANGERATE")
    private Integer FEXCHANGERATE;
    @JSONField(name ="FISPRICEEXCLUDETAX")
    private String FISPRICEEXCLUDETAX;
}