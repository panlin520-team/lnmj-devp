package com.lnmj.store.serviceProxy.controller;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IBeauticianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: panlin
 * @Date: 2019/5/28 11:32
 * @Description:
 */
@Api(description = "美容师")
@RestController
@RequestMapping("/api/manage/beautician")
public class BeauticianManageService {
    @Resource(name = "beauticianService")
    private IBeauticianService beauticianService;


    @ApiOperation(value = "查看员工职位分类",notes = "查看美容师职位")
    @RequestMapping(value = "/selectPostCategoryNoPage",method = RequestMethod.POST)
    public ResponseResult selectPostCategoryNoPage(String postIndustryIDSearch) {
        return beauticianService.selectPostCategoryNoPage(postIndustryIDSearch);
    }

    @ApiOperation(value = "查看美容师列表",notes = "查看美容师列表")
    @RequestMapping(value = "/selectBeauticianListNoPage",method = RequestMethod.POST)
    public ResponseResult selectBeauticianListNoPage(String companyType,String companyId,Integer isSaleMan) {
        return beauticianService.selectBeauticianListNoPage(companyType, companyId,isSaleMan);
    }

    @ApiOperation(value = "根据职位查看职位信息",notes = "根据职位查看职位信息")
    @RequestMapping(value = "/selectPostById",method = RequestMethod.POST)
    public ResponseResult selectPostById(Long postId) {
        return beauticianService.selectPostById(postId);
    }


    @ApiOperation(value = "根据门店Id数组查询员工",notes = "根据门店Id数组查询员工")
    @RequestMapping(value = "/selectBeauticianByStoreIdArray",method = RequestMethod.POST)
    public ResponseResult selectBeauticianByStoreIdArray(String storeId) {
        return beauticianService.selectBeauticianByStoreIdArray(storeId);
    }
}
