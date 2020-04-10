package com.lnmj.common.baseDao.impl;

import com.lnmj.common.BaseEntity.Message;
import com.lnmj.common.baseDao.IMessageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/10 09:47
 * @Description: 短信接口实现
 */
@Repository
public class MessageDao extends BaseDao implements IMessageDao {

    @Override
    public int insertMessage(Message message) {
        return super.insert("message.insertMessage",message);
    }

    @Override
    public List<Message> selectMessageList(Message message) {
        return super.selectList("message.selectMessageList",message);
    }
}
