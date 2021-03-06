package com.lnmj.data.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCommodityTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.ICommodityTypeService;
import com.lnmj.data.entity.CommodityType;
import com.lnmj.data.entity.Subclass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:17
 * @Description: 商品大类、商品小类
 */
@Api(description = "商品大类、商品小类")
@RestController
@RequestMapping("/commodityType")
public class CommodityTypeController {
    @Resource
    private ICommodityTypeService commodityTypeService;

    /**
    *@Description 查询商品小类
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/5
    *@Time 10:04
    */
    @ApiOperation(value = "查询商品小类",notes = "查询商品小类")
    @RequestMapping(value = "/selectSubclassList",method = RequestMethod.POST)
    public ResponseResult selectSubclassList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String commodityTypeID,String searchWord) {
        return commodityTypeService.selectSubclassList(pageNum,pageSize,commodityTypeID,searchWord);
    }

    @ApiOperation(value = "查询商品小类不分页",notes = "查询商品小类不分页")
    @RequestMapping(value = "/selectSubclassListNoPage",method = RequestMethod.POST)
    public ResponseResult selectSubclassListNoPage(String commodityTypeID) {
        return commodityTypeService.selectSubclassListNoPage(commodityTypeID);
    }


    /**
    *@Description 条件查询商品小类
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/5
    *@Time 10:06
    */
    @ApiOperation(value = "条件查询商品小类",notes = "条件查询商品小类")
    @RequestMapping(value = "/selectSubclassByCondition",method = RequestMethod.POST)
    public ResponseResult selectSubclassByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                    Subclass subclass,Long industryID ,String access_token,Long storeId) {
        return commodityTypeService.selectSubclassByCondition(pageNum,pageSize,subclass,industryID,storeId);
    }

    @ApiOperation(value = "条件查询商品小类不分页",notes = "条件查询商品小类不分页")
    @RequestMapping(value = "/selectSubclassByConditionNoPage",method = RequestMethod.POST)
    public ResponseResult selectSubclassByConditionNoPage(Subclass subclass,Long industryID) {
        return commodityTypeService.selectSubclassByConditionNoPage(subclass,industryID);
    }

    /**
    *@Description 删除商品小类
    *@Param [subclassID, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/5
    *@Time 10:17
    */
    @ApiOperation(value = "删除商品小类",notes = "删除商品小类")
    @RequestMapping(value = "/deleteSubclass",method = RequestMethod.POST)
    public ResponseResult deleteSubclass(Long subclassID,String modifyOperator, String access_token) {
        if(subclassID==null){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_ID_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.SUBCLASS_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return commodityTypeService.deleteSubclass(subclassID,modifyOperator);
    }

    /**
    *@Description 新增商品小类
    *@Param [subclass, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/5
    *@Time 10:11
    */
    @ApiOperation(value = "新增商品小类",notes = "新增商品小类")
    @RequestMapping(value = "/insertSubclass",method = RequestMethod.POST)
    public ResponseResult insertSubclass(Subclass subclass, String access_token) {
        return commodityTypeService.insertSubclass(subclass);
    }
    /**
    *@Description 修改商品小类
    *@Param [subclass, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/5
    *@Time 10:18
    */
    @ApiOperation(value = "修改商品小类",notes = "修改商品小类")
    @RequestMapping(value = "/updateSubclass",method = RequestMethod.POST)
    public ResponseResult updateSubclass(Subclass subclass, String access_token) {
        if(subclass.getSubclassID()==null){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_ID_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.SUBCLASS_ID_NOT_NULL.getDesc()));
        }
/*        if(StringUtils.isBlank(subclass.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }*/
        return commodityTypeService.updateSubclass(subclass);
    }

    /**
    *@Description 查询商品大类
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/4
    *@Time 15:21
    */
    @ApiOperation(value = "查询商品大类",notes = "查询商品大类")
    @RequestMapping(value = "/selectCommodityTypeList",method = RequestMethod.POST)
    public ResponseResult selectCommodityTypeList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, Integer isDingzhi,Long commodityTypeIndustryID,String type,String searchWord) {
        return commodityTypeService.selectCommodityTypeList(pageNum,pageSize,isDingzhi,commodityTypeIndustryID,type,searchWord);
    }

    @ApiOperation(value = "查询商品大类",notes = "查询商品大类")
    @RequestMapping(value = "/selectCommodityTypeListNoPage",method = RequestMethod.POST)
    public ResponseResult selectCommodityTypeListNoPage( Integer isDingzhi,Long commodityTypeIndustryID,String type) {
        return commodityTypeService.selectCommodityTypeListNoPage(isDingzhi,commodityTypeIndustryID,type);
    }

    /**
    *@Description 条件查询商品大类
    *@Param [pageNum, pageSize, commodityType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/4
    *@Time 15:23
    */
    @ApiOperation(value = "条件查询商品大类",notes = "条件查询商品大类")
    @RequestMapping(value = "/selectCommodityTypeByCondition",method = RequestMethod.POST)
    public ResponseResult selectCommodityTypeByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                         CommodityType commodityType,String access_token) {
        return commodityTypeService.selectCommodityTypeByCondition(pageNum,pageSize,commodityType);
    }

    @ApiOperation(value = "查询商品大类通过Id",notes = "查询商品大类通过Id")
    @RequestMapping(value = "/selectCommodityTypeById",method = RequestMethod.POST)
    public ResponseResult selectCommodityTypeById(Long commodityTypeID) {
        return commodityTypeService.selectCommodityTypeById(commodityTypeID);
    }


    /**
    *@Description 新增商品大类
    *@Param [commodityType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/4
    *@Time 15:23
    */
    @ApiOperation(value = "新增商品大类",notes = "新增商品大类")
    @RequestMapping(value = "/insertCommodityType",method = RequestMethod.POST)
    public ResponseResult insertCommodityType(CommodityType commodityType, String access_token) {
        return commodityTypeService.insertCommodityType(commodityType);
    }

    /**
    *@Description 修改商品大类
    *@Param [commodityType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/4
    *@Time 15:24
    */
    @ApiOperation(value = "修改商品大类",notes = "修改商品大类")
    @RequestMapping(value = "/updateCommodityType",method = RequestMethod.POST)
    public ResponseResult updateCommodityType(CommodityType commodityType, String access_token) {
        if(commodityType.getCommodityTypeID()==null){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_ID_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_ID_NOT_NULL.getDesc()));
        }
        return commodityTypeService.updateCommodityType(commodityType);
    }

    /**
    *@Description 删除商品大类
    *@Param [commodityType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/4
    *@Time 15:25
    */
    @ApiOperation(value = "删除商品大类",notes = "删除商品大类")
    @RequestMapping(value = "/deleteCommodityTypeByID",method = RequestMethod.POST)
    public ResponseResult deleteCommodityTypeByID(Long commodityTypeId,String modifyOperator, String access_token) {
        if(commodityTypeId==null){
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_ID_NOT_NULL.getCode(),
                    ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_ID_NOT_NULL.getDesc()));
        }

        return commodityTypeService.deleteCommodityTypeByID(commodityTypeId,modifyOperator);
    }

}
