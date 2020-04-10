package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class Store extends BaseEntity {

    private Long storeId;
    private String code;
    @NotBlank(message = "店铺名称不能为空")
    private String name;
    private Long subCompanyId;
    private String erpStock;
    private Integer isThisStock;
    private Integer isEnableAppointment;
    private Integer distributionType;
    private Long shopOpenId;
    private Long industryID;
    private Long generalManagerOpenId;
    private Long assistantManagerOpenId;
    private Long shopManagerOpenId;
    private Long spareShopManagerOneOpenId;
    private Long spareShopManagerTowOpenId;
    private Long firstGovernorOpenId;
    private Long secondGovernorOpenId;
    private Long marketServiceOpenId;
    // @NotBlank(message = "店铺所在省不能为空")
    private String provinceId;
    //@NotBlank(message = "店铺所在市不能为空")
    private String cityId;
    //@NotBlank(message = "店铺所在区不能为空")
    private String countyId;
    @NotBlank(message = "店铺所在详细地址不能为空")
    private String shopDetailedAddress;
    private String detailedAddress;
    private Double baiduLongitude;
    private Double baiduLatitude;
    private Double gaodeLongitude;
    private Double gaodeLatitude;
    private Double tencentLongitude;
    private Double tencentLatitude;
    private Long environmentGrade;
    @Max(value = 3, message = "护理类型超出了上限选择")
    @Min(value = 1, message = "护理类型低于了下限选择")
    private Integer nursingType;
    private String storeImage;
    private String courseCardConsumptionAuthCode;
    @NotBlank(message = "店铺营业时间不能为空")
    private String shopBusinessTime;
    @NotBlank(message = "店铺联系电话不能为空")
    private String phoneNumber;
    private Long StoreCategoryId;
    private String k3Id;
    private String k3Number;
    private String systemK3Number;
    private String heSuanFanWeiK3Number;
    private String zhangBuK3Number;
    private String dataCentreUserName;

    private String dataCentrePassword;

    private String bankNumber;

    private String cashNumber;
}
