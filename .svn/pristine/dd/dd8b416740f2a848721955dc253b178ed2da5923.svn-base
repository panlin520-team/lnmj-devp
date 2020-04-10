package com.lnmj.k3cloud.controller.backend.dataCentre;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IDataCentreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Api(description = "供应商管理")
@RestController
@RequestMapping("/k3cloud/datacentre/datacentre")
public class K3DataCentreController {
    @Resource
    private IDataCentreService iDataCentreService;

    @ApiOperation(value = "查询k3数据中心列表信息",notes = "查询k3数据中心列表信息")
    @RequestMapping(value = "/selectK3DataCentreList",method = RequestMethod.POST)
    public ResponseResult selectK3DataCentreList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                 String dataCentreName){
        return iDataCentreService.selectK3DataCentreList (pageNum,pageSize,dataCentreName);
    }

    @ApiOperation(value = "查询指定数据库下法人的组织编号",notes = "查询指定数据库下法人的组织编号")
    @RequestMapping(value = "/selectK3LegalPersonNumber",method = RequestMethod.POST)
    public ResponseResult selectK3LegalPersonNumber(String dataCentreName){
        return iDataCentreService.selectK3LegalPersonNumber (dataCentreName);
    }

}
