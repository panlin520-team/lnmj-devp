package com.lnmj.system.entity.VO;

import lombok.Data;

import java.util.Date;

@Data
public class SystemVO {
  private Long storeId;
  private String name;
  private Double shopProvince;
  private String shopCity;
  private String shopCounty;
  private String shopDetailedAddress;
  private Double baiduLongitude;
  private Double baiduLatitude;
  private Date ShopBusinessTime;
  private String phoneNumber;
  private Long nursingType;
  private String storeImage;






}
