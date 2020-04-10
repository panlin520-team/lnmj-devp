package com.lnmj.store.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author renqingyun
 * @Date: 2019/5/22 14:32
 * @Description:消费项目定制
 */

@Data
public class ConsumeCustomMadeVO extends BaseEntity{
  private Long consumeCustomMadeId;
  private Long customMadeOrderDetailId;//定制项目id
  private String nurseStore;//护理门店
  private String beautician;//美容师
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date nurseTime;
  private String specifications;//规格
  @JsonSerialize(using = ToStringSerializer.class)
  private Long orderNumber;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date orderTime;
  private String mobile;
  private String orderLink;

}
