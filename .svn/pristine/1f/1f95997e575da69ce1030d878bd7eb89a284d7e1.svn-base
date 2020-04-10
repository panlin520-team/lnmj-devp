package com.lnmj.product.controller.portal;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 14:47
 * @Description: 商品分类controller
 */
@Api(description = "商品分类")
@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Resource(name = "productTypeService")
    private IProductTypeService productTypeService;

    /**
     * @Description 商品分类分页显示
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "商品分类分页显示", notes = "商品分类分页显示")
    @RequestMapping(value = "/selectProductTypeList", method = RequestMethod.POST)
    public ResponseResult selectProductTypeList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return productTypeService.selectProductTypeList(pageNum, pageSize);
    }

    /**
     * @Description 商品分类关键字查询
     * @Param [pageNum, pageSize, keyWord, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:40
     */
    @ApiOperation(value = "商品分类关键字查询", notes = "商品分类关键字查询")
    @RequestMapping(value = "/selectProductTypeByKeyWord", method = RequestMethod.POST)
    public ResponseResult selectProductTypeByKeyWord(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                     String keyWord, String access_token) {
        return productTypeService.selectProductTypeByKeyWord(pageNum, pageSize, keyWord);
    }
}
