package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductNursingService;
import com.lnmj.product.entity.ProductNursing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author renqingyun
 * @Date: 2019/5/15 11:05
 * @Description:商品护理
 */
@Api(description = "商品护理")
@RestController
@RequestMapping("/manage/productNursing")
public class ProductNursingController {
    @Resource
    private IProductNursingService productNursingService;


    /**
     * @Description 新增商品护理方式
     * @Param [pageNum, pageSize, keyword, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/15
     * @Time
     */
    @ApiOperation(value = "新增商品护理方式", notes = "新增商品护理方式")
    @RequestMapping(value = "/insertProductNursing", method = RequestMethod.POST)
    public ResponseResult insertProductNursing(@Validated ProductNursing productNursing, BindingResult bindingResult, String access_token) {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(productNursing, bindingResult);
        }
        return productNursingService.insertProductNursing(productNursing);
    }


    /**
    *@Description 删除商品护理方式
    *@Param [productNursing, bindingResult, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/5/15
    *@Time
    */
    @ApiOperation(value = "删除商品护理方式", notes = "删除商品护理方式")
    @RequestMapping(value = "/deleteProductNursing", method = RequestMethod.POST)
    public ResponseResult deleteProductNursing(Long productNursingId, String access_token) {
        if (0 ==productNursingId) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTNURSING_IDS_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTNURSING_IDS_ISNULL.getDesc()));
        }
        return productNursingService.deleteProductNursing(productNursingId);
    }


    public static ResponseResult ProductCheck(@Validated ProductNursing productNursing, BindingResult bindingResult) {
        int count = bindingResult.getErrorCount();
        HashMap<Object, Object> map = new HashMap<>();
        if (count > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < count; i++) {
                FieldError error = fieldErrors.get(i);
                map.put(error.getField(), error.getDefaultMessage());
            }
        }
        return ResponseResult.error(new Error(1, map));
    }
}
