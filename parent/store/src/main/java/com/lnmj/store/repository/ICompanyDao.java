package com.lnmj.store.repository;

import com.lnmj.store.entity.*;

import java.util.List;
import java.util.Map;

public interface ICompanyDao {
    List<Company> selectCompanyList(Map map);

    Company selectCompanyByID(Long companyId);

    int deleteCompanyByID(Company company);

    int insertCompany(Company company);

    int updateCompanyByID(Company company);

    List<Subsidiary> selectSubsidiaryList(Map map);

    Subsidiary selectSubsidiaryByID(Long subsidiaryId);

    List<Subsidiary> selectSubsidiaryByParentID(Long parentCompany);

    int deleteSubsidiaryByID(Subsidiary subsidiary);

    int insertSubsidiary(Subsidiary subsidiary);

    int updateSubsidiaryByID(Subsidiary subsidiary);

    List<BankAccount> selectBankAccountList(BankAccount bankAccount);

    BankAccount selectBankAccountById(BankAccount bankAccount);

    int addBankAccount(BankAccount bankAccount);

    int chekBankAccountName(BankAccount bankAccount);

    int chekBankAccountNumber(BankAccount bankAccount);

    int updateBankAccount(BankAccount bankAccount);

    int insertBankCashCompany(Map map);

    List<BankAccountCompany> checkBankCashCompany(Map map);

    int deleteBankCashCompany(Map map);

    int checkCompany(Company company);

    int checkSubCompany(Subsidiary subsidiary);
}
