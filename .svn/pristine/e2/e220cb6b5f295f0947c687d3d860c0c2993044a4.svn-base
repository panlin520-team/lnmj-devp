package com.lnmj.wallet.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.AccountUseRule;
import org.springframework.stereotype.Service;

@Service("iRuleSettingService")
public interface IRuleSettingService {

    ResponseResult deleteAccountRuleById(Long accountUseRuleId, String modifyOperator);

    ResponseResult updateAccountRuleById(AccountUseRule accountUseRule);

    ResponseResult insertAccountRule(AccountUseRule accountUseRule);

    ResponseResult selectAccountRuleByCondition(int pageNum, int pageSize, AccountUseRule accountUseRule);

    ResponseResult selectAllAccountRuleList(int pageNum, int pageSize);

    ResponseResult selectUseAccountRule(Long accountType);

    ResponseResult selectRuleEnumName(String name);

}
