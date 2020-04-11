package com.lnmj.store.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Orderwork extends BaseEntity {

  private Long orderworkId;
  private Long beauticianId;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date orderworkDate;
  private String LeisureTimeNodes;
  private String busyTimeNodes;
  private String restTimeNodes;





}
