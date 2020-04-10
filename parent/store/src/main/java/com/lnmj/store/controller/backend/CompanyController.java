package com.lnmj.store.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICompanyService;
import com.lnmj.store.entity.BankAccount;
import com.lnmj.store.entity.Company;
import com.lnmj.store.entity.Subsidiary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Api(description = "公司管理")
@RestController
@RequestMapping("/manage/company")
public class CompanyController {
    @Resource
    private ICompanyService companyService;
    
    /**
    *@Description 查询总公司
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/19
    *@Time 18:25
    */
    @ApiOperation(value = "查询总公司",notes = "查询总公司")
    @RequestMapping(value = "/selectCompanyList",method = RequestMethod.POST)
    public ResponseResult selectCompanyList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String companyNameKeyword){
        return companyService.selectCompanyList(pageNum,pageSize,companyNameKeyword);
    }


    @ApiOperation(value = "修改总公司k3管理员密码",notes = "修改总公司k3管理员密码")
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public ResponseResult updatePassword(Long companyId,String password,Integer adminType){
        return companyService.updatePassword(companyId,password,adminType);
    }

    /**
     *@Description 查询总公司
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/19
     *@Time 18:25
     */
    @ApiOperation(value = "查询总公司不分页",notes = "查询总公司不分页")
    @RequestMapping(value = "/selectCompanyListNoPage",method = RequestMethod.POST)
    public ResponseResult selectCompanyListNoPage(){
        return companyService.selectCompanyListNoPage( );
    }

    /**
     *@Description 查询总公司及其下面的子公司
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/19
     *@Time 18:25
     */
    @ApiOperation(value = "查询总公司及其下面的子公司",notes = "查询总公司不分页")
    @RequestMapping(value = "/selectCompanyAndSubListNoPage",method = RequestMethod.POST)
    public ResponseResult selectCompanyAndSubListNoPage(String companyType,String companyId){
        return companyService.selectCompanyAndSubListNoPage(companyType,companyId);
    }

    /**
    *@Description 根据ID查询公司
    *@Param [companyId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/19
    *@Time 18:31
    */
    @ApiOperation(value = "根据ID查询公司",notes = "根据ID查询公司")
    @RequestMapping(value = "/selectCompanyByID",method = RequestMethod.POST)
    public ResponseResult selectCompanyByID(Long companyId, String access_token){
        if(companyId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_ID_NULL.getDesc()));
        }
        return companyService.selectCompanyByID(companyId);
    }
    
    /**
    *@Description 根据ID删除公司
    *@Param [companyId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/20
    *@Time 14:34
    */
    @ApiOperation(value = "根据ID删除公司",notes = "根据ID删除公司")
    @RequestMapping(value = "/deleteCompanyByID",method = RequestMethod.POST)
    public ResponseResult deleteCompanyByID(Long companyId,String modifyOperator, String access_token){
        if(companyId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getCode(),
                    ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return companyService.deleteCompanyByID(companyId,modifyOperator);
    }

    /**
    *@Description 新增公司
    *@Param [company, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/20
    *@Time 14:34
    */
    @ApiOperation(value = "新增公司",notes = "新增公司")
    @RequestMapping(value = "/insertCompany",method = RequestMethod.POST)
    public ResponseResult insertCompany(Company company, String access_token){
        return companyService.insertCompany(company);
    }

    /**
    *@Description 修改公司信息
    *@Param [company, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/20
    *@Time 14:35
    */
    @ApiOperation(value = "修改公司信息",notes = "修改公司信息")
    @RequestMapping(value = "/updateCompanyByID",method = RequestMethod.POST)
    public ResponseResult updateCompanyByID(Company company, String access_token){
        if(company.getCompanyId()==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(company.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getCode(),
                    ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return companyService.updateCompanyByID(company);
    }

    /**
     *@Description 查询子公司
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/19
     *@Time 18:25
     */
    @ApiOperation(value = "查询子公司",notes = "查询子公司")
    @RequestMapping(value = "/selectSubsidiaryList",method = RequestMethod.POST)
    public ResponseResult selectSubsidiaryList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String keyWordName,String companyId){
        return companyService.selectSubsidiaryList(pageNum,pageSize,keyWordName,companyId);
    }

    /**
     *@Description 根据ID查询子公司
     *@Param [subsidiaryId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/19
     *@Time 18:31
     */
    @ApiOperation(value = "根据ID查询子公司",notes = "根据ID查询子公司")
    @RequestMapping(value = "/selectSubsidiaryByID",method = RequestMethod.POST)
    public ResponseResult selectSubsidiaryByID(Long subsidiaryId, String access_token){
        if(subsidiaryId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getDesc()));
        }
        return companyService.selectSubsidiaryByID(subsidiaryId);
    }

    /**
    *@Description 根据总公司ID查询子公司
    *@Param [parentCompany, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/20
    *@Time 14:39
    */
    @ApiOperation(value = "根据查询子公司",notes = "根据查询子公司")
    @RequestMapping(value = "/selectSubsidiary",method = RequestMethod.POST)
    public ResponseResult selectSubsidiary(String companyType,String companyId,String companyName){

        return companyService.selectSubsidiary(companyType,companyId,companyName);
    }

    /**
     *@Description 根据ID删除子公司
     *@Param [subsidiaryId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/20
     *@Time 14:34
     */
    @ApiOperation(value = "根据ID删除子公司",notes = "根据ID删除子公司")
    @RequestMapping(value = "/deleteSubsidiaryByID",method = RequestMethod.POST)
    public ResponseResult deleteSubsidiaryByID(Long subsidiaryId,String modifyOperator, String access_token){
        if(subsidiaryId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getCode(),
                    ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return companyService.deleteSubsidiaryByID(subsidiaryId,modifyOperator);
    }

    /**
     *@Description 新增子公司
     *@Param [subsidiary, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/20
     *@Time 14:34
     */
    @ApiOperation(value = "新增子公司",notes = "新增子公司")
    @RequestMapping(value = "/insertSubsidiary",method = RequestMethod.POST)
    public ResponseResult insertSubsidiary(Subsidiary subsidiary, String access_token){
        return companyService.insertSubsidiary(subsidiary);
    }

    @ApiOperation(value = "生成子公司核算范围",notes = "生成子公司核算范围")
    @RequestMapping(value = "/insertHeSuanFanWei",method = RequestMethod.POST)
    public ResponseResult insertHeSuanFanWei(Subsidiary subsidiary, String access_token){
        return companyService.insertHeSuanFanWei(subsidiary);
    }

    @ApiOperation(value = "生成公司账簿",notes = "生成子公司账簿")
    @RequestMapping(value = "/insertZhangBu",method = RequestMethod.POST)
    public ResponseResult insertZhangBu(String companyId, String companyType){
        return companyService.insertZhangBu(companyId,companyType);
    }

    /**
     *@Description 修改子公司信息
     *@Param [subsidiary, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/9/20
     *@Time 14:35
     */
    @ApiOperation(value = "修改子公司信息",notes = "修改子公司信息")
    @RequestMapping(value = "/updateSubsidiaryByID",method = RequestMethod.POST)
    public ResponseResult updateSubsidiaryByID(Subsidiary subsidiary, String access_token){
        if(subsidiary.getSubsidiaryId()==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(subsidiary.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getCode(),
                    ResponseCodeCompanyEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }

        return companyService.updateSubsidiaryByID(subsidiary);
    }

    @ApiOperation(value = "生成基础资料控制策略",notes = "生成基础资料控制策略")
    @RequestMapping(value = "/createJiChuKongZhi",method = RequestMethod.POST)
    public ResponseResult createJiChuKongZhi(Long companyId){
        return companyService.createJiChuKongZhi(companyId);
    }


    @ApiOperation(value = "查看总公司银行账号",notes = "查看总公司银行账号")
    @RequestMapping(value = "/selectBankAccountList",method = RequestMethod.POST)
    public ResponseResult selectBankAccountList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,BankAccount bankAccount){
        return companyService.selectBankAccountList(pageNum,pageSize,bankAccount);
    }

    @ApiOperation(value = "添加总公司银行账号",notes = "查看总公司银行账号")
    @RequestMapping(value = "/addBankAccount",method = RequestMethod.POST)
    public ResponseResult addBankAccount(BankAccount bankAccount){
        return companyService.addBankAccount(bankAccount);
    }

    @ApiOperation(value = "批量分配账号-子公司", notes = "批量分配供应商-子公司")
    @RequestMapping(value = "/batchAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchAllocationSubCompany(String companyType,String companyId,String id,String accountType,String subcompanyIdArrayStr) {
        return companyService.batchAllocationSubCompany(companyType,companyId,id,accountType,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量分配账号-分公司", notes = "批量分配供应商-分公司")
    @RequestMapping(value = "/batchAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchAllocationStore(String companyType,String companyId,String id,String accountType,String storeIdArrayStr) {
        return companyService.batchAllocationStore(companyType,companyId,id,accountType,storeIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配账号-子公司", notes = "批量取消分配供应商-子公司")
    @RequestMapping(value = "/batchCancelAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationSubCompany(String companyType,String companyId,String id,String subcompanyIdArrayStr) {
        return companyService.batchCancelAllocationSubCompany(companyType,companyId,id,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配账号-分公司", notes = "批量取消分配供应商-分公司")
    @RequestMapping(value = "/batchCancelAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationStore(String companyType,String companyId,String id,String storeIdArrayStr) {
        return companyService.batchCancelAllocationStore(companyType,companyId,id,storeIdArrayStr);
    }


}
