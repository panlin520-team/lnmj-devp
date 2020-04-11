package com.lnmj.data.repository;

import com.lnmj.data.entity.CustomMadeStatistics;

import java.util.List;
import java.util.Map;

public interface ICustomMadeStatisticsDao {
    List<CustomMadeStatistics> selectCustomMadeByList(Map map);
}
