package com.lnmj.wallet.repository;

import com.lnmj.wallet.entity.AccountUseRule;
import com.lnmj.wallet.entity.IndustryAccount;

import java.util.List;

public interface IAccountRuleDao {
   // ========================账号规则==========================
   List<AccountUseRule> selectAllAccountRuleList();
   List<AccountUseRule> selectAccountRuleByCondition(AccountUseRule accountUseRule);
   int insertAccountRule(AccountUseRule accountUseRule);
   int deleteAccountRuleById(AccountUseRule accountUseRule);
   int updateAccountRuleById(AccountUseRule accountUseRule);
    List<AccountUseRule> selectUseAccountRule(Long accountType);
   // ========================账号行业==========================
   List<IndustryAccount> selectAllRuleIndustryList();

   List<IndustryAccount> selectRuleIndustryByCondition(IndustryAccount industryAccount);

   int insertIndustryAccount(IndustryAccount industryAccount);

   int deleteIndustryAccountById(IndustryAccount industryAccount);

   int updateIndustryAccountById(IndustryAccount industryAccount);

   List<Long> updateIndustryAccountList(List<IndustryAccount> accountList);

   int deleteIndustryAccountByAccountRuleID(IndustryAccount industryAccount);

   List<Long> insertIndustryAccountList(List<IndustryAccount> accountList);
}
