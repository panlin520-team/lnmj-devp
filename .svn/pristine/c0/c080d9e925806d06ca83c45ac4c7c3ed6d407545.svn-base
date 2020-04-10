package com.lnmj.common.BaseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.common.BaseEntity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/10/9 17:32
 * @Description:
 */
@Data
public class Message extends BaseEntity {
        private Long messageId;
        private Integer messageType;
        private String mobile;
        private String templateId;
        private String datas;
        private String template;
        private String message;
        private String statusCode;
        private String templateSMS;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date dateCreated;
        private String smsMessageSId;
        private String statusMsg;
        private String remark;

}
