package com.lnmj.store.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ISubordBuyLimitService;
import com.lnmj.store.entity.SubordBuyLimit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/8/25 11:32
 * @Description:
 */
@Api(description = "限购管理")
@RestController
@RequestMapping("/manage/subordBuyLimit")
public class SubordBuyLimitController {
    @Resource
    private ISubordBuyLimitService iSubordBuyLimitService;


    /**
     * @Description 查询体验卡列表
     * @Param [file]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/8/5
     * @Time 10:56
     */
    @ApiOperation(value = "查询限购列表", notes = "查询限购列表")
    @RequestMapping(value = "/selectSubordBuyLimitList", method = RequestMethod.POST)
    public ResponseResult selectSubordBuyLimitList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Integer productType, String keyWordSubordBuyLimitName) {
        return iSubordBuyLimitService.selectSubordBuyLimitList(pageNum, pageSize,productType ,keyWordSubordBuyLimitName);

    }

    @ApiOperation(value = "删除限购", notes = "删除限购")
    @RequestMapping(value = "/deleteSubordBuyLimit", method = RequestMethod.POST)
    public ResponseResult deleteSubordBuyLimit(Long subordBuyLimitId) {
        Map map = new HashMap() ;
        map.put("subordBuyLimitId",subordBuyLimitId);
        return iSubordBuyLimitService.deleteSubordBuyLimit(map);

    }

    @ApiOperation(value = "后去限购商品类型", notes = "删除限购")
    @RequestMapping(value = "/selectSubordBuyLimitProductType", method = RequestMethod.POST)
    public ResponseResult selectSubordBuyLimitProductType() {
        return iSubordBuyLimitService.selectSubordBuyLimitProductType();

    }

    @ApiOperation(value = "添加限购", notes = "删除限购")
    @RequestMapping(value = "/addSubordBuyLimit", method = RequestMethod.POST)
    public ResponseResult addSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        return iSubordBuyLimitService.addSubordBuyLimit(subordBuyLimit);

    }

    @ApiOperation(value = "修改限购", notes = "删除限购")
    @RequestMapping(value = "/updateSubordBuyLimit", method = RequestMethod.POST)
    public ResponseResult updateSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        return iSubordBuyLimitService.updateSubordBuyLimit(subordBuyLimit);

    }
}
