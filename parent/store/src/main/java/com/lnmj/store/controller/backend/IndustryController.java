package com.lnmj.store.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IIndustryService;
import com.lnmj.store.entity.Industry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 〈行业〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */
@Api(description = "行业")
@RestController
@RequestMapping("/manage/industry")
public class IndustryController {
    @Autowired
    private IIndustryService industryService;


    @ApiOperation(value = "查询所有行业",notes = "查询所有行业")
    @RequestMapping(value = "/selectList",method = RequestMethod.POST)
    public ResponseResult selectList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String industryNameKeyword,String access_token) {
        return industryService.selectList(pageNum,pageSize,industryNameKeyword);
    }

    @ApiOperation(value = "根据id查看行业",notes = "根据id查看行业")
    @RequestMapping(value = "/selectListIndustryById",method = RequestMethod.POST)
    public ResponseResult selectListIndustryById(Long industryID) {
        return industryService.selectListIndustryById(industryID);
    }

    @ApiOperation(value = "查询所有行业不分页",notes = "查询所有行业不分页")
    @RequestMapping(value = "/selectListIndustryNoPage",method = RequestMethod.POST)
    public ResponseResult selectListIndustryNoPage() {
        return industryService.selectListIndustryNoPage();
    }

    @ApiOperation(value = "添加行业",notes = "添加行业")
    @RequestMapping(value = "/addIndustry",method = RequestMethod.POST)
    public ResponseResult addIndustry(Industry industry) {
        return industryService.addIndustry(industry);
    }

    @ApiOperation(value = "修改行业",notes = "修改行业")
    @RequestMapping(value = "/updateIndustry",method = RequestMethod.POST)
    public ResponseResult updateIndustry(Industry industry) {
        return industryService.updateIndustry(industry);
    }

    @ApiOperation(value = "删除行业",notes = "删除行业")
    @RequestMapping(value = "/deleteIndustry",method = RequestMethod.POST)
    public ResponseResult deleteIndustry(Industry industry) {
        return industryService.deleteIndustry(industry);
    }
}
