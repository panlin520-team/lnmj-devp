package com.lnmj.common.baseDao;

import com.lnmj.common.BaseEntity.Message;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/9 17:32
 * @Description: 短信接口
 */
public interface IMessageDao {
    int insertMessage(Message message);
    List<Message> selectMessageList(Message message);
}
