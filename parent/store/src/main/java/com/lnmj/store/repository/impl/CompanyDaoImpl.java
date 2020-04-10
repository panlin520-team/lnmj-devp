package com.lnmj.store.repository.impl;


import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.ICompanyDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:15
 * @Description: 
 */
@Repository
public class CompanyDaoImpl extends BaseDao implements ICompanyDao {

    @Override
    public List<Company> selectCompanyList(Map map) {
        return super.selectList("Company.selectCompanyList",map);
    }

    @Override
    public Company selectCompanyByID(Long companyId) {
        return super.select("Company.selectCompanyByID",companyId);
    }

    @Override
    public int deleteCompanyByID(Company company) {
        return super.update("Company.deleteCompanyByID",company);
    }

    @Override
    public int insertCompany(Company company) {
        return super.insert("Company.insertCompany",company);
    }

    @Override
    public int updateCompanyByID(Company company) {
        return super.update("Company.updateCompanyByID",company);
    }

    @Override
    public List<Subsidiary> selectSubsidiaryList(Map map) {
        return super.selectList("Subsidiary.selectSubsidiaryList",map);
    }

    @Override
    public Subsidiary selectSubsidiaryByID(Long subsidiaryId) {
        return super.select("Subsidiary.selectSubsidiaryByID",subsidiaryId);
    }

    @Override
    public List<Subsidiary> selectSubsidiaryByParentID(Long parentCompany) {
        return super.selectList("Subsidiary.selectSubsidiaryByParentID",parentCompany);
    }

    @Override
    public int deleteSubsidiaryByID(Subsidiary subsidiary) {
        return super.update("Subsidiary.deleteSubsidiaryByID",subsidiary);
    }

    @Override
    public int insertSubsidiary(Subsidiary subsidiary) {
        return super.insert("Subsidiary.insertSubsidiary",subsidiary);
    }

    @Override
    public int updateSubsidiaryByID(Subsidiary subsidiary) {
        return super.update("Subsidiary.updateSubsidiaryByID",subsidiary);
    }

    @Override
    public List<BankAccount> selectBankAccountList(BankAccount bankAccount) {
        return super.selectList("bankAccount.selectBankAccountList",bankAccount);
    }

    @Override
    public BankAccount selectBankAccountById(BankAccount bankAccount) {
        return super.select("bankAccount.selectBankAccountById",bankAccount);
    }

    @Override
    public int addBankAccount(BankAccount bankAccount) {
        return super.insert("bankAccount.addBankAccount",bankAccount);
    }

    @Override
    public int chekBankAccountName(BankAccount bankAccount) {
        return super.select("bankAccount.chekBankAccountName",bankAccount);
    }

    @Override
    public int chekBankAccountNumber(BankAccount bankAccount) {
        return super.select("bankAccount.chekBankAccountNumber",bankAccount);
    }

    @Override
    public int updateBankAccount(BankAccount bankAccount) {
        return super.update("bankAccount.updateBankAccount",bankAccount);
    }

    @Override
    public int insertBankCashCompany(Map map) {
        return super.insert("bankAccountCompany.insertBankCashCompany",map);
    }

    @Override
    public List<BankAccountCompany> checkBankCashCompany(Map map) {
        return super.selectList("bankAccountCompany.selectBankAccountCompany",map);
    }

    @Override
    public int deleteBankCashCompany(Map map) {
        return super.delete("bankAccountCompany.deleteBankCashCompany",map);
    }

    @Override
    public int checkCompany(Company company) {
        return super.select("Company.checkCompany",company);
    }

    @Override
    public int checkSubCompany(Subsidiary subsidiary) {
        return super.select("Subsidiary.checkSubCompany",subsidiary);
    }
}
