package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.business.IComplaintService;
import com.lnmj.system.entity.*;
import com.lnmj.system.repository.IBackupRestoreDao;
import com.lnmj.system.repository.IComplaintDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: panlin
 * @Date: 2019/8/19 15:39
 * @Description: 投诉管理Dao
 */
@Repository
public class ComplaintDaoImpl extends BaseDao implements IComplaintDao {

    @Override
    public List<Complaint> complaintList(Map map) {
        return super.selectList("complaint.complaintList",map);
    }

    @Override
    public int complaintDel(Long id) {
        return super.delete("complaint.complaintDel",id);
    }

    @Override
    public Complaint getComplaintById(Long id) {
        return select("complaint.getComplaintById",id);
    }

    @Override
    public int addComplaintHandleMethod(Complainthandle complainthandle) {
        return super.insert("complaint.addComplaintHandleMethod",complainthandle);
    }

    @Override
    public Complainthandle getComplaintHandleMethodById(Map map) {
        return super.select("complaint.getComplaintHandleMethodById",map);
    }

    @Override
    public int addComplaintHandleTrack(Complainttrack complainttrack) {
        return super.insert("complaint.addComplaintHandleTrack",complainttrack);
    }

    @Override
    public List<Complainttrack> complaintTrackList(Long handleId) {
        return super.selectList("complaint.complaintTrackList",handleId);
    }

    @Override
    public int addComplaintHandleRecord(Complaintrecord complaintrecord) {
        return super.insert("complaint.addComplaintHandleRecord",complaintrecord);
    }

    @Override
    public List<Complaintrecord> complaintRecordList(Map map) {
        return super.selectList("complaint.complaintRecordList",map);
    }

    @Override
    public int updateComplaintRecordResult(Complaintrecord complaintrecord) {
        return super.update("complaint.updateComplaintRecordResult",complaintrecord);
    }
}
