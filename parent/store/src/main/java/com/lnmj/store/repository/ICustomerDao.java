package com.lnmj.store.repository;

import com.lnmj.store.entity.*;

import java.util.List;
import java.util.Map;

public interface ICustomerDao {
    List<Customer> selectCustomerList(Map map);
    List<Customer> selectCustomerListSubCompany(Map map);
    List<Customer> selectCustomerListStore(Map map);
    Customer selectLinShouCustomer(Map map);
    int insertCustomer(Customer customer);
    int insertCustomerBatch(Map map);
    int deleteCustomer(Customer customer);
    int updateCustomer(Customer customer);
    int checkDefault(Customer customer);
    int checkCustName(Customer customer);

    String selectLastCustomerCode();
    Customer selectDefaultCust(Customer customer);
    Customer selectCustomerByConditon(Customer customer);
    List<CustomerSubCompany> checkCustomerSubCompany(Map map);

    List<CustomerStore> checkCustomerStore(Map map);

    int addCustomerSubCompany(Map map);

    int addCustomerStore(Map map);

    int addCustomerSubCompanyBatch(Map map);

    int addCustomerStoreBatch(Map map);

    int deleteCustomerSubcompany(Map map);

    int deleteCustomerStore(Map map);

    Customer selectCustomerByNumber(String k3Number);

    List<CustomerSubCompany> selectCustomerSubCompany(Map map);
    List<CustomerStore> selectCustomerStore(Map map);
}
