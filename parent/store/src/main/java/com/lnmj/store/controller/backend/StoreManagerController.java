package com.lnmj.store.controller.backend;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IStoreService;
import com.lnmj.store.entity.Store;
import com.lnmj.store.entity.StoreCategory;
import com.lnmj.store.entity.StoreMemoNum;
import com.lnmj.store.entity.Subsidiary;
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
@RequestMapping("/manage/store")
public class StoreManagerController {
    @Resource(name = "storeService")
    private IStoreService storeService;


    /**
    *@Description 门店列表分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.product.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/5/8
    *@Time
    */
    @ApiOperation(value = "门店列表分页显示",notes = "门店列表分页显示")
    @RequestMapping(value = "/selectStoreList",method = RequestMethod.POST)
    public ResponseResult selectStoreList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token,
                                            String keyWordPhone,String keyWordName,Long storeCategoryId,Long companyType,Long companyId,Long industryID) {
        return storeService.selectStoretList(pageNum,pageSize,keyWordPhone,keyWordName,storeCategoryId,companyType,companyId,industryID);
    }
    /**
    *@Description
    *@Param [pageNum, pageSize, access_token, keyWordPhone, keyWordName, storeCategoryId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/9/27
    *@Time 18:11
    */
    @ApiOperation(value = "门店列表不分页",notes = "门店列表不分页")
    @RequestMapping(value = "/selectStoreListNoPage",method = RequestMethod.POST)
    public ResponseResult selectStoreListNoPage(String companyId,String subCompanyId,String storeId,String productCode,String experienceCardNum,Long companyType) {
        return storeService.selectStoreListNoPage(companyId,subCompanyId,storeId,productCode,experienceCardNum,companyType);
    }

    /**
    *@Description 根据id查询门店
    *@Param [storeId, access_token]
    *@Return com.lnmj.store.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/5/8
    *@Time
    */
    @ApiOperation(value = "根据id查询门店",notes = "根据id查询门店")
    @RequestMapping(value = "/selectStoreById",method = RequestMethod.POST)
    public ResponseResult selectStoreById(Long storeId) {
        if (storeId !=null) {
            return storeService.selectStoreById(storeId);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(),ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
    }

    /**
     *@Description 根据id查询门店
     *@Param [storeId, access_token]
     *@Return com.lnmj.store.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/5/8
     *@Time
     */
    @ApiOperation(value = "根据门店编号查询门店",notes = "根据id查询门店")
    @RequestMapping(value = "/selectStoreByCodeOrName",method = RequestMethod.POST)
    public ResponseResult selectStoreByCodeOrName(Long storeId,String code,String name) {
        return storeService.selectStoreByCodeOrName(storeId,code,name);
    }

    /**
    *@Description 根据多个id查询门店
    *@Param [storeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/25
    *@Time 11:21
    */
    @ApiOperation(value = "根据多个id查询门店",notes = "根据多个id查询门店")
    @RequestMapping(value = "/selectStoresByIds",method = RequestMethod.POST)
    public ResponseResult selectStoresByIds(String storeArray) {
        JSONArray  array = JSONArray.parseArray(storeArray);
        List<String> storeList = array.toJavaList(String.class);
        Map map = new HashMap();
        map.put("list",storeList);
        if (array !=null&&array.size()>0) {
            return storeService.selectStoresByIds(map);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(),ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
    }

    /**
    *@Description 添加门店
    *@Param [store, bindingResult] 店铺实体 绑定验证对象
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 9:29
    */
    @ApiOperation(value = "添加门店",notes = "添加门店")
    @RequestMapping(value = "/insertStore",method = RequestMethod.POST)
    public ResponseResult insertStore(@Validated Store store, BindingResult bindingResult) {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(store, bindingResult);
        }
        return storeService.insertStore(store);
    }



    @ApiOperation(value = "生成分公司核算范围",notes = "生成分公司核算范围")
    @RequestMapping(value = "/insertHeSuanFanWei",method = RequestMethod.POST)
    public ResponseResult insertHeSuanFanWei(Store store, String access_token){
        return storeService.insertHeSuanFanWei(store);
    }

    @ApiOperation(value = "生成分公司账簿",notes = "生成分公司账簿")
    @RequestMapping(value = "/insertZhangBu",method = RequestMethod.POST)
    public ResponseResult insertZhangBu(String companyId, String companyType){
        return storeService.insertZhangBu(companyId,companyType);
    }

    /**
    *@Description 修改门店
    *@Param [store, bindingResult] 店铺实体 绑定验证对象
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 10:44
    */
    @ApiOperation(value = "修改门店",notes = "修改门店")
    @RequestMapping(value = "/updateStoreByCodeOrId",method = RequestMethod.POST)
    public ResponseResult updateStoreByCodeOrId(@Validated Store store,BindingResult bindingResult) {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(store, bindingResult);
        }
        return storeService.updateStoreByCodeOrId(store);
    }

    @ApiOperation(value = "修改门店经纬度",notes = "修改门店经纬度")
    @RequestMapping(value = "/updateStoreLatById",method = RequestMethod.POST)
    public ResponseResult updateStoreLatById(Store store) {
        return storeService.updateStoreLatById(store);
    }


    /**
    *@Description 删除门店
    *@Param [storeId] 门店ID
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 10:52
    */
    @ApiOperation(value = "删除门店",notes = "删除门店")
    @RequestMapping(value = "/deleteStore",method = RequestMethod.POST)
    public ResponseResult deleteStore(Long storeId,String modifyOperator) {
        return storeService.deleteStore(storeId,modifyOperator);
    }

    /**
     *@Description 启动或禁用
     *@Param [storeId] 门店ID
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/5/28
     *@Time 10:52
     */
    @ApiOperation(value = "启动或禁用",notes = "启动或禁用")
    @RequestMapping(value = "/storeEnableOrDisEnable",method = RequestMethod.POST)
    public ResponseResult storeEnableOrDisEnable(Long storeId) {
        return storeService.storeEnableOrDisEnable(storeId);
    }

    /**
    *@Description 店铺分类列表显示
    *@Param [storeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/28
    *@Time 11:42
    */
    @ApiOperation(value = "店铺分类列表显示",notes = "店铺分类列表显示")
    @RequestMapping(value = "/storeCategoryList",method = RequestMethod.POST)
    public ResponseResult storeCategoryList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                            String keyWordStoreCategoryName) {
        return storeService.storeCategoryList(pageNum,pageSize,keyWordStoreCategoryName);
    }

    @ApiOperation(value = "店铺分类列表显示",notes = "店铺分类列表显示")
    @RequestMapping(value = "/storeCategoryListNoPage",method = RequestMethod.POST)
    public ResponseResult storeCategoryListNoPage() {
        return storeService.storeCategoryListNoPage();
    }


    /**
    *@Description 修改店铺分类
    *@Param [pageNum, pageSize, keyWordStoreCategoryName]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/28
    *@Time 15:07
    */
    @ApiOperation(value = "修改店铺分类",notes = "修改店铺分类")
    @RequestMapping(value = "/updateStoreCategory",method = RequestMethod.POST)
    public ResponseResult updateStoreCategory(StoreCategory storeCategory) {
        return storeService.updateStoreCategory(storeCategory);
    }

    /**
    *@Description 新增门店分类
    *@Param [storeCategory]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/28
    *@Time 15:16
    */
    @ApiOperation(value = "新增门店分类",notes = "新增门店分类")
    @RequestMapping(value = "/insertStoreCategory",method = RequestMethod.POST)
    public ResponseResult insertStoreCategory(StoreCategory storeCategory) {
        return storeService.insertStoreCategory(storeCategory);
    }

    /**
    *@Description 删除门店分类
    *@Param [storeCategoryId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/28
    *@Time 15:28
    */
    @ApiOperation(value = "删除门店分类",notes = "新增门店分类")
    @RequestMapping(value = "/deleteStoreCategory",method = RequestMethod.POST)
    public ResponseResult deleteStoreCategory(Long storeCategoryId) {
        return storeService.deleteStoreCategory(storeCategoryId);
    }

    /**
    *@Description 查看子公司及其下面的门店
    *@Param [subsidiaryId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/10/22
    *@Time 10:20
    */
    @ApiOperation(value = "查看子公司及其下面的门店",notes = "查看子公司及其下面的门店")
    @RequestMapping(value = "/selectSubCompanyAndStoreNoPage",method = RequestMethod.POST)
    public ResponseResult selectSubCompanyAndStoreNoPage(Long companyId,Integer companyType) {
        return storeService.selectSubCompanyAndStoreNoPage(companyId,companyType);
    }

    /**
    *@Description 查看子公司及同级公司及其下面的门店
    *@Param [subsidiaryId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/10/22
    *@Time 10:20
    */
    @ApiOperation(value = "查看子公司及同级公司及其下面的门店",notes = "查看子公司及同级公司及其下面的门店")
    @RequestMapping(value = "/selectCompanyAndStoreNoPage",method = RequestMethod.POST)
    public ResponseResult selectCompanyAndStoreNoPage(Long companyId,Integer companyType) {
        return storeService.selectCompanyAndStoreNoPage(companyId,companyType);
    }

    @ApiOperation(value = "根据子公司查看门店",notes = "根据子公司查看门店")
    @RequestMapping(value = "/selectStoreListBySubCompany",method = RequestMethod.POST)
    public ResponseResult selectStoreListBySubCompany(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long subsidiaryId,String keyWord) {
        return storeService.selectStoreListBySubCompany(pageNum,pageSize,subsidiaryId,keyWord);
    }

    @ApiOperation(value = "添加门店水单号",notes = "添加门店水单号")
    @RequestMapping(value = "/addStoreMemoNum",method = RequestMethod.POST)
    public ResponseResult addStoreMemoNum(StoreMemoNum storeMemoNum) {
        return storeService.addStoreMemoNum(storeMemoNum);
    }

    @ApiOperation(value = "查看门店水单列表",notes = "添加门店水单号")
    @RequestMapping(value = "/listStoreMemo",method = RequestMethod.POST)
    public ResponseResult listStoreMemo(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,StoreMemoNum storeMemoNum) {
        return storeService.listStoreMemo(pageNum,pageSize,storeMemoNum);
    }

    @ApiOperation(value = "查看水单号是否连续",notes = "查看水单号是否连续")
    @RequestMapping(value = "/checkMenoContinuous",method = RequestMethod.POST)
    public ResponseResult checkMenoContinuous(StoreMemoNum storeMemoNum) {
        return storeService.checkMenoContinuous(storeMemoNum);
    }

    @ApiOperation(value = "查看所有的门店",notes = "查看所有的门店")
    @RequestMapping(value = "/selectStoreAll",method = RequestMethod.POST)
    public ResponseResult selectStoreAll() {
        return storeService.selectStoreAll();
    }
}
