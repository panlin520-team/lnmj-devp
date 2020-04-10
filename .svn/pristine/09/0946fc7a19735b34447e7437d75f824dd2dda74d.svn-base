package com.lnmj.store.serviceProxy.controller;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IStoreService;
import com.lnmj.store.entity.Store;
import com.lnmj.store.entity.StoreCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lnmj.common.utils.ProductCheckUtil.ProductCheck;

/**
 * @Author panlin
 * @Date: 2019/5/28 11:49
 * @Description: 店铺管理
 */
@Api(description = "店铺管理")
@RestController
@RequestMapping("/api/manage/store")
public class StoreManagerService {
    @Resource(name = "storeService")
    private IStoreService storeService;

    @ApiOperation(value = "门店列表不分页",notes = "门店列表不分页")
    @RequestMapping(value = "/selectStoreListNoPage",method = RequestMethod.POST)
    public ResponseResult selectStoreListNoPage(String companyId,String subCompanyId,String storeId,String productCode,String experienceCardNum,Long companyType) {
        return storeService.selectStoreListNoPage(companyId,subCompanyId,storeId,productCode,experienceCardNum,companyType);
    }

    @ApiOperation(value = "根据总公司id查看所能管理的门店",notes = "根据ID查询公司")
    @RequestMapping(value = "/selectStoreListByCompanyId",method = RequestMethod.POST)
    public ResponseResult selectStoreListByCompanyId(Long companyId){
        if(companyId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_ID_NULL.getDesc()));
        }
        return storeService.selectStoreListByCompanyId(companyId);
    }

    @ApiOperation(value = "根据子公司查看门店不分页",notes = "根据子公司查看门店不分页")
    @RequestMapping(value = "/selectStoreListBySubCompanyNoPage",method = RequestMethod.POST)
    public ResponseResult selectStoreListBySubCompanyNoPage(Long subsidiaryId) {
        return storeService.selectStoreListBySubCompanyNoPage(subsidiaryId);
    }
}
