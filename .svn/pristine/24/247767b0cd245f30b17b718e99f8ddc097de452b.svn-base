package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IMerchantSourceService;
import com.lnmj.product.business.IProductBrandService;
import com.lnmj.product.entity.VO.ProductBrandVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: renqingyun
 * @Date: 2019/5/6 18:35
 * @Description: 来源商家controller
 */
@Api(description = "来源商家")
@RestController
@RequestMapping("/merchantSource")
public class MerchantSourceController {
    @Resource
    private IMerchantSourceService merchantSourceService;

    /**
    *@Description 查询来源商家
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/22
    *@Time
    */
    @ApiOperation(value = "查询来源商家", notes = "查询来源商家")
    @RequestMapping(value = "/selectMerchantSourceList", method = RequestMethod.POST)
    public ResponseResult selectMerchantSourceList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return merchantSourceService.selectMerchantSourceList(pageNum, pageSize);
    }



}
