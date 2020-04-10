package com.lnmj.system.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBackupRestoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IBackupRestoreService;
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
 * @Author: yilihua
 * @Date: 2019/6/4 11:34
 * @Description: 备份还原controller
 */
@Api(description = "备份还原")
@RestController
@RequestMapping("/manage")
public class BackupRestoreController {
    @Resource
    private IBackupRestoreService backupRestoreService;

    /**
    *@Description 数据库
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/17
    *@Time 16:56
    */
    @ApiOperation(value = "数据库",notes = "数据库")
    @RequestMapping(value = "/showSchema",method = RequestMethod.POST)
    public ResponseResult showSchema(String access_token){
        return backupRestoreService.showSchema();
    }

    /**
    *@Description 表
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/17
    *@Time 16:56
    */
    @ApiOperation(value = "表",notes = "表")
    @RequestMapping(value = "/showTable",method = RequestMethod.POST)
    public ResponseResult showTable(String schema,String access_token){
        return backupRestoreService.showTable(schema);
    }

    /**
    *@Description 查看备份还原记录
    *@Param [backupRestore]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/6
    *@Time 14:48
    */
    @ApiOperation(value = "查看备份还原记录",notes = "查看备份还原记录")
    @RequestMapping(value = "/selectBackupRestoreList",method = RequestMethod.POST)
    public ResponseResult selectBackupRestoreList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String access_token,BackupRestore backupRestore){
        return backupRestoreService.selectBackupRestoreList(pageNum,pageSize,backupRestore);
    }

    /**
    *@Description 备份
    *@Param []
    *@Return void
    *@Author yilihua
    *@Date 2019/6/4
    *@Time 11:38
    */
    @ApiOperation(value = "备份",notes = "备份")
    @RequestMapping(value = "/backup",method = RequestMethod.POST)
    public ResponseResult backup(String tableName, String tableSchema,String createOperator) {
        if(StringUtils.isBlank(createOperator)){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.CREATEOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeBackupRestoreEnum.CREATEOPERATOR_NOT_NULL.getDesc()));
        }
        //传入参数就精确备份，没有传入参数就直接全备份
        return backupRestoreService.backup(tableSchema,tableName,createOperator);
    }


    /**
    *@Description 还原
    *@Param [tableSchema, fileUrl, createOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/5
    *@Time 11:36
    */
    @ApiOperation(value = "还原",notes = "还原")
    @RequestMapping(value = "/restore",method = RequestMethod.POST)
    public ResponseResult restore(String tableSchema,String fileUrl,String createOperator) {
        if(StringUtils.isBlank(tableSchema)){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.TABLE_SCHEMA_NOT_NULL.getCode(),
                    ResponseCodeBackupRestoreEnum.TABLE_SCHEMA_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(fileUrl)){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.RESTORE_FILE_NOT_NULL.getCode(),
                    ResponseCodeBackupRestoreEnum.RESTORE_FILE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(createOperator)){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.CREATEOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeBackupRestoreEnum.CREATEOPERATOR_NOT_NULL.getDesc()));
        }
        return backupRestoreService.restore(tableSchema,fileUrl,createOperator);
    }

}
