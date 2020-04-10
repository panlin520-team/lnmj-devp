package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.VO.UserStatisticsVO;
import com.lnmj.data.repository.IUserStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: panlin
 * @Date: 2019/5/8 16:17
 * @Description:
 */
@Repository
public class UserStatisticsDaolmpl extends BaseDao implements IUserStatisticsDao {

    @Override
    public List<UserStatisticsVO> selectList() {
        return super.selectList("account.selectList");
    }
}
