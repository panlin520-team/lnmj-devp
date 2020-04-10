package com.lnmj.store.repository;

import com.lnmj.store.entity.Appointment;
import com.lnmj.store.entity.Option;
import com.lnmj.store.entity.OptionValue;
import com.lnmj.store.entity.VO.AppointmentTimeVO;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 15:35
 * @Description: 预约dao接口
 */
public interface IAppointmentDao {
    List<Long> checkOptionValue(OptionValue optionValue);
    List<OptionValue> selectOptionValueList();
    int insertOptionValue(OptionValue optionValue);
    int updateOptionValue(OptionValue optionValue);
    int deleteOptionValue(OptionValue optionValue);
    List<Option> selectOptionList();
    int insertOption(Option option);
    int updateOption(Option option);
    int deleteOption(Option option);
    List<Appointment> selectAppointmentList();
    List<Appointment> selectAppointmentByWhere(Appointment appointment);
    int insertAppointment(Appointment appointment);
    int updateAppointment(Appointment appointment);
    int deleteAppointment(Appointment appointment);

    List<Appointment> selectAppointmentByEmployssNursingTime(HashMap<String ,Object> checkMap);

    List<AppointmentTimeVO> selectAppointmentTimeByDateAndEmpAndSta(HashMap<String, Object> selectMap);
}
