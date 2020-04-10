package com.lnmj.data.business.impl;


import com.lnmj.data.business.BackupsService;
import com.lnmj.data.repository.BackupsDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BackupsServicelmpl implements BackupsService {

    @Resource
    private BackupsDao backupsDao;

    @Override
    public void saveProductData(List list) {
        backupsDao.saveData(list);
    }

    @Override
    public void saveServiceProductData(List list) {
        backupsDao.saveServiceProdcutData(list);

    }

    @Override
    public void saveSupplierData(List list) {
        backupsDao.saveSupplierData(list);
    }

    @Override
    public void saveMember(Map map) {
        backupsDao.saveMember(map);
    }

    @Override
    public void saveMemberWallet(Map map2) {
        backupsDao.saveMemberWallet(map2);
    }
}
