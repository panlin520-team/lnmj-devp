/**
 * Copyright (C), 2015-2019, 俪凝美聚
 * FileName: BaseEntity
 * Author:   Administrator
 * Date:     2019/4/17 15:34
 * Description: 基本的pojo类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lnmj.account.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 9:57
 * @Description: 基本的entity类
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;//transient 关键字表示不用序列化
    private Integer status;                //状态
    private String createOperator;      //创建人
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;            //创建时间
    private String modifyOperator;      //修改人
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataChangeLastTime;    //数据最后更新时间
    /*springboot 格式化返回日期:
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")或全局配置：
        spring.jackson.date-format=yyyy-MM-dd
        spring.jackson.time-zone=GMT+8
        spring.jackson.serialization.write-dates-as-timestamps=false
        GMT+8 北京时间，CTT 上海香港时间
     */

    /*
    排除非表字段
    使用 transient 修饰
    private transient String noColumn;

    使用 static 修饰
    private static String noColumn;

    使用 TableField 注解
    @TableField(exist=false)
    private String noColumn;
     */
    //时间在数据库存储为datatime或者timestamp，需要转换

}
