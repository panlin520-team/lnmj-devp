package com.lnmj.system.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBackupRestoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IBackupRestoreService;
import com.lnmj.system.business.IComplaintService;
import com.lnmj.system.entity.BackupRestore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: panlin
 * @Date: 2019/8/19 11:34
 * @Description: 投诉管理
 */
@Api(description = "投诉")
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Resource
    private IComplaintService complaintService;

    /**
    *@Description 数据库
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 16:56
    */
    @ApiOperation(value = "查询投诉列表",notes = "查询投诉列表")
    @RequestMapping(value = "/complaintList",method = RequestMethod.POST)
    public ResponseResult complaintList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String phoneNumber,Long storeId){
        return complaintService.complaintList(pageNum,pageSize,phoneNumber,storeId);
    }

    /**
    *@Description 删除投诉
    *@Param [id]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 14:29
    */
    @ApiOperation(value = "删除投诉",notes = "删除投诉")
    @RequestMapping(value = "/complaintDel",method = RequestMethod.POST)
    public ResponseResult complaintDel(Long id){
        return complaintService.complaintDel(id);
    }

    /**
    *@Description 添加处理方案
    *@Param [id, handleMethod]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 14:58
    */
    @ApiOperation(value = "添加处理方案",notes = "添加处理方案")
    @RequestMapping(value = "/addComplaintHandleMethod",method = RequestMethod.POST)
    public ResponseResult addComplaintHandleMethod(Long id,String handleMethod,String createOperator){
        return complaintService.addComplaintHandleMethod(id,handleMethod,createOperator);
    }

    /**
     *@Description 查看投诉是否有处理方案
     *@Param [id, handleMethod]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/8/19
     *@Time 14:58
     */
    @ApiOperation(value = "查看投诉处理方案",notes = "查看投诉处理方案")
    @RequestMapping(value = "/checkComplaintHandle",method = RequestMethod.POST)
    public ResponseResult checkComplaintHandle(Long id){
        return complaintService.checkComplaintHandle(id);
    }

    /**
    *@Description 添加方案处理实施
    *@Param [handleId, phoneNumber, handle, complaintor, createOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 16:52
    */
    @ApiOperation(value = "添加方案处理实施",notes = "添加方案处理实施")
    @RequestMapping(value = "/addComplaintTrack",method = RequestMethod.POST)
    public ResponseResult addComplaintTrack(Long handleId,String phoneNumber,String handle,String complaintor,String createOperator){
        return complaintService.addComplaintTrack(handleId,phoneNumber,handle,complaintor,createOperator);
    }

    /**
    *@Description 查看方案处理实施列表
    *@Param [handleId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 16:59
    */
    @ApiOperation(value = "查看方案处理实施列表",notes = "查看方案处理实施列表")
    @RequestMapping(value = "/complaintTrackList",method = RequestMethod.POST)
    public ResponseResult complaintTrackList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long handleId){
        return complaintService.complaintTrackList(pageNum,pageSize,handleId);
    }
    /**
    *@Description 查看方案处理实施记录列表
    *@Param [pageNum, pageSize, keyWordUserName, keyWordStoreId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 18:05
    */
    @ApiOperation(value = "查看方案处理实施记录列表",notes = "查看方案处理实施记录列表")
    @RequestMapping(value = "/complaintRecordList",method = RequestMethod.POST)
    public ResponseResult complaintRecordList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordUserName,Long keyWordStoreId){
        return complaintService.complaintRecordList(pageNum,pageSize,keyWordUserName,keyWordStoreId);
    }

    /**
    *@Description 添加实施结果
    *@Param [handleId, result, modifyOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/19
    *@Time 18:47
    */
    @ApiOperation(value = "添加实施结果",notes = "添加实施结果")
    @RequestMapping(value = "/addComplaintRecordResult",method = RequestMethod.POST)
    public ResponseResult addComplaintRecordResult(Long  handleId,String result,String modifyOperator){
        return complaintService.addComplaintRecordResult(handleId,result,modifyOperator);
    }
}
