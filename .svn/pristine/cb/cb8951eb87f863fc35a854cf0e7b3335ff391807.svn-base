package com.lnmj.store.serviceProxy.controller;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IIndustryService;
import com.lnmj.store.entity.Industry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 〈行业〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */
@Api(description = "行业")
@RestController
@RequestMapping("/api/manage/industry")
public class IndustryManageService {
    @Autowired
    private IIndustryService industryService;


    @ApiOperation(value = "查询所有行业",notes = "查询所有行业")
    @RequestMapping(value = "/selectListIndustryNoPage",method = RequestMethod.POST)
    public ResponseResult selectListIndustryNoPage() {
        return industryService.selectListIndustryNoPage();
    }



}
