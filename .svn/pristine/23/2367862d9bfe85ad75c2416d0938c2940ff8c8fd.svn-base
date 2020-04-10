package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Score;
import com.lnmj.data.repository.IScoreDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Repository
public class IScoreDaoImpl extends BaseDao implements IScoreDao {
    @Override
    public List<Score> selectList() {
        return super.selectList("score.selectList");
    }

    @Override
    public List<Score> selectScoreByPostId(Map map) {
        return super.selectList("score.selectScoreByPostId",map);
    }

    @Override
    public int addScore(Score score) {
        return super.insert("score.addScore",score);
    }

    @Override
    public int updateScore(Score score) {
        return super.update("score.updateScore",score);
    }

    @Override
    public int deleteScore(HashMap map) {
        return super.update("score.deleteScore",map);
    }

//    @Override
//    public Score selectByPostId(Long postId) {
//        return super.select("score.selectByPostId",postId);
//    }
    @Override
    public Score selectScoreById(Long scoreID) {
        return super.select("score.selectScoreById",scoreID);
    }
}
