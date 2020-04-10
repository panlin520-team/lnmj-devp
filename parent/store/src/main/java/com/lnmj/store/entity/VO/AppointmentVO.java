package com.lnmj.store.entity.VO;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/21 16:57
 * @Description: 预约表
 */
@Data
public class AppointmentVO extends BaseEntity {
    private Long appointmentId;//Id
    private String appointmentType;//预约类型（美容/美发）
    private Long consumerId;//顾客
    private String consumer;//顾客名
    private String consumerPhone;//顾客电话
    private String product;//项目（服务商品）[1,2,3]
    private BigDecimal performance;//业绩
    private BigDecimal selling;//销售
    private String source;//来源
    private Long storeId;//护理门店
    private String storeName;//护理门店
    private String employeeCode;//美容师
    private String nursingDate;//护理时间
    private String duration;//护理时长
    private Integer storeNumber;//到店人数
    private Long orderNumber;//订单号
    private Integer appointmentStatus;//预约状态(0 已预约  1 已开单  2 已占用 3 已调休  4 已过期）
    private Boolean pushAppoinmentStatus;
    private Boolean pushPayStatus;
    private String remark;//备注
}
