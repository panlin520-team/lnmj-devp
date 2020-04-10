package com.lnmj.account.business;

import com.github.pagehelper.PageInfo;
import com.lnmj.account.entity.RecipientAddress;
import com.lnmj.common.response.ResponseResult;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 14:25
 * @Description:
 */
public interface IRecipientAddressService {
    ResponseResult add(String memberNum, RecipientAddress recipientAddress);

    ResponseResult delete(String memberNum, Long recipientAddress);

    ResponseResult update(String memberNum, RecipientAddress recipientAddress);



    ResponseResult<PageInfo> listByPage(String  memberNum, int pageNum, int pageSize);

    ResponseResult setDefault(String memberNum,Long recipientAddressId,String modifyOperator);
}
