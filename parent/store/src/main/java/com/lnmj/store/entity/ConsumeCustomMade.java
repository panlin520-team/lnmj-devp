package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author renqingyun
 * @Date: 2019/5/22 14:32
 * @Description:消费项目定制
 */

@Data
public class ConsumeCustomMade extends BaseEntity {

  private Long consumeCustomMadeId;

  @NotNull(message = "定制项目id不能为空")
  private Long customMadeOrderDetailId;

  @JsonSerialize(using = ToStringSerializer.class)
  @NotNull(message = "订单号不能为空")
  private Long orderNumber;
  @NotNull(message = "护理门店id不能为空")
  private Integer storeId;
  @NotNull(message = "护理门店名称不能为空")
  private String nurseStore;
  private String bookingBeauticianIds;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @NotNull(message = "护理时间不能为空")
  private Date nurseTime;
  //规格
  private String specifications;
  @NotNull(message = "护理状态不能为空")
  private Integer nurseStatus;


}
