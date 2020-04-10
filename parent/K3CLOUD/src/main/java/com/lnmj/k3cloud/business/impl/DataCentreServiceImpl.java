package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IDataCentreService;
import com.lnmj.k3cloud.business.IK3CLOUDService;
import com.lnmj.k3cloud.entity.DataCentre;
import com.lnmj.k3cloud.entity.Organization;
import com.lnmj.k3cloud.entity.login.Login;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.lnmj.k3cloud.serviceProxy.StoreApi;
import com.lnmj.k3cloud.util.K3cloudConfig;
import com.lnmj.k3cloud.util.MyHttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dataCentreServiceImpl")
public class DataCentreServiceImpl implements IDataCentreService {
    @Resource
    private IK3CLOUDDao ik3CLOUDDao;
    @Resource
    private StoreApi storeApi;

    @Override
    public ResponseResult selectK3DataCentreList(int pageNum, int pageSize,String dataCentreName) {
        PageInfo<DataCentre> dataCentreList = ik3CLOUDDao.selectK3DataCentreList(pageNum, pageSize,dataCentreName);
        List<Map> mapListCompany = (List<Map>) storeApi.selectCompanyListNoPage().getResult();
        List<DataCentre> dataCentres = dataCentreList.getList();

        if (mapListCompany != null&&mapListCompany.size()!=0){
            for (DataCentre dataCentre : dataCentres) {
                for (Map map : mapListCompany) {
                    if (dataCentre.getFDATACENTERID().equals(map.get("dataCentre").toString())) {
                        dataCentre.setIsUsed(1);
                        dataCentre.setUseOrg(map.get("companyName").toString());
                        continue;
                    } else {
                        dataCentre.setIsUsed(0);
                    }
                }
            }
        }else{
            for (DataCentre dataCentre : dataCentres) {
                dataCentre.setIsUsed(0);
            }
        }
        PageInfo pageInfo = new PageInfo(dataCentres);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectK3LegalPersonNumber(String dataCentreName) {
        Organization organization = ik3CLOUDDao.selectK3LegalPersonNumber(dataCentreName);
        return ResponseResult.success(organization);
    }

}
