package com.lnmj.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/6/4 12:03
 * @Description: 备份还原记录表
 */
@Data
public class BackupRestore extends BaseEntity {
    private Long backupRestoreId;   //id
    private String fileUrl;     // 文件名（文件路径）
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;     // 操作开始时间（备份时间/还原时间)
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;       // 操作结束时间（备份时间/还原时间）
    private String fileSize;    // 文件大小
    private String content;      // 内容（备份数据库.表/全备份/数据库内容，还原内容）
    private String operation;    // 操作（还原/备份/定时备份）
}
