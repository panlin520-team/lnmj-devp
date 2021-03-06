package com.lnmj.wallet.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */
@FeignClient(value = "lnmj-store")
public interface StoreApi {
    @RequestMapping(value = "/api/manage/presentation/insertPresentation", method = RequestMethod.POST)
    ResponseResult insertPresentation(@RequestParam("walletId")Integer orderType, @RequestParam("orderNumber")String orderNumber,
                                      @RequestParam("orderLink")String orderLink, @RequestParam("mobile")String mobile,
                                      @RequestParam("cartNumber")String cartNumber, @RequestParam("productId")Long productId,
                                      @RequestParam("productName")String productName, @RequestParam("contacts")String contacts,
                                      @RequestParam("phone")String phone, @RequestParam("presentationTime")Date presentationTime,
                                      @RequestParam("presentationEndTime")Date presentationEndTime);

    @RequestMapping(value = "/api/manage/beautician/selectBeauticianListNoPage",method = RequestMethod.POST)
    ResponseResult selectBeauticianListNoPage(@RequestParam("companyType")String companyType,@RequestParam("companyId")String companyId,@RequestParam("isSaleMan")Integer isSaleMan);

    @RequestMapping(value = "/manage/store/selectStoreAll", method = RequestMethod.POST)
    ResponseResult selectStoreAll();

}

