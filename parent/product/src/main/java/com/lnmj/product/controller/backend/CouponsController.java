package com.lnmj.product.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.ICouponsService;
import com.lnmj.product.business.IProductDivisionService;
import com.lnmj.product.entity.Coupons;
import com.lnmj.product.entity.CouponsGetRecord;
import com.lnmj.product.entity.VO.ProductDivisionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: panlin
 * @Date: 2019/8/21 12:12
 * @Description: 优惠券管理
 */
@Api(description = "优惠券管理")
@RestController
@RequestMapping("/coupons")
public class CouponsController {
    @Resource
    private ICouponsService couponsService;

    /**
    *@Description 优惠券列表显示
    *@Param [pageNum, pageSize, keyWordCouponsType, keyWordCouponsName]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/21
    *@Time 16:14
    */
    @ApiOperation(value = "优惠券列表显示", notes = "优惠券列表显示")
    @RequestMapping(value = "/couponsList", method = RequestMethod.POST)
    public ResponseResult couponsList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long keyWordCouponsType,String keyWordCouponsName) {
        return couponsService.couponsList(pageNum, pageSize,keyWordCouponsType,keyWordCouponsName);
    }

    /**
    *@Description 优惠券类型显示
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/21
    *@Time 16:40
    */
    @ApiOperation(value = "优惠券类型显示", notes = "优惠券类型显示")
    @RequestMapping(value = "/couponsTypeList", method = RequestMethod.POST)
    public ResponseResult couponsTypeList() {
        return couponsService.couponsTypeList();
    }


    /**
    *@Description 优惠券类型显示
    *@Param [coupons]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/21
    *@Time 16:44
    */
    @ApiOperation(value = "添加优惠券", notes = "添加优惠券")
    @RequestMapping(value = "/addCoupons", method = RequestMethod.POST)
    public ResponseResult addCoupons(Coupons coupons) {
        return couponsService.addCoupons(coupons);
    }

    /**
    *@Description 优惠券下架
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/21
    *@Time 19:29
    */
    @ApiOperation(value = "优惠券下架", notes = "优惠券下架")
    @RequestMapping(value = "/couponsDown", method = RequestMethod.POST)
    public ResponseResult couponsDown(Coupons coupons) {
        return couponsService.couponsDown(coupons);
    }


    /**
    *@Description 修改优惠券
    *@Param [coupons]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/23
    *@Time 9:31
    */
    @ApiOperation(value = "修改优惠券", notes = "修改优惠券")
    @RequestMapping(value = "/updateCoupons", method = RequestMethod.POST)
    public ResponseResult updateCoupons(Coupons coupons) {
        return couponsService.updateCoupons(coupons);
    }

    /**
    *@Description 查看优惠券领取记录
    *@Param [pageNum, pageSize, keyWordCouponsType, keyWordCouponsName]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/23
    *@Time 11:16
    */
    @ApiOperation(value = "查看优惠券领取记录", notes = "查看优惠券领取记录")
    @RequestMapping(value = "/selectCouponsGetRecordList", method = RequestMethod.POST)
    public ResponseResult selectCouponsGetRecordList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long keyWordCouponsType,String keyWordCouponsName) {
        return couponsService.selectCouponsGetRecordList(pageNum,pageSize,keyWordCouponsType,keyWordCouponsName);
    }

    /**
    *@Description 删除优惠券领取记录
    *@Param [couponsGetRecordId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/23
    *@Time 14:34
    */
    @ApiOperation(value = "删除优惠券领取记录", notes = "删除优惠券领取记录")
    @RequestMapping(value = "/deleteCouponsGetRecord", method = RequestMethod.POST)
    public ResponseResult deleteCouponsGetRecord(Long couponsGetRecordId) {
        return couponsService.deleteCouponsGetRecord(couponsGetRecordId);
    }

    /**
    *@Description 查看优惠券使用记录
    *@Param [pageNum, pageSize, keyWordOrderNum, keyWordUserName]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/23
    *@Time 15:08
    */
    @ApiOperation(value = "查看优惠券使用记录", notes = "查看优惠券领取记录")
    @RequestMapping(value = "/selectCouponsUseRecordList", method = RequestMethod.POST)
    public ResponseResult selectCouponsUseRecordList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long keyWordOrderNum,Long keyWordUserId,Long keyWordCouponsType) {
        return couponsService.selectCouponsUseRecordList(pageNum,pageSize,keyWordOrderNum,keyWordUserId,keyWordCouponsType);
    }

    @ApiOperation(value = "删除优惠券使用记录", notes = "删除优惠券使用记录")
    @RequestMapping(value = "/deleteCouponsUseRecord", method = RequestMethod.POST)
    public ResponseResult deleteCouponsUseRecord(Long couponsUseRecordId) {
        return couponsService.deleteCouponsUseRecord(couponsUseRecordId);
    }


    /**
     *@Description 添加优惠券领取记录
     *@Param [coupons]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/8/23
     *@Time 9:31
     */
    /*@ApiOperation(value = "添加优惠券领取记录", notes = "添加优惠券领取记录")
    @RequestMapping(value = "/addCouponsGetRecord", method = RequestMethod.POST)
    public ResponseResult addCouponsGetRecord(CouponsGetRecord couponsGetRecord) {
        return couponsService.addCouponsGetRecord(couponsGetRecord);
    }*/

}
