package com.lnmj.data.serviceProxy;

import com.github.pagehelper.PageInfo;
import com.lnmj.common.response.ResponseResult;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */

@FeignClient(value = "lnmj-account")
public interface AccountApi {
    @RequestMapping(value = "/api/manage/member/countMemberByDay", method = RequestMethod.POST)
    ResponseResult  countMemberByDay(@RequestParam("time") String time,@RequestParam("storeId") String storeId);
}

