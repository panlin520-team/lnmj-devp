package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Customer;
import com.lnmj.store.entity.Department;
import org.springframework.stereotype.Service;


@Service("iCustomerService")
public interface ICustomerService {
    ResponseResult selectCustomerList(int pageNum, int pageSize, String keyWordName, String companyType, String companyId);
    ResponseResult selectCustomerByNumber(String  k3Number);
    ResponseResult selectCustomerListNoPage(String companyType, String companyId);
    ResponseResult insertCustomer(Customer customer);
    ResponseResult deleteCustomer(Customer customer);
    ResponseResult updateCustomer(Customer customer);
    ResponseResult selectDefaultCust(Customer customer);
    ResponseResult selectCustomerByConditon(Customer customer);

    ResponseResult batchAllocationSubCompany(String companyId,String companyType,String customerArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchAllocationStore(String companyId,String companyType,String customerArrayStr,String storeIdArrayStr);

    ResponseResult batchCancelAllocationSubCompany(String companyId,String companyType,String customerArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchCancelAllocationStore(String companyId,String companyType,String customerArrayStr,String storeIdArrayStr);
}
