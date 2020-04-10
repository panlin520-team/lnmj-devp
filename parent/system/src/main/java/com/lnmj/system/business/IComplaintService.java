package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.BackupRestore;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/6/11 11:04
 * @Description: 备份还原Service接口
 */
@Service("iComplaintService")
public interface IComplaintService {
    ResponseResult complaintList(int pageNum, int pageSize,String phoneNumber,Long storeId) ;
    ResponseResult complaintDel(Long id) ;
    ResponseResult addComplaintHandleMethod(Long id,String handleMethod,String createOperator) ;
    ResponseResult checkComplaintHandle(Long id) ;
    ResponseResult addComplaintTrack(Long handleId,String phoneNumber,String handle,String complaintor,String createOperator) ;
    ResponseResult complaintTrackList(int pageNum,int pageSize,Long handleId) ;
    ResponseResult complaintRecordList(int pageNum,int pageSize,String keyWordUserName,Long keyWordStoreId) ;
    ResponseResult addComplaintRecordResult(Long  handleId,String result,String modifyOperator) ;

}
