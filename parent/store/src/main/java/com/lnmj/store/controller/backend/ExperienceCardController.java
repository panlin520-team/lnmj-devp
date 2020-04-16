package com.lnmj.store.controller.backend;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IExperienceCardService;
import com.lnmj.store.entity.Experiencecard;
import com.lnmj.store.entity.ExperiencecardOrderDetail;
import com.lnmj.store.entity.ExperiencecardProduct;
import com.lnmj.store.entity.ExperiencecardUseRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @Auther: panlin
 * @Date: 2019/8/5 11:32
 * @Description:
 */
@Api(description = "体验卡")
@RestController
@RequestMapping("/manage/experienceCard")
public class ExperienceCardController {
    @Resource(name = "experienceCardService")
    private IExperienceCardService iExperienceCardService;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    /**
     *@Description 上传美容师头像图片
     *@Param [file]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/4
     *@Time 10:56
     */
    @ApiOperation(value = "上传美容师头像图片",notes = "上传美容师头像图片")
    @RequestMapping(value = "/uploadBeauticianImg",method = RequestMethod.POST)
    public ResponseResult uploadBeauticianImg(MultipartFile file) throws IOException {
        String imageType = "experiencecardType";
        ResponseResult responseResult = ftpFileUploadController.uploadImg(file,imageType);
        return  responseResult;

    }

    /**
    *@Description 查询体验卡列表
    *@Param [file]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/5
    *@Time 10:56
    */
    @ApiOperation(value = "查询体验卡列表",notes = "查询体验卡列表")
    @RequestMapping(value = "/selectExperienceCardList",method = RequestMethod.POST)
    public ResponseResult selectExperienceCardList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordCardName,String keyWordCarNum,String storeId) {
        return  iExperienceCardService.selectExperienceCardList(pageNum,pageSize,keyWordCardName,keyWordCarNum,storeId);

    }

    @ApiOperation(value = "查询体验卡通过id",notes = "查询体验卡通过id")
    @RequestMapping(value = "/selectExperienceCardByCardId",method = RequestMethod.POST)
    public ResponseResult selectExperienceCardByCardId(Long cardId) {
        return  iExperienceCardService.selectExperienceCardByCardId(cardId);

    }

    /**
    *@Description  查询体验卡项目列表
    *@Param [pageNum, pageSize, cardNum]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/6
    *@Time 11:09
    */
    @ApiOperation(value = "查询体验卡项目列表",notes = "查询体验卡项目列表")
    @RequestMapping(value = "/selectExpCardProductListByCarNum",method = RequestMethod.POST)
    public ResponseResult selectExpCardProductListByCarNum(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String cardNum) {
        return  iExperienceCardService.selectExpCardProductListByCarNum(pageNum,pageSize,cardNum);

    }

    /**
    *@Description 删除体验卡
    *@Param [cardNum]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/6
    *@Time 11:10
    */
    @ApiOperation(value = "删除体验卡",notes = "删除体验卡")
    @RequestMapping(value = "/deleteExpCardByCarNum",method = RequestMethod.POST)
    public ResponseResult deleteExpCardByCarNum(String cardNum) {
        return  iExperienceCardService.deleteExpCardByCarNum(cardNum);

    }

    /**
    *@Description 删除体验卡项目
    *@Param [cardProductId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/6
    *@Time 12:03
    */
    @ApiOperation(value = "删除体验卡项目",notes = "删除体验卡项目")
    @RequestMapping(value = "/deleteExpCardProductById",method = RequestMethod.POST)
    public ResponseResult deleteExpCardProductById(Long cardProductId) {
        return  iExperienceCardService.deleteExpCardProductById(cardProductId);

    }

    /**
    *@Description 修改体验卡项目
    *@Param [experiencecardProduct]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/6
    *@Time 17:17
    */
    @ApiOperation(value = "修改体验卡项目",notes = "删除体验卡项目")
    @RequestMapping(value = "/updateExpCardProductById",method = RequestMethod.POST)
    public ResponseResult updateExpCardProductById(ExperiencecardProduct experiencecardProduct) {
        return  iExperienceCardService.updateExpCardProductById(experiencecardProduct);

    }

    /**
    *@Description 修改体验卡
    *@Param [experiencecard]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/6
    *@Time 17:52
    */
    @ApiOperation(value = "修改体验卡",notes = "修改体验卡")
    @RequestMapping(value = "/updateExpCardById",method = RequestMethod.POST)
    public ResponseResult updateExpCardById(Experiencecard experiencecard) {
        return  iExperienceCardService.updateExpCardById(experiencecard);

    }
    /**
    *@Description 添加体验卡
    *@Param [experiencecard]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/7
    *@Time 13:20
    */
    @ApiOperation(value = "添加体验卡",notes = "添加体验卡")
    @RequestMapping(value = "/addExpCard",method = RequestMethod.POST)
    public ResponseResult addExpCard(Long subordBuyLimitId, Double account, String cardName , String createOperator, String productArray, Long achievementPostId,Long achievementId, String logoImage, String moreContent, Integer stockNum, Integer salesVolume, String purchaseDeadline) {
        JSONArray productJsonArray = JSONArray.parseArray(productArray);
        return  iExperienceCardService.addExpCard(subordBuyLimitId,account,cardName,createOperator,productJsonArray,achievementPostId,achievementId,logoImage,moreContent,stockNum,salesVolume,purchaseDeadline);

    }

    /**
    *@Description 添加体验卡订单
    *@Param [account, cardName, cardNum, createOperator, linkPhone, purchaserName, storeId, userId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/7
    *@Time 16:50
    */
    @ApiOperation(value = "添加体验卡订单",notes = "添加体验卡订单")
    @RequestMapping(value = "/addExpCardOrder",method = RequestMethod.POST)
    public ResponseResult addExpCardOrder(String memoNum,Double account,String cardName,String cardNum,String createOperator,String linkPhone,String purchaserName,Integer storeId,String memberNum,String beauticians,String payTypeAndAmount) {
        return  iExperienceCardService.addExpCardOrder(memoNum,account,cardName,cardNum,createOperator,linkPhone,purchaserName,storeId,memberNum,beauticians,payTypeAndAmount);
    }

    /**
    *@Description 查看体验卡订单
    *@Param [account, cardName, cardNum, createOperator, linkPhone, purchaserName, storeId, userId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/8/7
    *@Time 17:22
    */
    @ApiOperation(value = "查看体验卡订单",notes = "查看体验卡订单")
    @RequestMapping(value = "/selectExpCardOrder",method = RequestMethod.POST)
    public ResponseResult selectExpCardOrder(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                             String keyWordLinkPhone,
                                             String keyWordLinkName,
                                             String cardName,
                                             String startdate,
                                             String endDate,
                                             String memoNum,
                                             Integer storeId) {
        return  iExperienceCardService.selectExpCardOrder(pageNum,pageSize,keyWordLinkPhone,keyWordLinkName,cardName,startdate,endDate,memoNum,storeId);
    }


    @ApiOperation(value = "查看体验卡订单详情",notes = "查看体验卡订单详情")
    @RequestMapping(value = "/selectExpCardOrderDetail",method = RequestMethod.POST)
    public ResponseResult selectExpCardOrderDetail(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String cardOrderCode) {
        return  iExperienceCardService.selectExpCardOrderDetail(pageNum,pageSize,cardOrderCode);
    }

    @ApiOperation(value = "修改体验卡订单详情",notes = "修改体验卡订单详情")
    @RequestMapping(value = "/updateExpCardOrderDetail",method = RequestMethod.POST)
    public ResponseResult updateExpCardOrderDetail(ExperiencecardOrderDetail experiencecardOrderDetail) {
        return  iExperienceCardService.updateExpCardOrderDetail(experiencecardOrderDetail);
    }

    @ApiOperation(value = "添加使用记录",notes = "添加使用记录")
    @RequestMapping(value = "/addUseRecord",method = RequestMethod.POST)
    public ResponseResult addUseRecord(ExperiencecardUseRecord experiencecardUseRecord) {
        return  iExperienceCardService.addUseRecord(experiencecardUseRecord);
    }

    @ApiOperation(value = "修改使用记录状态",notes = "修改使用记录状态")
    @RequestMapping(value = "/updateUseRecordStatus",method = RequestMethod.POST)
    public ResponseResult updateUseRecordStatus(ExperiencecardUseRecord experiencecardUseRecord) {
        return  iExperienceCardService.updateUseRecordStatus(experiencecardUseRecord);
    }

    @ApiOperation(value = "查询使用记录列表",notes = "添加使用记录")
    @RequestMapping(value = "/selectUseRecord",method = RequestMethod.POST)
    public ResponseResult selectUseRecord(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long cardOrderDetailId) {
        return  iExperienceCardService.selectUseRecord(pageNum,pageSize,cardOrderDetailId);
    }

    @ApiOperation(value = "查看会员拥有的体验卡",notes = "查看会员拥有的体验卡")
    @RequestMapping(value = "/selectExpCardUser",method = RequestMethod.POST)
    public ResponseResult selectExpCardUser(String memberNum) {
        return  iExperienceCardService.selectExpCardUser(memberNum);
    }

    @ApiOperation(value = "查看会员拥有的体验卡",notes = "查看会员拥有的体验卡")
    @RequestMapping(value = "/selectExpCardUserProduct",method = RequestMethod.POST)
    public ResponseResult selectExpCardUserProduct(String memberNum,Long experiencecardUserId) {
        return  iExperienceCardService.selectExpCardUserProduct(memberNum,experiencecardUserId);
    }

    @ApiOperation(value = "修改会员体验卡使用次数（划卡）",notes = "修改会员体验卡使用次数（划卡）")
    @RequestMapping(value = "/updateUserExpUseTimes",method = RequestMethod.POST)
    public ResponseResult updateUserExpUseTimes(String staffNumber,Long id,String experiencecardNum,String memberNum,String productCode,Long storeId,String storeName,String productName,String postAndBeautician,String createOperator,String memberName,String memberMobile) {
        return  iExperienceCardService.updateUserExpUseTimes(staffNumber,id,experiencecardNum,memberNum,productCode,storeId,storeName,productName,postAndBeautician,createOperator,memberName,memberMobile);
    }

    @ApiOperation(value = "退货使用(次数)",notes = "退货使用(次数)")
    @RequestMapping(value = "/experiencecardProductUserRefuse",method = RequestMethod.POST)
    public ResponseResult experiencecardProductUserRefuse(Long id,Integer refuseTimes) {
        return  iExperienceCardService.experiencecardProductUserRefuse(id,refuseTimes);
    }

    @ApiOperation(value = "查看退货使用次数明细",notes = "查看退货使用次数明细")
    @RequestMapping(value = "/selectExperiencecardProductUserRefuseList",method = RequestMethod.POST)
    public ResponseResult selectExperiencecardProductUserRefuseList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long id) {
        return  iExperienceCardService.selectExperiencecardProductUserRefuseList(pageNum,pageSize,id);
    }


    @ApiOperation(value = "增加使用次数",notes = "增加使用次数")
    @RequestMapping(value = "/addUserExpUseTimes",method = RequestMethod.POST)
    public ResponseResult addUserExpUseTimes(Long id) {
        return  iExperienceCardService.addUserExpUseTimes(id);
    }

    @ApiOperation(value = "修改体验卡订单水单号",notes = "修改体验卡订单水单号")
    @RequestMapping(value = "/updateExpOrderMemoNum",method = RequestMethod.POST)
    public ResponseResult updateExpOrderMemoNum(String cardOrderCode,String memoNum,String modifyOperator) {
        return  iExperienceCardService.updateExpOrderMemoNum(cardOrderCode,memoNum,modifyOperator);
    }

    @ApiOperation(value = "查看水单号是否重复",notes = "查看水单号是否重复")
    @RequestMapping(value = "/checkOrderMemo",method = RequestMethod.POST)
    public ResponseResult checkOrderMemo(String memoNum) {
        return  iExperienceCardService.checkOrderMemo(memoNum);
    }

    @ApiOperation(value = "更新体验卡下所有项目的出库价",notes = "更新体验卡下所有项目的出库价")
    @RequestMapping(value = "/updateAllExpCardProductOutStockPrice",method = RequestMethod.POST)
    public ResponseResult updateAllExpCardProductOutStockPrice(Long cardId) {
        return  iExperienceCardService.updateAllExpCardProductOutStockPrice(cardId);
    }

    @ApiOperation(value = "取消体验卡订单",notes = "取消体验卡订单")
    @RequestMapping(value = "/cancleExpOrder",method = RequestMethod.POST)
    public ResponseResult cancleExpOrder(String orderNumbers, Integer orderStatus) {
        return  iExperienceCardService.cancleExpOrder(orderNumbers,orderStatus);
    }

    @ApiOperation(value = "修改体验卡订单状态",notes = "修改体验卡订单状态")
    @RequestMapping(value = "/updateExpOrderStatus",method = RequestMethod.POST)
    public ResponseResult updateExpOrderStatus(String cardOrderCode, Integer orderStatus) {
        return  iExperienceCardService.updateExpOrderStatus(cardOrderCode,orderStatus);
    }

    @ApiOperation(value = "查看体验卡订单里面已支付的订单的现金支付的总额",notes = "查看体验卡订单里面已支付的订单的现金支付的总额")
    @RequestMapping(value = "/selectExpOrderCashAll",method = RequestMethod.POST)
    public ResponseResult selectExpOrderCashAll(String memberNumber) {
        return  iExperienceCardService.selectExpOrderCashAll(memberNumber);
    }

    @ApiOperation(value = "查看体验卡是否使用",notes = "查看体验卡是否使用")
    @RequestMapping(value = "/checkExpIsUse",method = RequestMethod.POST)
    public ResponseResult checkExpIsUse(String experiencecardNum,String memberCode) {
        return  iExperienceCardService.checkExpIsUse(experiencecardNum,memberCode);
    }

    @ApiOperation(value = "修改体验卡库存数量(增加)",notes = "修改体验卡库存数量(增加)")
    @RequestMapping(value = "/updateExpCarStockNumUp",method = RequestMethod.POST)
    public ResponseResult updateExpCarStockNumUp(String experiencecardNum) {
        return  iExperienceCardService.updateExpCarStockNumUp(experiencecardNum);
    }
}
