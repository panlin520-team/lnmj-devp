package com.lnmj.k3cloud.entity.product.product.addSendParam;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

import lombok.Data;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ProductAddModel {

    @JSONField(name="FMATERIALID")
    private Integer fmaterialid;
    @JSONField(name="FCreateOrgId")
    private Fcreateorgid fcreateorgid;
    @JSONField(name="FUseOrgId")
    private Fuseorgid fuseorgid;
    @JSONField(name="FNumber")
    private String fnumber;
    @JSONField(name="FName")
    private String fname;
    @JSONField(name="FSpecification")
    private String fspecification;
    @JSONField(name="FMnemonicCode")
    private String fmnemoniccode;
    @JSONField(name="FOldNumber")
    private String foldnumber;
    @JSONField(name="FDescription")
    private String fdescription;
    @JSONField(name="FMaterialGroup")
    private Fmaterialgroup fmaterialgroup;
    @JSONField(name="FImgStorageType")
    private String fimgstoragetype;
    @JSONField(name="FIsSalseByNet")
    private String fissalsebynet;
    @JSONField(name="FSubHeadEntity")
    private Fsubheadentity fsubheadentity;
    @JSONField(name="SubHeadEntity")
    private Subheadentity subheadentity;
    @JSONField(name="SubHeadEntity1")
    private Subheadentity1 subheadentity1;
    @JSONField(name="SubHeadEntity2")
    private Subheadentity2 subheadentity2;
    @JSONField(name="SubHeadEntity3")
    private Subheadentity3 subheadentity3;
    @JSONField(name="SubHeadEntity4")
    private Subheadentity4 subheadentity4;
    @JSONField(name="SubHeadEntity5")
    private Subheadentity5 subheadentity5;
    @JSONField(name="SubHeadEntity7")
    private Subheadentity7 subheadentity7;
    @JSONField(name="SubHeadEntity6")
    private Subheadentity6 subheadentity6;
    @JSONField(name="FBarCodeEntity_CMK")
    private List<FbarcodeentityCmk> fbarcodeentityCmk;
    @JSONField(name="FSpecialAttributeEntity")
    private List<Fspecialattributeentity> fspecialattributeentity;
    @JSONField(name="FEntityAuxPty")
    private List<Fentityauxpty> fentityauxpty;
    @JSONField(name="FEntityInvPty")
    private List<Fentityinvpty> fentityinvpty;

}