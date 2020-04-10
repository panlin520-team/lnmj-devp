package com.lnmj.k3cloud.repository;

import com.lnmj.k3cloud.pojo.Incident;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/9 17:32
 * @Description: 事件接口
 */
public interface IIncidentDao {
    List<Incident> selectIncidentList(String orderByClause);

    Incident selectIncidentByID(Long incidentId);

    int deleteIncidentByID(Incident incident);

    int insertIncident(Incident incident);

    int updateIncidentByID(Incident incident);
}
