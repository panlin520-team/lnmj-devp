package com.lnmj.system.repository;

import com.lnmj.system.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/8/19 15:38
 * @Description: 投诉管理Dao接口
 */
public interface IComplaintDao {
    //投诉
    List<Complaint> complaintList(Map map);
    int complaintDel(Long id);
    Complaint getComplaintById(Long id);
    //投诉处理
    int addComplaintHandleMethod(Complainthandle complainthandle);
    Complainthandle getComplaintHandleMethodById(Map map);
    //投诉追踪
    int addComplaintHandleTrack(Complainttrack complainttrack);
    List<Complainttrack> complaintTrackList(Long handleId);
    //投诉记录
    int addComplaintHandleRecord(Complaintrecord complaintrecord);
    List<Complaintrecord> complaintRecordList(Map map);
    int updateComplaintRecordResult(Complaintrecord complaintrecord);

}
