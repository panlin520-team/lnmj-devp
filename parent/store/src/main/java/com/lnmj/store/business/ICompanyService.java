package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.BankAccount;
import com.lnmj.store.entity.Company;
import com.lnmj.store.entity.Subsidiary;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 15:31
 * @Description: 公司service接口
 */
@Service("iCompanyService")
public interface ICompanyService {

    ResponseResult updateSubsidiaryByID(Subsidiary subsidiary);

    ResponseResult insertSubsidiary(Subsidiary subsidiary);

    ResponseResult insertHeSuanFanWei(Subsidiary subsidiary);

    ResponseResult insertZhangBu(String companyId, String companyType);

    ResponseResult deleteSubsidiaryByID(Long subsidiaryId,String modifyOperator);

    ResponseResult selectSubsidiary(String companyType, String companyId, String companyName);

    ResponseResult selectSubsidiaryByParentID(Long parentCompany);

    ResponseResult selectSubsidiaryByID(Long subsidiaryId);

    ResponseResult selectSubsidiaryList(int pageNum, int pageSize,String keyWordName,String companyId);

    ResponseResult updateCompanyByID(Company company);

    ResponseResult insertCompany(Company company);

    ResponseResult deleteCompanyByID(Long companyId,String modifyOperator);

    ResponseResult selectCompanyByID(Long companyId);

    ResponseResult selectCompanyList(int pageNum, int pageSize,String companyNameKeyword);

    ResponseResult updatePassword(Long companyId,String password,Integer adminType);

    ResponseResult selectCompanyListNoPage();

    ResponseResult selectCompanyAndSubListNoPage(String companyType,String companyId);

    ResponseResult createJiChuKongZhi(Long companyId);

    ResponseResult selectBankAccountList(int pageNum,int pageSize,BankAccount bankAccount);

    ResponseResult addBankAccount(BankAccount bankAccount);


    ResponseResult batchAllocationSubCompany(String companyType,String companyId,String id,String accountType,String subcompanyIdArrayStr);

    ResponseResult batchAllocationStore(String companyType,String companyId,String id,String accountType,String storeIdArrayStr);

    ResponseResult batchCancelAllocationSubCompany(String companyType,String companyId,String id,String subcompanyIdArrayStr);

    ResponseResult batchCancelAllocationStore(String companyType,String companyId,String id,String storeIdArrayStr);


}
