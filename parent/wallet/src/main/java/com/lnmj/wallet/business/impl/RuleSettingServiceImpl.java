package com.lnmj.wallet.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductPriceTypeEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.AccountRuleEnum;
import com.lnmj.common.Enum.ScopeOfUseEnum;
import com.lnmj.common.Enum.UseMethodEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.wallet.business.IRuleSettingService;
import com.lnmj.wallet.entity.AccountUseRule;
import com.lnmj.wallet.entity.IndustryAccount;
import com.lnmj.wallet.repository.IAccountRuleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("ruleSettingServiceImpl")
public class RuleSettingServiceImpl implements IRuleSettingService {
    @Resource
    private IAccountRuleDao accountRuleDao;


    @Override
    public ResponseResult deleteAccountRuleById(Long accountUseRuleId, String modifyOperator) {
        //删除账号规则
        AccountUseRule accountUseRule = new AccountUseRule();
        accountUseRule.setAccountUseRuleId(accountUseRuleId);
        accountUseRule.setModifyOperator(modifyOperator);
        accountRuleDao.deleteAccountRuleById(accountUseRule);
        //删除账号规则行业
        IndustryAccount account = new IndustryAccount();
        account.setAccountUseRuleId(accountUseRuleId);
        account.setModifyOperator(modifyOperator);
        accountRuleDao.deleteIndustryAccountByAccountRuleID(account);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateAccountRuleById(AccountUseRule accountUseRule) {
        int i = accountRuleDao.updateAccountRuleById(accountUseRule);
        List<IndustryAccount> accountList = accountUseRule.getAccountList();
        if(accountList!=null && accountList.size()>0){
            //需要新加的对象
            List<IndustryAccount> insert = new ArrayList<>();
            //需要更新的对象
            List<IndustryAccount> update = new ArrayList<>();
            for (IndustryAccount industryAccount : accountList) {
                if(industryAccount.getIndustryAccountId()==null){
                    insert.add(industryAccount);
                }else{
                    update.add(industryAccount);
                }
            }
            if(insert!=null && insert.size()>0){
                accountRuleDao.insertIndustryAccountList(insert);
            }
            if(update!=null && update.size()>0){
                accountRuleDao.updateIndustryAccountList(update);
            }
        }
        return ResponseResult.success(accountUseRule);
    }

    @Override
    public ResponseResult insertAccountRule(AccountUseRule accountUseRule) {
        int i = accountRuleDao.insertAccountRule(accountUseRule);
        Long accountUseRuleId = accountUseRule.getAccountUseRuleId();
        List<IndustryAccount> accountList = accountUseRule.getAccountList();
        if(accountList != null && accountList.size() > 0){
            for (IndustryAccount industryAccount : accountList) {
                industryAccount.setAccountUseRuleId(accountUseRuleId);
            }
            //将行业账号规则写入数据库
            accountRuleDao.insertIndustryAccountList(accountList);
        }
        return ResponseResult.success(accountUseRule);
    }

    @Override
    public ResponseResult selectAccountRuleByCondition(int pageNum, int pageSize, AccountUseRule accountUseRule) {
        PageHelper.startPage(pageNum,pageSize);
        List<AccountUseRule> accountUseRuleList = accountRuleDao.selectAccountRuleByCondition(accountUseRule);
        if(accountUseRuleList!=null && accountUseRuleList.size()>0){
            //账号规则行业
            for (AccountUseRule useRule : accountUseRuleList) {
                Long accountUseRuleId = useRule.getAccountUseRuleId();
                IndustryAccount industryAccount = new IndustryAccount();
                industryAccount.setAccountUseRuleId(accountUseRuleId);
                List<IndustryAccount> industryAccountList = accountRuleDao.selectRuleIndustryByCondition(industryAccount);
                if(industryAccountList!=null && industryAccountList.size()>0){
                    useRule.setAccountList(industryAccountList);
                }
            }
            PageInfo<AccountUseRule> pageInfo = new PageInfo(accountUseRuleList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(AccountRuleEnum.ACCOUNT_RULE_NOT_FIND.getCode(),
                AccountRuleEnum.ACCOUNT_RULE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectAllAccountRuleList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AccountUseRule> accountUseRuleList = accountRuleDao.selectAllAccountRuleList();
        if(accountUseRuleList.size()>0){
            //账号规则行业
            for (AccountUseRule useRule : accountUseRuleList) {
                Long accountUseRuleId = useRule.getAccountUseRuleId();
                IndustryAccount industryAccount = new IndustryAccount();
                industryAccount.setAccountUseRuleId(accountUseRuleId);
                List<IndustryAccount> industryAccountList = accountRuleDao.selectRuleIndustryByCondition(industryAccount);
                if(industryAccountList!=null && industryAccountList.size()>0){
                    useRule.setAccountList(industryAccountList);
                }
            }
            PageInfo<AccountUseRule> pageInfo = new PageInfo(accountUseRuleList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(AccountRuleEnum.ACCOUNT_RULE_NOT_FIND.getCode(),
                AccountRuleEnum.ACCOUNT_RULE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectUseAccountRule(Long accountType) {
        List<AccountUseRule> accountUseRuleList = accountRuleDao.selectUseAccountRule(accountType);
        //账号规则行业
        for (AccountUseRule useRule : accountUseRuleList) {
            Long accountUseRuleId = useRule.getAccountUseRuleId();
            IndustryAccount industryAccount = new IndustryAccount();
            industryAccount.setAccountUseRuleId(accountUseRuleId);
            List<IndustryAccount> industryAccountList = accountRuleDao.selectRuleIndustryByCondition(industryAccount);
            if(industryAccountList!=null && industryAccountList.size()>0){
                useRule.setAccountList(industryAccountList);
            }
        }
        return  ResponseResult.success(accountUseRuleList);
    }

    @Override
    public ResponseResult selectRuleEnumName(String name) {
        if ("ProductTypeEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(ProductTypeEnum.class));
        }
        if ("UseMethodEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(UseMethodEnum.class));
        }
        if("ScopeOfUseEnum".equals(name)){
            return ResponseResult.success(MemberUtil.getEnumToMap(ScopeOfUseEnum.class));
        }
        if("ProductPriceTypeEnum".equals(name)){
            return ResponseResult.success(MemberUtil.getEnumToMap(ProductPriceTypeEnum.class));
        }
        return ResponseResult.error(new Error(AccountRuleEnum.ENUM_NAME_ERROR.getCode(), AccountRuleEnum.ENUM_NAME_ERROR.getDesc()));

    }

}
