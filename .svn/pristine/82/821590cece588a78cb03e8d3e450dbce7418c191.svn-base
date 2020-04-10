package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.Score;

public interface IScoreService {
    ResponseResult selectList(int pageNum, int pageSize,String storeId);

    ResponseResult addScore(Score score);

    ResponseResult updateScore(Score score);

    ResponseResult deleteScore(Long scoreID, String modifyOperator);

    ResponseResult selectScoreById(Long scoreID);

    ResponseResult selectScoreMode();
}
