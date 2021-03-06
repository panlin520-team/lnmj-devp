package com.lnmj.k3cloud.repository;

import com.github.pagehelper.PageInfo;
import com.lnmj.k3cloud.entity.DataCentre;
import com.lnmj.k3cloud.entity.Organization;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019-11-08 15:07
 * @Description:  金蝶dao
 */
public interface IK3CLOUDDao {
    PageInfo<DataCentre> selectK3DataCentreList(int pageNum, int pageSize,String dataCentreName);
    Organization selectK3LegalPersonNumber(String dataCentreName);
    Boolean orgToUser(String dataCentreName,String userId,String orgId);
    Boolean startStock(String dataCentreName, String orgId, String startDate);
    Boolean updateOrgFunctions(String dataCentreName,String orgId);
}
