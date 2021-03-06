package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCustomerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICustomerService;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.ICompanyDao;
import com.lnmj.store.repository.ICustomerDao;
import com.lnmj.store.repository.IStoreDao;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.ProductApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Transactional
@Service("customerService")
public class CustomerService implements ICustomerService {
    @Resource
    private ICustomerDao iCustomerDao;
    @Resource
    private ICompanyDao iCompanyDao;
    @Resource
    private IStoreDao iStoreDao;
    @Resource
    private K3Api_organization k3Api_organization;
    @Resource
    private ProductApi productApi;


    @Override
    public ResponseResult selectCustomerList(int pageNum, int pageSize, String keyWordName, String companyType, String companyId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("companyType", companyType);
        map.put("keyWordName", keyWordName);
        List<Customer> customerList = new ArrayList<>();
        if (companyType.equals("1")) {
            map.clear();
            map.put("zhongCompanyId",companyId);
            customerList = iCustomerDao.selectCustomerList(map);
        } else if (companyType.equals("2")) {
            customerList = iCustomerDao.selectCustomerListSubCompany(map);
        } else if (companyType.equals("3")) {
            customerList = iCustomerDao.selectCustomerListStore(map);
        }
        if (customerList.size() > 0) {
            PageInfo pageInfo = new PageInfo(customerList);
            return ResponseResult.success(pageInfo);
        }


        return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_LIST_NULL.getCode(),
                ResponseCustomerEnum.CUSTOMER_LIST_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectCustomerByNumber(String k3Number) {
        Customer customer = iCustomerDao.selectCustomerByNumber(k3Number);
        return ResponseResult.success(customer);
    }

    @Override
    public ResponseResult selectCustomerListNoPage(String companyType, String companyId) {

        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("companyType", companyType);
        List<Customer> customerList = iCustomerDao.selectCustomerList(map);
        List<Customer> customerListResult = new ArrayList<>();
        if (companyType != null) {
            if (companyType.equals("2")) {
                List<CustomerSubCompany> customerSubCompanyList = iCustomerDao.selectCustomerSubCompany(map);
                for (CustomerSubCompany customerSubCompany : customerSubCompanyList) {
                    for (Customer customer : customerList) {
                        if (customerSubCompany.getCustomerId().toString().equals(customer.getId().toString())) {
                            customerListResult.add(customer);
                        }
                    }

                }
            } else if (companyType.equals("3")) {
                List<CustomerStore> customerStoreList = iCustomerDao.selectCustomerStore(map);
                for (CustomerStore customerStore : customerStoreList) {
                    for (Customer customer : customerList) {
                        if (customerStore.getCustomerId().toString().equals(customer.getId().toString())) {
                            customerListResult.add(customer);
                        }
                    }

                }
            }
        }


        if (companyType == null) {
            if (customerList.size() > 0) {
                return ResponseResult.success(customerList);
            }
        } else {
            if (customerListResult.size() > 0) {
                return ResponseResult.success(customerListResult);
            }
        }


        return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_LIST_NULL.getCode(),
                ResponseCustomerEnum.CUSTOMER_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertCustomer(Customer customer) {
        //查看客户名字是否重复
        int resultIntName = iCustomerDao.checkCustName(customer);
        if (resultIntName>0){
            return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_NAME_EXIT.getCode(),
                    ResponseCustomerEnum.CUSTOMER_NAME_EXIT.getDesc()));
        }

        if (customer.getIsDefaultCust().toString().equals("1")) {
            //查看是否有默认
            int resultInt = iCustomerDao.checkDefault(customer);
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_ADD_FAIL_DEFUALT_EXIT.getCode(),
                        ResponseCustomerEnum.CUSTOMER_ADD_FAIL_DEFUALT_EXIT.getDesc()));
            }
        }


        //生成客户编码
        String k3NumberLast = iCustomerDao.selectLastCustomerCode();
        String k3NumberAdd = null;
        if (k3NumberLast == null) {
            customer.setK3Number("CUST" + "0001");
        } else {
            String substring = k3NumberLast.substring(4, 8);
            int i = Integer.parseInt(substring);
            k3NumberAdd = "CUST" + String.format("%4d", i + 1).replace(" ", "0");
            customer.setK3Number(k3NumberAdd);

        }

        //添加到k3
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (customer.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(customer.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(customer.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(customer.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        String fCustTypeId = null;
        if (customer.getCustomerType() == 1) {
            fCustTypeId = "KHLB001_SYS";
        } else if (customer.getCustomerType() == 2) {
            fCustTypeId = "KHLB002_SYS";
        } else if (customer.getCustomerType() == 3) {
            fCustTypeId = "KHLB003_SYS";
        }

        Company company = null;
        Subsidiary subsidiary = null;
        if (customer.getCompanyType().equals("1")) {
            company = iCompanyDao.selectCompanyByID(Long.parseLong(customer.getCompanyId()));
        } else if (customer.getCompanyType().equals("2")) {
            subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(customer.getCompanyId()));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }
        ResponseResult responseResult = k3Api_organization.customerSave(dataCentre, k3UserName, k3PassWord, customer.getName(), customer.getK3Number(), customer.getName(), company.getK3Number(), company.getK3Number(), fCustTypeId, null, null);

        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户添加失败,K3客户添加未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        String k3Id = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Id").toString();
        String number = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Number").toString();
        customer.setK3Id(k3Id);
        customer.setK3Number(number);
        iCustomerDao.insertCustomer(customer);

        if (customer.getCompanyType().equals("2")) {
            //建立客户和当前组织的关系
            Map map = new HashMap();
            map.put("customerId", customer.getId());
            map.put("subCompanyId", customer.getCompanyId());
            iCustomerDao.addCustomerSubCompany(map);
            //同时分配给当前组织
            k3Api_organization.allocateCustomer(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    k3Id,
                    subsidiary.getK3Id(),
                    true
            );
            return ResponseResult.success("客户添加成功，并分配到当前组织");
        }
        return ResponseResult.success("客户添加成功，k3添加成功");
    }

    @Override
    public ResponseResult deleteCustomer(Customer customer) {
        //判断客户是否正在被使用
        List<Map> outstorageListMap = (List<Map>) (productApi.checkOutstorageByCust(customer.getK3Number()).getResult());
        if (outstorageListMap != null && outstorageListMap.size() > 0) {
            return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_OUTSTORAGE_USE.getCode(),
                    ResponseCustomerEnum.CUSTOMER_OUTSTORAGE_USE.getDesc()));
        }

        int resultInt = iCustomerDao.deleteCustomer(customer);
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (customer.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(customer.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(customer.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(customer.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        ResponseResult responseResult = k3Api_organization.customerDelete(dataCentre, k3UserName, k3PassWord, null, customer.getK3Id());
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户删除成功,K3客户删除未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        if (resultInt > 0) {
            return ResponseResult.success("客户删除成功,K3客户删除成功");
        } else {
            return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_DELETE_FAIL.getCode(),
                    ResponseCustomerEnum.CUSTOMER_DELETE_FAIL.getDesc()));

        }
    }

    @Override
    public ResponseResult updateCustomer(Customer customer) {
        int resultInt = iCustomerDao.updateCustomer(customer);
        //K3接入
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (customer.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(customer.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(customer.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (customer.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(customer.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        ResponseResult responseResult = k3Api_organization.customerSave(dataCentre, k3UserName, k3PassWord, customer.getName(), "", "", "", "", "", "FName", Integer.parseInt(customer.getK3Id()));
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户修改成功,K3客户修改未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (resultInt > 0) {
            return ResponseResult.success("客户修改成功,K3客户修改成功");
        } else {
            return ResponseResult.error(new Error(ResponseCustomerEnum.CUSTOMER_UPDATE_FAIL.getCode(),
                    ResponseCustomerEnum.CUSTOMER_UPDATE_FAIL.getDesc()));

        }
    }

    @Override
    public ResponseResult selectDefaultCust(Customer customer) {
        Customer customerResult = iCustomerDao.selectDefaultCust(customer);
        return ResponseResult.success(customerResult);
    }

    @Override
    public ResponseResult selectCustomerByConditon(Customer customer) {
        Customer customerResult = iCustomerDao.selectCustomerByConditon(customer);
        return ResponseResult.success(customerResult);
    }


    @Override
    public ResponseResult batchAllocationSubCompany(String companyId, String companyType, String customerArrayStr, String subcompanyIdArrayStr) {
        //本系统客户分配
        JSONArray customerArrary = JSON.parseArray(customerArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            CustomerSubCompany customerSubCompany = new CustomerSubCompany();
            for (int j = 0; j < customerArrary.size(); j++) {
                List<CustomerSubCompany> customerSubCompanylistAdd = new ArrayList<>();
                customerSubCompany.setCustomerId(customerArrary.getJSONObject(j).getLong("customerId"));
                customerSubCompany.setSubCompanyId(subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("customerId", customerArrary.getJSONObject(j).getString("customerId"));
                mapCheck.put("subCompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                List<CustomerSubCompany> customerSubCompanyList = iCustomerDao.checkCustomerSubCompany(mapCheck);
                if (customerSubCompanyList != null && customerSubCompanyList.size() > 0) {
                    Map mapResult = new HashMap();
                    mapResult.put("客户名称", customerArrary.getJSONObject(j).getString("customerName"));
                    mapResult.put("组织名称", subcompanyIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    customerSubCompanylistAdd.add(customerSubCompany);
                    Map map = new HashMap();
                    map.put("list", customerSubCompanylistAdd);
                    iCustomerDao.addCustomerSubCompany(map);
                }
            }
        }


        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < customerArrary.size(); i++) {
            PkIds = PkIds + "," + customerArrary.getJSONObject(i).getString("customerK3Id");
        }
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + subcompanyIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Company company = null;
        if (companyType.equals("1")) {
            //如果是总公司
            company = iCompanyDao.selectCompanyByID(Long.parseLong(companyId));
        } else if (companyType.equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(companyId));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }


        ResponseResult responseResult = k3Api_organization.allocateCustomer(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                PkIds,
                TOrgIds,
                true
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("客户分配成功,K3客户未分配，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下客户已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }

    }

    @Override
    public ResponseResult batchAllocationStore(String companyId, String companyType, String customerArrayStr, String storeIdArrayStr) {
        //本系统客户分配
        JSONArray customerArrary = JSON.parseArray(customerArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);

        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < storeIdArray.size(); i++) {
            CustomerStore customerStore = new CustomerStore();
            for (int j = 0; j < customerArrary.size(); j++) {
                List<CustomerStore> customerStorelistAdd = new ArrayList<>();
                customerStore.setCustomerId(customerArrary.getJSONObject(j).getLong("customerId"));
                customerStore.setStoreId(storeIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("customerId", customerArrary.getJSONObject(j).getString("customerId"));
                mapCheck.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                List<CustomerStore> customerStoreList = iCustomerDao.checkCustomerStore(mapCheck);
                if (customerStoreList != null && customerStoreList.size() > 0) {
                    Map mapResult = new HashMap();
                    mapResult.put("客户名称", customerArrary.getJSONObject(j).getString("customerName"));
                    mapResult.put("组织名称", storeIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    customerStorelistAdd.add(customerStore);
                    Map map = new HashMap();
                    map.put("list", customerStorelistAdd);
                    iCustomerDao.addCustomerStore(map);
                }
            }
        }
        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < customerArrary.size(); i++) {
            PkIds = PkIds + "," + customerArrary.getJSONObject(i).getString("customerK3Id");
        }
        for (int i = 0; i < storeIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + storeIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Company company = null;
        if (companyType.equals("1")) {
            //如果是总公司
            company = iCompanyDao.selectCompanyByID(Long.parseLong(companyId));
        } else if (companyType.equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(companyId));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }


        ResponseResult responseResult = k3Api_organization.allocateCustomer(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                PkIds,
                TOrgIds,
                true
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("客户分配成功,K3客户分配未生成，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下客户已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }
    }

    @Override
    public ResponseResult batchCancelAllocationSubCompany(String companyId, String companyType, String customerArrayStr, String subcompanyIdArrayStr) {
        //本系统商品取消分配
        JSONArray customerArrary = JSON.parseArray(customerArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            for (int j = 0; j < customerArrary.size(); j++) {
                Map map = new HashMap();
                map.put("customerId", customerArrary.getJSONObject(j).getString("customerId"));
                map.put("subCompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                iCustomerDao.deleteCustomerSubcompany(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动客户取消分配");
    }

    @Override
    public ResponseResult batchCancelAllocationStore(String companyId, String companyType, String customerArrayStr, String storeIdArrayStr) {
        //本系统商品取消分配
        JSONArray customerArrary = JSON.parseArray(customerArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);
        for (int i = 0; i < storeIdArray.size(); i++) {
            for (int j = 0; j < customerArrary.size(); j++) {
                Map map = new HashMap();
                map.put("customerId", customerArrary.getJSONObject(j).getString("customerId"));
                map.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                iCustomerDao.deleteCustomerStore(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动客户取消分配");
    }
}
