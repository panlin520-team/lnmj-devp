package com.lnmj.store.entity.VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class StoreVO {
  private Long storeId;
  private String name;
  private String code;
  private Long subCompanyId;
  private Integer isEnableAppointment;
  private Long industryID;
  @Transient
  private String industryName;
  private String provinceId;
  private String cityId;
  private String countyId;
  private String shopDetailedAddress;
  private Double baiduLongitude;
  private Double baiduLatitude;
  private String ShopBusinessTime;
  private String phoneNumber;
  private Integer nursingType;
  private String storeImage;
  private String k3Id;
  private String k3Number;
  private String systemK3Number;
  private String heSuanFanWeiK3Number;
  private String zhangBuK3Number;

  private Long storeCategoryId;
  @Transient
  private String storeCategoryName;
  @Transient
  private String subCompanyName;
  @Transient
  private Integer isChecked;
  @Transient
  private Long orgId;
  @Transient
  private String orgK3Number;
  @Transient
  private String orgName;
}
