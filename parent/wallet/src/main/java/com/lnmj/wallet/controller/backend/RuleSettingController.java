package com.lnmj.wallet.controller.backend;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.AccountRuleEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IRuleSettingService;
import com.lnmj.wallet.entity.AccountUseRule;
import com.lnmj.wallet.entity.IndustryAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "规则设置接口")
@RestController
@RequestMapping("/manage/ruleSet")
public class RuleSettingController {
    @Resource
    private IRuleSettingService ruleSettingService;

    /**
     * @Description 显示所有enum内容
     * @Param [name]
     * @Return com.lnmj.account.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/4/25
     * @Time 12:30
     */
    @PreAuthorize("hasAuthority('sys:menu:show')")
    @ApiOperation(value = "显示所有enum内容", notes = "显示所有enum内容")
    @RequestMapping(value = "/selectRuleEnumName", method = RequestMethod.POST)
    public ResponseResult selectRuleEnumName(String name, String access_token) {
        //ENUM字段名字不能为空
        if (StringUtils.isBlank(name) || name == "") {
            return ResponseResult.error(new Error(AccountRuleEnum.ENUM_NAME_NULL.getCode(), AccountRuleEnum.ENUM_NAME_NULL.getDesc()));
        }
        if ("ProductTypeEnum".equals(name) || "UseMethodEnum".equals(name)
                || "ScopeOfUseEnum".equals(name) ||"ProductPriceTypeEnum".equals(name)) {
            return this.ruleSettingService.selectRuleEnumName(name);
        } else {      //ENUM字段名字错误
            return ResponseResult.error(new Error(AccountRuleEnum.ENUM_NAME_ERROR.getCode(), AccountRuleEnum.ENUM_NAME_ERROR.getDesc()));
        }
    }

    /**
     * @Description 查询所有账号规则
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "查询所有账号规则", notes = "查询所有账号规则")
    @RequestMapping(value = "/selectAllAccountRuleList", method = RequestMethod.POST)
    public ResponseResult selectAllAccountRuleList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String accessToken) {
        return ruleSettingService.selectAllAccountRuleList(pageNum,pageSize);
    }

    /**
     * @Description 按条件查询所有账号规则
     * @Param [pageNum, pageSize,accountUseRule, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "按条件查询所有账号规则", notes = "按条件查询所有账号规则")
    @RequestMapping(value = "/selectAccountRuleByCondition", method = RequestMethod.POST)
    public ResponseResult selectAccountRuleByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       AccountUseRule accountUseRule, String accessToken) {
        return ruleSettingService.selectAccountRuleByCondition(pageNum,pageSize,accountUseRule);
    }

    /**
     * @Description 设置规则
     * @Param [accountUseRule, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "设置规则", notes = "设置规则")
    @RequestMapping(value = "/insertAccountRule", method = RequestMethod.POST)
    public ResponseResult insertAccountRule(AccountUseRule accountUseRule, String accessToken) {
        //账号行业转换
        String industryJson = accountUseRule.getIndustryJson();
        if(StringUtils.isNotBlank(industryJson)){
            JSONArray array = JSONArray.parseArray(industryJson);
            List<IndustryAccount> accountList = array.toJavaList(IndustryAccount.class);
            accountUseRule.setAccountList(accountList);
        }
        return ruleSettingService.insertAccountRule(accountUseRule);
    }

    /**
     * @Description 修改规则
     * @Param [accountUseRule, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "修改规则", notes = "修改规则")
    @RequestMapping(value = "/updateAccountRuleById", method = RequestMethod.POST)
    public ResponseResult updateAccountRuleById(AccountUseRule accountUseRule, String accessToken) {
        if(accountUseRule.getAccountUseRuleId() == null){
            return ResponseResult.error(new Error(AccountRuleEnum.ACCOUNT_RULE_ID_NULL.getCode(),
                    AccountRuleEnum.ACCOUNT_RULE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(accountUseRule.getModifyOperator())){
            return ResponseResult.error(new Error(AccountRuleEnum.MODIFYOPERATOR_NULL.getCode(),
                    AccountRuleEnum.MODIFYOPERATOR_NULL.getDesc()));
        }
        //账号行业转换
        String industryJson = accountUseRule.getIndustryJson();
        if(StringUtils.isNotBlank(industryJson)){
            JSONArray array = JSONArray.parseArray(industryJson);
            List<IndustryAccount> accountList = array.toJavaList(IndustryAccount.class);
            accountUseRule.setAccountList(accountList);
        }
        return ruleSettingService.updateAccountRuleById(accountUseRule);
    }

    /**
     * @Description 删除规则
     * @Param [accountUseRuleId,modifyOperator, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "删除规则", notes = "删除规则")
    @RequestMapping(value = "/deleteAccountRuleById", method = RequestMethod.POST)
    public ResponseResult deleteAccountRuleById(Long accountUseRuleId, String modifyOperator, String accessToken) {
        if(accountUseRuleId == null){
            return ResponseResult.error(new Error(AccountRuleEnum.ACCOUNT_RULE_ID_NULL.getCode(),
                    AccountRuleEnum.ACCOUNT_RULE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(AccountRuleEnum.MODIFYOPERATOR_NULL.getCode(),
                    AccountRuleEnum.MODIFYOPERATOR_NULL.getDesc()));
        }
        return ruleSettingService.deleteAccountRuleById(accountUseRuleId,modifyOperator);
    }

    /**
     * @Description 查询账号类型在有效期内的规则
     * @Param [accountType, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author YiLihua
     * @Date 2019/8/18
     * @Time
     */
    @ApiOperation(value = "查询账号类型在有效期内的规则", notes = "查询账号类型在有效期内的规则")
    @RequestMapping(value = "/selectUseAccountRule", method = RequestMethod.POST)
    public ResponseResult selectUseAccountRule(Long accountType, String accessToken) {
        return ruleSettingService.selectUseAccountRule(accountType);
    }


}
