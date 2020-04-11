package com.lnmj.data.entity;


import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author renqingyun
 * @Date: 2019/6/10 9:40
 * @Description: 业绩单
 */

@Data
public class Achievement extends BaseEntity {

  private Long achievementId;
  private String store;
  private String customer;
  private String project;
  private Long achievement;
  private String sale;
  private String source;
  private String beautician;
  private Date orderTime;

}
