package com.lnmj.product.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.ICouponsService;
import com.lnmj.product.business.IUnitService;
import com.lnmj.product.entity.Coupons;
import com.lnmj.product.entity.Unit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api(description = "单位管理")
@RestController
@RequestMapping("/unit")
public class UnitController {
    @Resource
    private IUnitService unitService;

    /**
    *@Description 单位列表显示
    *@Param [pageNum, pageSize]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/11/12
    *@Time
    */
    @ApiOperation(value = "单位列表显示", notes = "单位列表显示")
    @RequestMapping(value = "/selectUnitList", method = RequestMethod.POST)
    public ResponseResult selectUnitList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String keyWord) {
        return unitService.selectUnitList(pageNum, pageSize,keyWord);
    }

    @ApiOperation(value = "单位列表显示", notes = "单位列表显示")
    @RequestMapping(value = "/selectUnitListNoPage", method = RequestMethod.POST)
    public ResponseResult selectUnitListNoPage() {
        return unitService.selectUnitListNoPage();
    }

    @ApiOperation(value = "添加单位", notes = "添加单位")
    @RequestMapping(value = "/addUnit", method = RequestMethod.POST)
    public ResponseResult addUnit(Unit unit) {
        return unitService.addUnit(unit);
    }


    @ApiOperation(value = "修改单位", notes = "修改单位")
    @RequestMapping(value = "/editUnit", method = RequestMethod.POST)
    public ResponseResult editUnit(Unit unit) {
        return unitService.editUnit(unit);
    }

    @ApiOperation(value = "删除单位", notes = "删除单位")
    @RequestMapping(value = "/deleteUnit", method = RequestMethod.POST)
    public ResponseResult deleteUnit(String unitId,String modifyOperator) {
        return unitService.deleteUnit(unitId,modifyOperator);
    }

    @ApiOperation(value = "查看单位组枚举", notes = "查看单位组枚举")
    @RequestMapping(value = "/selectUnitGroup", method = RequestMethod.POST)
    public ResponseResult selectUnitGroup() {
        return unitService.selectUnitGroup();
    }

    @ApiOperation(value = "查看单位舍入类型枚举", notes = "查看单位舍入类型枚举")
    @RequestMapping(value = "/selectFroundType", method = RequestMethod.POST)
    public ResponseResult selectFroundType() {
        return unitService.selectFroundType();
    }

}
