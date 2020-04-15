package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class Group extends BaseEntity {

  private Integer groupId;
  private Long groupLeaderId;
  private String name;
  private Long storeId;

  @Transient
  private String groupLeaderName;

  @Transient
  private String storeName;



}
