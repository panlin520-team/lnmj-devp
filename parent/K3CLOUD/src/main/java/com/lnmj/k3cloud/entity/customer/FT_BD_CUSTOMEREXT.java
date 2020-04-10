/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.customer;
import java.util.Date;

/**
 * Auto-generated: 2019-11-11 14:30:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FT_BD_CUSTOMEREXT {

    private int FEntryId;
    private String FEnableSL;
    private String FFreezeLimit;
    private FFreezeOperator FFreezeOperator;
    private Date FFreezeDate;
    private FPROVINCE FPROVINCE;
    private FCITY FCITY;
    private FDefaultConsiLoc FDefaultConsiLoc;
    private FDefaultSettleLoc FDefaultSettleLoc;
    private FDefaultPayerLoc FDefaultPayerLoc;
    private FDefaultContact FDefaultContact;
    private int FMarginLevel;
    private String FDebitCard;
    private FSettleId FSettleId;
    private FChargeId FChargeId;
    public void setFEntryId(int FEntryId) {
         this.FEntryId = FEntryId;
     }
     public int getFEntryId() {
         return FEntryId;
     }

    public void setFEnableSL(String FEnableSL) {
         this.FEnableSL = FEnableSL;
     }
     public String getFEnableSL() {
         return FEnableSL;
     }

    public void setFFreezeLimit(String FFreezeLimit) {
         this.FFreezeLimit = FFreezeLimit;
     }
     public String getFFreezeLimit() {
         return FFreezeLimit;
     }

    public void setFFreezeOperator(FFreezeOperator FFreezeOperator) {
         this.FFreezeOperator = FFreezeOperator;
     }
     public FFreezeOperator getFFreezeOperator() {
         return FFreezeOperator;
     }

    public void setFFreezeDate(Date FFreezeDate) {
         this.FFreezeDate = FFreezeDate;
     }
     public Date getFFreezeDate() {
         return FFreezeDate;
     }

    public void setFPROVINCE(FPROVINCE FPROVINCE) {
         this.FPROVINCE = FPROVINCE;
     }
     public FPROVINCE getFPROVINCE() {
         return FPROVINCE;
     }

    public void setFCITY(FCITY FCITY) {
         this.FCITY = FCITY;
     }
     public FCITY getFCITY() {
         return FCITY;
     }

    public void setFDefaultConsiLoc(FDefaultConsiLoc FDefaultConsiLoc) {
         this.FDefaultConsiLoc = FDefaultConsiLoc;
     }
     public FDefaultConsiLoc getFDefaultConsiLoc() {
         return FDefaultConsiLoc;
     }

    public void setFDefaultSettleLoc(FDefaultSettleLoc FDefaultSettleLoc) {
         this.FDefaultSettleLoc = FDefaultSettleLoc;
     }
     public FDefaultSettleLoc getFDefaultSettleLoc() {
         return FDefaultSettleLoc;
     }

    public void setFDefaultPayerLoc(FDefaultPayerLoc FDefaultPayerLoc) {
         this.FDefaultPayerLoc = FDefaultPayerLoc;
     }
     public FDefaultPayerLoc getFDefaultPayerLoc() {
         return FDefaultPayerLoc;
     }

    public void setFDefaultContact(FDefaultContact FDefaultContact) {
         this.FDefaultContact = FDefaultContact;
     }
     public FDefaultContact getFDefaultContact() {
         return FDefaultContact;
     }

    public void setFMarginLevel(int FMarginLevel) {
         this.FMarginLevel = FMarginLevel;
     }
     public int getFMarginLevel() {
         return FMarginLevel;
     }

    public void setFDebitCard(String FDebitCard) {
         this.FDebitCard = FDebitCard;
     }
     public String getFDebitCard() {
         return FDebitCard;
     }

    public void setFSettleId(FSettleId FSettleId) {
         this.FSettleId = FSettleId;
     }
     public FSettleId getFSettleId() {
         return FSettleId;
     }

    public void setFChargeId(FChargeId FChargeId) {
         this.FChargeId = FChargeId;
     }
     public FChargeId getFChargeId() {
         return FChargeId;
     }

}