package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Option;
import com.lnmj.store.entity.OptionValue;
import com.lnmj.store.entity.VO.AppointmentVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 15:31
 * @Description: 预约service接口
 */
@Service("iAppointmentService")
public interface IAppointmentService {
    ResponseResult checkOptionValue(Long userId, Long optionId);

    ResponseResult selectOptionValueList(int pageNum, int pageSize);

    ResponseResult insertOptionValue(OptionValue optionValue);

    ResponseResult updateOptionValue(OptionValue optionValue);

    ResponseResult deleteOptionValue(Long userOptionId, String modifyOperator);

    ResponseResult selectOptionList(int pageNum, int pageSize);

    ResponseResult insertOption(Option option);

    ResponseResult updateOption(Option option);

    ResponseResult deleteOption(Long optionId, String modifyOperator);

    ResponseResult selectAppointmentList(int pageNum, int pageSize);

    ResponseResult selectAppointmentByWhere(AppointmentVO appointment, int pageNum, int pageSize);

    ResponseResult insertAppointment(AppointmentVO appointment);

    ResponseResult updateAppointment(AppointmentVO appointment);

    ResponseResult selectAppointmentByDate(String nursingTime, int pageNum, int pageSize);

    ResponseResult selectAppointmentByBeautician(String employeeCode, int pageNum, int pageSize);

    ResponseResult selectAppointmentByBeauticianAndDate(String employeeCode, String nursingTime, int pageNum, int pageSize);

    ResponseResult deleteAppointment(Long appointmentId, String modifyOperator);

    ResponseResult pushAppointment(Long appointmentId, Integer appointmentStatus, String modifyOperator);

    ResponseResult cancelAppointment(Long appointmentId, String modifyOperator);

    ResponseResult selectAppointmentTimeByDateAndEmployeeAndStatus(String employeeCode, String nursingDate, Integer appointmentStatus);
}
