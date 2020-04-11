package com.lnmj.account.controller.backend.pos;


import com.lnmj.account.business.ILabelService;
import com.lnmj.account.business.IPosLabelService;
import com.lnmj.account.entity.Poslabel;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
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
 * @Date: 2019/4/18 11:34
 * @Description: 门店标签管理
 */

@Api(description = "门店标签接口")
@RestController
@RequestMapping("/manage/pos/posLabel")
public class PosLabelManageController {
    @Resource
    private IPosLabelService posLabelService;

    @ApiOperation(value = "查询所有的标签", notes = "查询所有的标签")
    @RequestMapping(value = "/selectLabelList", method = RequestMethod.POST)
    public ResponseResult selectLabelList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord,Long labelCategoryId,String access_token) {
        return this.posLabelService.selectLabelList(pageNum, pageSize,keyWord);
    }

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @RequestMapping(value = "/addPosLabel", method = RequestMethod.POST)
    public ResponseResult addLabel(Poslabel posLabel, String access_token) {
        return this.posLabelService.addLabel(posLabel);
    }

    @ApiOperation(value = "修改标签", notes = "修改标签")
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
    public ResponseResult updateLabel(Poslabel posLabel,String access_token) {
        return this.posLabelService.updateLabel(posLabel);
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
        return this.posLabelService.deleteLabelById(labelId,modifyOperator);
    }


}
