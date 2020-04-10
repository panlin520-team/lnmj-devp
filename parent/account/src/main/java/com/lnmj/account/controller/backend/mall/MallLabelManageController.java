package com.lnmj.account.controller.backend.mall;


import com.lnmj.account.business.ILabelService;
import com.lnmj.account.entity.Label;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: renqingyun
 * @Date: 2019/4/18 11:34
 * @Description: 标签管理
 */

@Api(description = "标签接口")
@RestController
@RequestMapping("/manage/mall/label")
public class MallLabelManageController {
    @Resource
    private ILabelService labelService;

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @RequestMapping(value = "/addLabel", method = RequestMethod.POST)
    public ResponseResult addLabel(Label label, String access_token) {
        if (StringUtils.isBlank(label.getLabelName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_NAME_NULL.getCode(), ResponseCodeAccountEnum.LABEL_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(label.getLabelDescribe())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_DESCRIBE_NULL.getCode(), ResponseCodeAccountEnum.LABEL_DESCRIBE_NULL.getDesc()));
        }
        if (label.getParentId() ==null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_PARENTID_ISNULL.getCode(), ResponseCodeAccountEnum.LABEL_PARENTID_ISNULL.getDesc()));
        }
        return this.labelService.addLabel(label);
    }

    @ApiOperation(value = "修改标签", notes = "修改标签")
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
    public ResponseResult updateLabel(Label label,String access_token) {
        if (StringUtils.isBlank(label.getLabelName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_NAME_NULL.getCode(), ResponseCodeAccountEnum.LABEL_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(label.getLabelDescribe())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_DESCRIBE_NULL.getCode(), ResponseCodeAccountEnum.LABEL_DESCRIBE_NULL.getDesc()));
        }
        if (StringUtils.isBlank(label.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return this.labelService.updateLabel(label);
    }

    @ApiOperation(value = "删除指定标签", notes = "删除指定标签")
    @RequestMapping(value = "/deleteLabelById", method = RequestMethod.POST)
    public ResponseResult deleteLabelById(String labelId, String modifyOperator,String access_token) {
        if (StringUtils.isBlank(labelId)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_ID_NULL.getCode(), ResponseCodeAccountEnum.LABEL_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(modifyOperator)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return this.labelService.deleteLabelById(labelId,modifyOperator);
    }

    @ApiOperation(value = "查询所有的标签", notes = "查询所有的标签")
    @RequestMapping(value = "/selectLabelList", method = RequestMethod.POST)
    public ResponseResult selectLabelList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord,Long labelCategoryId,String access_token) {
        return this.labelService.selectLabelList(pageNum, pageSize,keyWord,labelCategoryId);
    }






  /*  @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @RequestMapping(value = "/deleteLabelByIds", method = RequestMethod.POST)
    public ResponseResult deleteLabelByIds(@RequestBody String[] ids,String modifyOperator, String access_token) {
        if (ids.length < 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_NUMBER_NULL.getCode(), ResponseCodeAccountEnum.LABEL_NUMBER_NULL.getDesc()));
        }
        return this.labelService.deleteLabelByIds(ids,modifyOperator);
    }

    @ApiOperation(value = "根据id查询标签", notes = "根据id查询标签")
    @RequestMapping(value = "/selectById", method = RequestMethod.POST)
    public ResponseResult selectById(String labelId, String access_token) {
        if (labelId == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_ID_NULL.getCode(), ResponseCodeAccountEnum.LABEL_ID_NULL.getDesc()));
        }
        return this.labelService.selectById(labelId);
    }*/
}
