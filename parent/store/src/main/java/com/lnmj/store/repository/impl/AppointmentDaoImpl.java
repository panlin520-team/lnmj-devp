package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Appointment;
import com.lnmj.store.entity.Option;
import com.lnmj.store.entity.OptionValue;
import com.lnmj.store.entity.VO.AppointmentTimeVO;
import com.lnmj.store.repository.IAppointmentDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 15:35
 * @Description: 预约dao实现
 */
@Repository
public class AppointmentDaoImpl extends BaseDao implements IAppointmentDao {
    @Override
    public List<Long> checkOptionValue(OptionValue optionValue){
        return super.selectList("appointment.checkOptionValue",optionValue);
    }

    @Override
    public List<OptionValue> selectOptionValueList() {
        return super.selectList("appointment.selectOptionValueList");
    }

    @Override
    public int insertOptionValue(OptionValue optionValue) {
        return super.insert("appointment.insertOptionValue",optionValue);
    }

    @Override
    public int updateOptionValue(OptionValue optionValue) {
        return super.update("appointment.updateOptionValue",optionValue);
    }

    @Override
    public int deleteOptionValue(OptionValue optionValue) {
        return super.update("appointment.updateOptionValue",optionValue);
    }

    @Override
    public List<Option> selectOptionList() {
        return super.selectList("appointment.selectOptionList");
    }

    @Override
    public int insertOption(Option option) {
        return super.insert("appointment.insertOption",option);
    }

    @Override
    public int updateOption(Option option) {
        return super.update("appointment.updateOption",option);
    }

    @Override
    public int deleteOption(Option option) {
        return super.update("appointment.updateOption",option);
    }

    @Override
    public List<Appointment> selectAppointmentList() {
        return super.selectList("appointment.selectAppointmentList");
    }

    @Override
    public List<Appointment> selectAppointmentByWhere(Appointment appointment) {
        return super.selectList("appointment.selectAppointmentByWhere",appointment);
    }

    @Override
    public int insertAppointment(Appointment appointment) {
        return super.insert("appointment.insertAppointment",appointment);
    }

    @Override
    public int updateAppointment(Appointment appointment) {
        return super.update("appointment.updateAppointment",appointment);
    }

    @Override
    public int deleteAppointment(Appointment appointment) {
        return super.update("appointment.deleteAppointment",appointment);
    }

    @Override
    public List<Appointment> selectAppointmentByEmployssNursingTime(HashMap<String ,Object> checkMap) {
        return super.selectList("appointment.selectAppointmentByEmployssNursingTime",checkMap);
    }

    @Override
    public List<AppointmentTimeVO> selectAppointmentTimeByDateAndEmpAndSta(HashMap<String, Object> selectMap) {
        return super.selectList("appointment.selectAppointmentTimeByDateAndEmpAndSta",selectMap);
    }
}
