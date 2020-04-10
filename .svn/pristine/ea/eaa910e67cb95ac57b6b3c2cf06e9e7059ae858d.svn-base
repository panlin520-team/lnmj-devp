package com.lnmj.account.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.ILabelService;
import com.lnmj.account.entity.Label;
import com.lnmj.account.repository.ILabelDao;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author renqingyun
 * @Date: 2019/4/29 15:27
 * @Description:
 */
@Service("labelService")
@Transactional
public class LabelService implements ILabelService {
    @Resource
    private ILabelDao labelDao;

    @Override
    public ResponseResult selectById(String labelId) {
        Label result = labelDao.selectByPrimaryKey(labelId);
        if (result == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.GET_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.GET_LABEL_FAILUER.getDesc()));
        }
        return ResponseResult.success(result);
    }


    @Override
    public ResponseResult selectLabelList(int pageNum, int pageSize, String keyWord, Long labelCategoryId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        if (labelCategoryId != null) {
            map.put("labelCategoryId", labelCategoryId);
        }
        List<Label> labelList = labelDao.selectAll(map);
        if (labelList.size() > 0) {
            PageInfo<Label> pageInfo = new PageInfo(labelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.GET_LABEL_List_FAILUER.getCode(), ResponseCodeAccountEnum.GET_LABEL_List_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult addLabel(Label label) {
        //判断标签名称
//        String labelName = label.getLabelName();
//        if (labelDao.checkLabelName(labelName) > 0) {
//            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_NAME_ISEXIST.getCode(), ResponseCodeAccountEnum.LABEL_NAME_ISEXIST.getDesc()));
//        } else {
            int result = labelDao.insertSelective(label);
            if (result > 0) {
                return ResponseResult.success();
            }
//        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ADD_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.ADD_LABEL_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult updateLabel(Label label) {
//        String labelName = label.getLabelName();
//        if (labelDao.checkLabelName(labelName) > 0) {
//            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LABEL_NAME_ISEXIST.getCode(), ResponseCodeAccountEnum.LABEL_NAME_ISEXIST.getDesc()));
//        } else {
            int result = labelDao.updateLabel(label);
            if (result >= 0) {
                return ResponseResult.success();
            }
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.UPDATE_LABEL_FAILUER.getDesc()));
//        }
    }

    @Override
    public ResponseResult deleteLabelById(String labelId, String modifyOperator) {
        Label label = new Label();
        label.setLabelId(Long.parseLong(labelId));
        label.setModifyOperator(modifyOperator);
        int result = labelDao.deleteLabelById(label);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult deleteLabelByIds(String[] ids, String modifyOperator) {
        Label label = new Label();
        for (String id : ids) {
            label.setLabelId(Long.parseLong(id));
            label.setModifyOperator(modifyOperator);
            int result = labelDao.deleteLabelById(label);
            if (result > 0) {
                return ResponseResult.success();
            }
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_LABELCATEGORY_FAILUER.getDesc()));
    }
}
