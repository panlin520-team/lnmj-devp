package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.AccountUseRule;
import com.lnmj.wallet.entity.IndustryAccount;
import com.lnmj.wallet.repository.IAccountRuleDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccountRuleImpl extends BaseDao implements IAccountRuleDao {

    @Override
    public List<AccountUseRule> selectAllAccountRuleList() {
        return super.selectList("rule.selectAllAccountRuleList");
    }

    @Override
    public List<AccountUseRule> selectAccountRuleByCondition(AccountUseRule accountUseRule) {
        return super.selectList("rule.selectAccountRuleByCondition",accountUseRule);
    }

    @Override
    public int insertAccountRule(AccountUseRule accountUseRule) {
        return super.insert("rule.insertAccountRule",accountUseRule);
    }

    @Override
    public int deleteAccountRuleById(AccountUseRule accountUseRule) {
        return super.update("rule.deleteAccountRuleById",accountUseRule);
    }

    @Override
    public int updateAccountRuleById(AccountUseRule accountUseRule) {
        return super.update("rule.updateAccountRuleById",accountUseRule);
    }

    @Override
    public List<AccountUseRule> selectUseAccountRule(Long accountType) {
        return super.selectList("rule.selectUseAccountRule",accountType);
    }

    @Override
    public List<IndustryAccount> selectAllRuleIndustryList() {
        return super.selectList("IndustryAccount.selectAllRuleIndustryList");
    }

    @Override
    public List<IndustryAccount> selectRuleIndustryByCondition(IndustryAccount industryAccount) {
        return super.selectList("IndustryAccount.selectRuleIndustryByCondition",industryAccount);
    }

    @Override
    public int insertIndustryAccount(IndustryAccount industryAccount) {
        return super.insert("IndustryAccount.insertIndustryAccount",industryAccount);
    }

    @Override
    public int deleteIndustryAccountById(IndustryAccount industryAccount) {
        return super.update("IndustryAccount.deleteIndustryAccountById",industryAccount);
    }

    @Override
    public int updateIndustryAccountById(IndustryAccount industryAccount) {
        return super.update("IndustryAccount.updateIndustryAccountById",industryAccount);
    }

    @Override
    public List<Long> updateIndustryAccountList(List<IndustryAccount> accountList) {
        return super.updateList("IndustryAccount.updateIndustryAccountList",accountList);
    }

    @Override
    public int deleteIndustryAccountByAccountRuleID(IndustryAccount industryAccount) {
        return super.delete("IndustryAccount.deleteIndustryAccountByAccountRuleID",industryAccount);
    }

    @Override
    public List<Long> insertIndustryAccountList(List<IndustryAccount> accountList) {
        return super.insertList("IndustryAccount.insertIndustryAccountList",accountList);
    }
}
