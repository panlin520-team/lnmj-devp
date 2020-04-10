package com.lnmj.order.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description: 
 */
@FeignClient(name = "lnmj-account")
public interface MemberUserApi {
    @RequestMapping(value = "/api/manage/member/updateStoreMember", method = RequestMethod.POST)
    ResponseResult updateStoreMember(@RequestParam("memberNum") String memberNum,@RequestParam("lastArriveDate")String lastArriveDate);

    @RequestMapping(value = "/api/manage/member/listMemberAccountNoPage", method = RequestMethod.POST)
    ResponseResult selectStoreMemberAccountNoPage(@RequestParam("memberNum")String memberNum,@RequestParam("subClassIds")String subClassIds);


    @RequestMapping(value = "/manage/member/memberUserUpdate", method = RequestMethod.POST)
    ResponseResult memberUserUpdate(@RequestParam("memberNumber")String memberNumber);
}

