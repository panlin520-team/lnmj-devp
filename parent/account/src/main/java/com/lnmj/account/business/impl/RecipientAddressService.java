package com.lnmj.account.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.lnmj.account.business.IRecipientAddressService;
import com.lnmj.account.entity.RecipientAddress;
import com.lnmj.account.repository.IRecipientAddressDao;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 14:24
 * @Description:
 */
@Transactional
@Service("recipientAddressService")
public class RecipientAddressService implements IRecipientAddressService {
    @Resource
    private IRecipientAddressDao recipientAddressDao;

    @Override
    public ResponseResult add(String memberNum, RecipientAddress recipientAddress) {
        //非空判断
        if (StringUtils.isBlank(recipientAddress.getName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_NAME_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(recipientAddress.getMobile())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_MOBILE_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_MOBILE_NULL.getDesc()));
        }
        if (recipientAddress.getProvinceId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_PROVINCEID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_PROVINCEID_NULL.getDesc()));
        }
        if (recipientAddress.getCityId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_CITYID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_CITYID_NULL.getDesc()));
        }
        if (recipientAddress.getCountyId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_COUNTYID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_COUNTYID_NULL.getDesc()));
        }
        if (recipientAddress.getAddressDetail() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESSDETAIL_COUNTYID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESSDETAIL_COUNTYID_NULL.getDesc()));
        }
        //判断是否此用户是否已经有默认地址
        int result = recipientAddressDao.checkDefault(memberNum);
        if (result == 0) {
            recipientAddress.setIsDefault(1);
        }
        recipientAddress.setMemberNum(memberNum);
        int rowCount = recipientAddressDao.insertAddress(recipientAddress);
        if (rowCount > 0) {
            Map map = Maps.newHashMap();
            map.put("recipientAddressId", recipientAddress.getRecipientAddressId());
            return ResponseResult.success(map);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ADD_FAILUER.getCode(), ResponseCodeAccountEnum.ADD_FAILUER.getDesc()));

    }

    @Override
    public ResponseResult delete(String memberNum, Long recipientAddressId) {
        //非空判断
        if (recipientAddressId == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getDesc()));
        }
        //判断如果此条地址是否为默认
        int resultInt = recipientAddressDao.selectIsDefaultById(recipientAddressId);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_RECIPIENT_ADDRESS_FAILURE.getCode(), ResponseCodeAccountEnum.DELETE_RECIPIENT_ADDRESS_FAILURE.getDesc()));
        }
        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setMemberNum(memberNum);
        recipientAddress.setRecipientAddressId(recipientAddressId);
        int row = recipientAddressDao.deleteByAddressIdUserId(recipientAddress);
        if (row > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_FAILUER.getDesc()));

    }

    @Override
    public ResponseResult update(String memberNum, RecipientAddress recipientAddress) {
        //非空判断
        if (recipientAddress.getRecipientAddressId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getDesc()));
        }
        recipientAddress.setMemberNum(memberNum);
        int row = recipientAddressDao.updateByAddress(recipientAddress);
        if (row > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_FAILUER.getCode(), ResponseCodeAccountEnum.UPDATE_FAILUER.getDesc()));

    }


    @Override
    public ResponseResult<PageInfo> listByPage(String memberNum, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RecipientAddress> addressesList = recipientAddressDao.selectByMemberNum(memberNum);
        if (!addressesList.isEmpty()) {
            PageInfo pageInfo = new PageInfo(addressesList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.GET_ADDRESS_LIST_FAILUER.getCode(), ResponseCodeAccountEnum.GET_ADDRESS_LIST_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult setDefault(String memberNum,Long recipientAddressId, String modifyOperator) {
        if (recipientAddressId==null){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getCode(), ResponseCodeAccountEnum.RECIPIENT_ADDRESS_ID_NULL.getDesc()));
        }
        //将原有的默认地址修改未非默认
        RecipientAddress recipientAddressDefault = recipientAddressDao.selectByDefalut(memberNum);
        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setIsDefault(0);
        recipientAddress.setRecipientAddressId(recipientAddressDefault.getRecipientAddressId());
        recipientAddress.setModifyOperator(modifyOperator);
        recipientAddress.setMemberNum(memberNum);
        recipientAddressDao.updateByAddress(recipientAddress);
        //修改当前地址未默认
        recipientAddress.setIsDefault(1);
        recipientAddress.setRecipientAddressId(recipientAddressId);
        recipientAddress.setModifyOperator(modifyOperator);
        recipientAddressDao.updateByAddress(recipientAddress);
        return ResponseResult.success();
    }

}
