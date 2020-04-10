package com.lnmj.data.repository;

import com.lnmj.data.entity.VO.UserStatisticsVO;

import java.util.List;

public interface IUserStatisticsDao {

     List<UserStatisticsVO> selectList();
}
