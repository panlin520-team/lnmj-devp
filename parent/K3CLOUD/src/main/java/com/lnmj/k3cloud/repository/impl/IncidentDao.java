package com.lnmj.k3cloud.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/10 09:47
 * @Description: 事件接口实现
 */
@Repository
public class IncidentDao extends BaseDao implements IIncidentDao {


    @Override
    public List<Incident> selectIncidentList(String orderByClause) {
        return super.selectList("Incident.selectIncidentList",orderByClause);
    }

    @Override
    public Incident selectIncidentByID(Long incidentId) {
        return super.select("Incident.selectIncidentByID",incidentId);
    }

    @Override
    public int deleteIncidentByID(Incident incident) {
        return super.update("Incident.deleteIncidentByID",incident);
    }

    @Override
    public int insertIncident(Incident incident) {
        return super.insert("Incident.insertIncident",incident);
    }

    @Override
    public int updateIncidentByID(Incident incident) {
        return super.update("Incident.updateIncidentByID",incident);
    }
}
