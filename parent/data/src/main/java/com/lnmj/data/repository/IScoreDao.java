package com.lnmj.data.repository;

import com.lnmj.data.entity.Score;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IScoreDao {
    List<Score> selectList();

    List<Score> selectScoreByPostId(Map map);

    int addScore(Score score);

    int updateScore(Score score);

    int deleteScore(HashMap map);

//    Score selectByPostId(Long postId);

    Score selectScoreById(Long scoreID);

}
