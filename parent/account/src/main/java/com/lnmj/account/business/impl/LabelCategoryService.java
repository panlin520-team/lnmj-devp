package com.lnmj.account.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.ILabelCategoryService;
import com.lnmj.account.entity.LabelCategory;
import com.lnmj.account.repository.ILabelCategoryDao;
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
@Service("labelCategoryService")
@Transactional
public class LabelCategoryService implements ILabelCategoryService {
    @Resource
    private ILabelCategoryDao labelCategoryDao;


    @Override
    public boolean checkCategoryName(String categoryName) {
        return labelCategoryDao.checkCategoryName(categoryName);
    }

    @Override
    public ResponseResult selectCategoryList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap<>();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        List<LabelCategory> resultList = labelCategoryDao.selectCategoryList(map);
        if (resultList.size() > 0) {
            PageInfo<LabelCategory> pageInfo = new PageInfo(resultList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.GET_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.GET_LABELCATEGORY_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult addCategory(LabelCategory labelCategory) {
//        Long parentId = labelCategory.getParentId();
//        if (parentId == 0) {
//            labelCategory.setPath(+labelCategory.getLabelCategoryId() + ".");
//        } else {
//            LabelCategory result = labelCategoryDao.getCategoryByParentId(parentId);
//            labelCategory.setPath(result.getPath() + labelCategory.getLabelCategoryId());
//        }
        int result = labelCategoryDao.addCategory(labelCategory);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ADD_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.ADD_LABELCATEGORY_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult selectLabelCategoryById(String labelCategoryId) {
        LabelCategory result = labelCategoryDao.selectLabelCategoryById(labelCategoryId);
        if (result != null) {
            return ResponseResult.success(result);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.UPDATE_LABELCATEGORY_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult updateLabelCategory(LabelCategory labelCategory) {
        int result = labelCategoryDao.updateLabelCategory(labelCategory);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.UPDATE_LABELCATEGORY_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult deleteLabelCategoryById(String labelCategoryId) {
        int result = labelCategoryDao.deleteLabelCategoryById(labelCategoryId);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_LABELCATEGORY_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_LABELCATEGORY_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult deleteLabelCategoryByIds(String[] labelCategoryIds) {
        for (String labelCategoryId : labelCategoryIds) {
            int result = labelCategoryDao.deleteLabelCategoryById(labelCategoryId);
            if (result > 0) {
                return ResponseResult.success();
            }
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getDesc()));
    }

//    @Override
//    public ResponseResult selectLabelCategoryParent() {
//        List<LabelCategory> list = labelCategoryDao.selectLabelCategoryParent();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResponseResult.success(pageInfo);
//    }
}
