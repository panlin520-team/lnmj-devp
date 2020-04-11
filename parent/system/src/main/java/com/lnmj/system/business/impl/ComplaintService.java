package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.ImageTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBackupRestoreEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeComplaintEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.FtpFileUtil;
import com.lnmj.system.business.IBackupRestoreService;
import com.lnmj.system.business.IComplaintService;
import com.lnmj.system.entity.*;
import com.lnmj.system.repository.IBackupRestoreDao;
import com.lnmj.system.repository.IComplaintDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/8/19 15:29
 * @Description: 投诉管理Service
 */
@Service("complaintService")
public class ComplaintService implements IComplaintService {
    @Resource
    private IComplaintDao complaintDao;


    @Override
    public ResponseResult complaintList(int pageNum, int pageSize, String phoneNumber, Long storeId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("phoneNumber", phoneNumber);
        map.put("storeId", storeId);
        List<Complaint> complaints = complaintDao.complaintList(map);
        PageInfo<Complaint> pageInfo = new PageInfo(complaints);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult complaintDel(Long id) {
        complaintDao.complaintDel(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addComplaintHandleMethod(Long id, String handleMethod,String createOperator) {
        //查看是否已经有处理方案
        Map map = new HashMap();
        map.put("id",id);
        Complainthandle complaintHandleResult = complaintDao.getComplaintHandleMethodById(map);
        if (complaintHandleResult != null) {
            return ResponseResult.error(new Error(ResponseCodeComplaintEnum.COMPLAINTHANDLE_EXIST.getCode(), ResponseCodeComplaintEnum.COMPLAINTHANDLE_EXIST.getDesc()));
        }
        //查询投诉内容
        Complaint complaint = complaintDao.getComplaintById(id);
        Complainthandle complainthandle = new Complainthandle();
        complainthandle.setUserName(complaint.getUserName());
        complainthandle.setConsumptionStore(complaint.getConsumptionStore());
        complainthandle.setResponsibility(complaint.getResponsibility());
        complainthandle.setComplaintTitle(complaint.getComplaintTitle());
        complainthandle.setComplaintReason(complaint.getComplaintReason());
        complainthandle.setHandleMethod(handleMethod);
        complainthandle.setComplaintHandleId(id);
        complainthandle.setCreateOperator(createOperator);
        complaintDao.addComplaintHandleMethod(complainthandle);
        Long idResult = complainthandle.getId();
        //添加处理记录
        Complaintrecord complaintrecord = new Complaintrecord();
        complaintrecord.setHandleId(idResult);
        complaintrecord.setUserName(complaint.getUserName());
        complaintrecord.setConsumptionStore(complaint.getConsumptionStore());
        complaintrecord.setConsumptionProject(complaint.getConsumptionProject());
        complaintrecord.setResponsibility(complaint.getResponsibility());
        complaintrecord.setComplaintTitle(complaint.getComplaintTitle());
        complaintrecord.setComplaintTime(complaint.getConsumptionTime());
        complaintrecord.setComplaintReason(complaint.getComplaintReason());
        complaintrecord.setHandleMethod(handleMethod);
        complaintDao.addComplaintHandleRecord(complaintrecord);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult checkComplaintHandle(Long id) {
        //查看是否已经有处理方案
        Map map = new HashMap();
        map.put("complaintHandleId",id);
        Complainthandle complaintHandleResult = complaintDao.getComplaintHandleMethodById(map);
        return ResponseResult.success(complaintHandleResult);
    }

    @Override
    public ResponseResult addComplaintTrack(Long handleId, String phoneNumber,String handle,String complaintor,String createOperator) {
        //查看处理方案信息
        Map map = new HashMap();
        map.put("id",handleId);
        Complainthandle complainthandle = complaintDao.getComplaintHandleMethodById(map);
        Complainttrack complainttrack = new Complainttrack();
        complainttrack.setHandle(handle);
        complainttrack.setHandleId(handleId);
        complainttrack.setUserName(complainthandle.getUserName());
        complainttrack.setPhoneNumber(phoneNumber);
        complainttrack.setConsumptionStore(complainthandle.getConsumptionStore());
        complainttrack.setComplaintor(complaintor);
        complainttrack.setCreateOperator(createOperator);
        complaintDao.addComplaintHandleTrack(complainttrack);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult complaintTrackList(int pageNum,int pageSize,Long handleId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Complainttrack> complainttrackList = complaintDao.complaintTrackList(handleId);
        PageInfo<Complainttrack> pageInfo = new PageInfo(complainttrackList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult complaintRecordList(int pageNum, int pageSize, String keyWordUserName, Long keyWordStoreId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordUserName",keyWordUserName);
        map.put("keyWordStoreId",keyWordStoreId);
        List<Complaintrecord> complainttrackList = complaintDao.complaintRecordList(map);
        PageInfo<Complainttrack> pageInfo = new PageInfo(complainttrackList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult addComplaintRecordResult(Long handleId, String result, String modifyOperator) {
        Complaintrecord complaintrecord = new Complaintrecord();
        complaintrecord.setHandleId(handleId);
        complaintrecord.setResult(result);
        complaintrecord.setModifyOperator(modifyOperator);
        complaintDao.updateComplaintRecordResult(complaintrecord);

        return ResponseResult.success();
    }
}
