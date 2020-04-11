package com.lnmj.account.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.IPosLabelService;
import com.lnmj.account.entity.Poslabel;
import com.lnmj.account.repository.ILabelDao;
import com.lnmj.account.repository.IPosLabelDao;
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
@Service("posLabelService")
@Transactional
public class PosLabelService implements IPosLabelService {
    @Resource
    private IPosLabelDao posLabelDao;

    @Override
    public ResponseResult selectLabelList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        List<Poslabel> labelList = posLabelDao.selectList(map);
        if (labelList.size() > 0) {
            PageInfo<Poslabel> pageInfo = new PageInfo(labelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.GET_LABEL_List_FAILUER.getCode(), ResponseCodeAccountEnum.GET_LABEL_List_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult addLabel(Poslabel posLabel) {
        int result = posLabelDao.insertPosLabel(posLabel);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ADD_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.ADD_LABEL_FAILUER.getDesc()));
    }


    @Override
    public ResponseResult updateLabel(Poslabel posLabel) {
        int result = posLabelDao.updatePosLabel(posLabel);
        if (result >= 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.UPDATE_LABEL_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult deleteLabelById(String labelId, String modifyOperator) {
        Map map = new HashMap();
        map.put("labelId", labelId);
        map.put("modifyOperator", modifyOperator);
        int result = posLabelDao.deletePosLabel(map);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getCode(), ResponseCodeAccountEnum.DELETE_LABEL_FAILUER.getDesc()));
    }

}
