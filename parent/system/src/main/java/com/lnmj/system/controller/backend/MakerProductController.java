package com.lnmj.system.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerProductService;
import com.lnmj.system.entity.MakerProduct;
import com.lnmj.system.entity.MakerProductDetail;
import com.lnmj.system.entity.VO.MakerProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:24
 * @Description:  创客产品
 */
@Api(description = "创客产品")
@RestController
@RequestMapping("/manage/maker/product")
public class MakerProductController {
    @Resource
    private IMakerProductService makerProductService;

    /**
    *@Description 条件查询创客商品项目
    *@Param [pageNum, pageSize, makerProductDetail, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/27
    *@Time 15:54
    */
    @ApiOperation(value = "条件查询创客商品项目",notes = "条件查询创客商品项目")
    @RequestMapping(value = "/selectMakerProductDetailByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerProductDetailByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                              MakerProductDetail makerProductDetail, String access_token) {
        return makerProductService.selectMakerProductDetailByCondition(pageNum,pageSize,makerProductDetail);
    }

    /**
     *@Description 查询创客商品
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:48
     */
    @ApiOperation(value = "查询创客商品",notes = "查询创客商品")
    @RequestMapping(value = "/selectMakerProductList",method = RequestMethod.POST)
    public ResponseResult selectMakerProductList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return makerProductService.selectMakerProductList(pageNum,pageSize);
    }

    /**
     *@Description 条件查询创客商品
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:48
     */
    @ApiOperation(value = "条件查询创客商品",notes = "条件查询创客商品")
    @RequestMapping(value = "/selectMakerProductByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerProductByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                     MakerProduct makerProduct, String access_token) {
        return makerProductService.selectMakerProductByCondition(pageNum,pageSize,makerProduct);
    }

    /**
     *@Description 新增创客商品
     *@Param [makerProduct, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:55
     */
    @ApiOperation(value = "新增创客商品",notes = "条件查询创客商品")
    @RequestMapping(value = "/insertMakerProduct",method = RequestMethod.POST)
    public ResponseResult insertMakerProduct(MakerProductVO makerProduct, String access_token) {
        return makerProductService.insertMakerProduct(makerProduct);
    }

    /**
     *@Description 修改创客商品
     *@Param [makerProduct, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:55
     */
    @ApiOperation(value = "修改创客商品",notes = "修改创客商品")
    @RequestMapping(value = "/updateMakerProduct",method = RequestMethod.POST)
    public ResponseResult updateMakerProduct(MakerProductVO makerProduct, String access_token) {
        if(makerProduct.getMakerProductId()==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(makerProduct.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerProductService.updateMakerProduct(makerProduct);
    }

    /**
     *@Description 删除创客商品
     *@Param [makerProductId, modifyOperator, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:57
     */
    @ApiOperation(value = "删除创客商品",notes = "删除创客商品")
    @RequestMapping(value = "/deleteMakerProduct",method = RequestMethod.POST)
    public ResponseResult deleteMakerProduct(Long makerProductId,String modifyOperator, String access_token) {
        if(makerProductId==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerProductService.deleteMakerProduct(makerProductId,modifyOperator);
    }

    /**
     *@Description 删除创客商品项目
     *@Param [makerProductDetailId, modifyOperator, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:57
     */
    @ApiOperation(value = "删除创客商品项目",notes = "删除创客商品项目")
    @RequestMapping(value = "/deleteMakerProductDetail",method = RequestMethod.POST)
    public ResponseResult deleteMakerProductDetail(Long makerProductDetailId,String modifyOperator, String access_token) {
        if(makerProductDetailId==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerProductService.deleteMakerProductDetail(makerProductDetailId,modifyOperator);
    }


}
