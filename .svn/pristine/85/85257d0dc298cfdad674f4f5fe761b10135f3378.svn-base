package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.RecipientAddress;
import com.lnmj.account.repository.IRecipientAddressDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 14:38
 * @Description:
 */
@Repository
public class RecipientAddressDaoImpl extends BaseDao implements IRecipientAddressDao {
    @Override
    public int insertAddress(RecipientAddress recipientAddress) {
        int intResult = insert("recipientAddress.insertAddress", recipientAddress);
        return intResult;
    }

    @Override
    public int checkDefault(String memberNum) {
        int intResult = select("recipientAddress.checkDefault", memberNum);
        return intResult;
    }

    @Override
    public int deleteByAddressIdUserId(RecipientAddress recipientAddress) {
        int intResult = update("recipientAddress.deleteByAddressIdUserId", recipientAddress);
        return intResult;
    }

    @Override
    public int selectIsDefaultById(Long recipientAddressId) {
        int intResult = select("recipientAddress.selectIsDefaultById", recipientAddressId);
        return intResult;
    }

    @Override
    public int updateByAddress(RecipientAddress recipientAddress) {
        int intResult = update("recipientAddress.updateByAddress", recipientAddress);
        return intResult;
    }

    @Override
    public RecipientAddress selectByAddressIdUserId(RecipientAddress recipientAddress) {
        RecipientAddress recipientAddress1 = select("recipientAddress.selectByAddressIdUserId", recipientAddress);
        return recipientAddress1;
    }

    @Override
    public List<RecipientAddress> selectByMemberNum(String memberNum) {
        List<RecipientAddress> result = selectList("recipientAddress.selectByMemberNum", memberNum);
        return result;
    }

    @Override
    public RecipientAddress selectByDefalut(String memberNum) {
        return super.select("recipientAddress.selectByDefalut",memberNum);
    }
}
