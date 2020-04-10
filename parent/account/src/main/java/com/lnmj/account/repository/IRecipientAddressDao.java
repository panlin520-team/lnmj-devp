package com.lnmj.account.repository;

import com.lnmj.account.entity.RecipientAddress;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 14:38
 * @Description:
 */
public interface IRecipientAddressDao {
    int insertAddress(RecipientAddress recipientAddress);

    int checkDefault(String memberNum);

    int deleteByAddressIdUserId(RecipientAddress recipientAddress);

    int selectIsDefaultById(Long recipientAddressId);

    int updateByAddress(RecipientAddress recipientAddress);

    RecipientAddress selectByAddressIdUserId(RecipientAddress recipientAddress);

    List<RecipientAddress> selectByMemberNum(String memberNum);

    RecipientAddress selectByDefalut(String memberNum);
}
