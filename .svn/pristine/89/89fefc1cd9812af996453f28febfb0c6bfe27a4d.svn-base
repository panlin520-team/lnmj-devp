package com.lnmj.account.controller.backend.mall;


import com.lnmj.account.business.ILabelCategoryService;
import com.lnmj.account.entity.LabelCategory;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Author: renqingyun
 * @Date: 2019/4/19 11:34
 * @Description:用户标签分类管理
 */
@Api(description = "用户标签分类接口")
@RestController
@RequestMapping("/manage/mall/labelCategory")
public class MallLabelCategoryManageController {
    @Resource
    private ILabelCategoryService labelCategoryService;

    @ApiOperation(value = "标签分类添加", notes = "标签分类添加")
    @RequestMapping(value = "/addLabelCategory", method = RequestMethod.POST)
    public ResponseResult addLabelCategory(LabelCategory labelCategory, String access_token) {
        if (StringUtils.isBlank(labelCategory.getCategoryName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABELCATEGORY_NAME_NULL.getCode(), ResponseCodeAccountEnum.LABELCATEGORY_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(labelCategory.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getDesc()));
        }
        return this.labelCategoryService.addCategory(labelCategory);
    }

    @ApiOperation(value = "获取所有标签类型", notes = "获取所有标签类型")
    @RequestMapping(value = "/selectLabelCategoryList", method = RequestMethod.POST)
    public ResponseResult selectLabelCategoryList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String keyWord, String access_token) {
        return this.labelCategoryService.selectCategoryList(pageNum, pageSize,keyWord);
    }


    @ApiOperation(value = "更新标签类别", notes = "更新标签类别")
    @RequestMapping(value = "/updateLabelCategoryById", method = RequestMethod.POST)
    public ResponseResult updateLabelCategoryById(LabelCategory labelCategory, String access_token) {
        if (StringUtils.isBlank(labelCategory.getCategoryName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABELCATEGORY_NAME_NULL.getCode(), ResponseCodeAccountEnum.LABELCATEGORY_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(labelCategory.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getDesc()));
        }
        return this.labelCategoryService.updateLabelCategory(labelCategory);
    }

/*    @ApiOperation(value = "删除指定标签类型", notes = "删除指定标签类型")
    @RequestMapping(value = "/deleteLabelCategoryById", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseResult deleteLabelById(String labelCategoryId, String access_token) {
        if (StringUtils.isBlank(labelCategoryId)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABELCATEGORY_ID_NULL.getCode(), ResponseCodeAccountEnum.LABELCATEGORY_ID_NULL.getDesc()));
        }
        return this.labelCategoryService.deleteLabelCategoryById(labelCategoryId);
    }*/

/*    @ApiOperation(value = "批量删除指定标签类型", notes = "批量删除指定标签类型")
    @RequestMapping(value = "/deleteLabelCategoryByIds", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseResult deleteLabelCategoryByIds(String[] labelCategoryIds, String access_token) {
        if (labelCategoryIds.length < 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABELCATEGORY_IDS_NULL.getCode(), ResponseCodeAccountEnum.LABELCATEGORY_IDS_NULL.getDesc()));
        }
        return this.labelCategoryService.deleteLabelCategoryByIds(labelCategoryIds);
    }



    @ApiOperation(value = "查看指定标签", notes = "查看指定标签")
    @RequestMapping(value = "/selectLabelCategoryById", method = RequestMethod.POST)
    public ResponseResult selectLabelCategoryById(String labelCategoryId, String access_token) {
        if (StringUtils.isBlank(labelCategoryId)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABELCATEGORY_ID_NULL.getCode(), ResponseCodeAccountEnum.LABELCATEGORY_ID_NULL.getDesc()));
        }
        return this.labelCategoryService.selectLabelCategoryById(labelCategoryId);
    }*/
}




