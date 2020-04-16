package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.VO.AmountTyp;
import com.lnmj.data.entity.VO.StoreVO;
import com.lnmj.data.entity.VO.WalletAmount;
import com.lnmj.data.repository.BackupsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class BackupsBDaoImpl extends BaseDao implements BackupsDao {


    @Override
    public void saveSupplierData(List list) {
        super.insert("backups.addSupplier", list);
    }

    @Override
    public void saveMember(Map map) {
        super.insert("backups.saveMember", map);
    }

    @Override
    public void saveServiceProdcutData(List list) {
        super.insert("backups.addServiceProduct", list);
    }

    @Override
    public void saveData(List productList) {
        super.insert("backups.insert", productList);
    }

    @Override
    public void saveMemberWallet(Map map2) {
        super.insert("backups.saveMemberWallet", map2);
    }

    @Override
    public void updateAmount(List list) {
       super.insert("backups.updateAmount",list);
    }

    @Override
    public List<AmountTyp> selectAmountType() {
        return super.selectList("backups.selectAmountType");
    }

    @Override
    public List<StoreVO> selectStores() {
        return super.selectList("backups.selectStores");
    }
}
