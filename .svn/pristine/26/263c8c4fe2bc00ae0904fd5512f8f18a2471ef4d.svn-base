package com.lnmj.wallet.serviceProxy;

import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */
@FeignClient(value = "lnmj-account")
public interface AccountApi {
    @RequestMapping(value = "/api/manage/member/selectMemberByNum", method = RequestMethod.POST)
    ResponseResult selectMemberByNum(@RequestParam("memberNum") String memberNum);
    @RequestMapping(value = "/api/manage/mall/account/selectAccountByCondition", method = RequestMethod.POST)
    JSONObject selectAccountByCondition(@RequestParam("mobile") String mobile);

    @RequestMapping(value = "/manage/member/memberUserUpdate", method = RequestMethod.POST)
    ResponseResult memberUserUpdate(@RequestParam("memberNumber")String memberNumber);
}

