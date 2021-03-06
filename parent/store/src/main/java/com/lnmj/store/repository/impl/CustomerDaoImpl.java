package com.lnmj.store.repository.impl;


import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.ICustomerDao;
import com.lnmj.store.repository.IDepartmentDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class CustomerDaoImpl extends BaseDao implements ICustomerDao {


    @Override
    public List<Customer> selectCustomerList(Map map) {
        return super.selectList("customer.selectCustomerList",map);
    }

    @Override
    public List<Customer> selectCustomerListSubCompany(Map map) {
        return super.selectList("customer.selectCustomerListSubCompany",map);
    }

    @Override
    public List<Customer> selectCustomerListStore(Map map) {
        return super.selectList("customer.selectCustomerListStore",map);
    }

    @Override
    public Customer selectLinShouCustomer(Map map) {
        return super.select("customer.selectLinShouCustomer",map);
    }

    @Override
    public int insertCustomer(Customer customer) {
        return super.insert("customer.insertCustomer",customer);
    }

    @Override
    public int insertCustomerBatch(Map map) {
        return super.insert("customer.insertCustomerBatch",map);
    }

    @Override
    public int deleteCustomer(Customer customer) {
        return super.delete("customer.deleteCustomer",customer);
    }

    @Override
    public int updateCustomer(Customer customer) {
        return super.update("customer.updateCustomer",customer);
    }

    @Override
    public int checkDefault(Customer customer) {
        return super.select("customer.checkDefault",customer);
    }

    @Override
    public int checkCustName(Customer customer) {
        return super.select("customer.checkCustName",customer);
    }

    @Override
    public String selectLastCustomerCode() {
        return super.select("customer.selectLastCustomerCode");
    }

    @Override
    public Customer selectDefaultCust(Customer customer) {
        return super.select("customer.selectDefaultCust",customer);
    }

    @Override
    public Customer selectCustomerByConditon(Customer customer) {
        return super.select("customer.selectCustomerByConditon",customer);
    }

    @Override
    public List<CustomerSubCompany> checkCustomerSubCompany(Map map) {
        return super.selectList("customer.checkCustomerSubCompany", map);
    }

    @Override
    public List<CustomerStore> checkCustomerStore(Map map) {
        return super.selectList("customer.checkCustomerStore", map);
    }

    @Override
    public int addCustomerSubCompany(Map map) {
        return super.insert("customer.addCustomerSubCompany", map);
    }

    @Override
    public int addCustomerStore(Map map) {
        return super.insert("customer.addCustomerStore", map);
    }

    @Override
    public int addCustomerSubCompanyBatch(Map map) {
        return super.insert("customer.addCustomerSubCompanyBatch", map);
    }

    @Override
    public int addCustomerStoreBatch(Map map) {
        return super.insert("customer.addCustomerStoreBatch", map);
    }

    @Override
    public int deleteCustomerSubcompany(Map map) {
        return super.delete("customer.deleteCustomerSubcompany", map);
    }

    @Override
    public int deleteCustomerStore(Map map) {
        return super.delete("customer.deleteCustomerStore", map);
    }

    @Override
    public Customer selectCustomerByNumber(String k3Number) {
        return super.select("customer.selectCustomerByNumber", k3Number);
    }

    @Override
    public List<CustomerSubCompany> selectCustomerSubCompany(Map map) {
        return super.selectList("customer.selectCustomerSubCompany",map);
    }

    @Override
    public List<CustomerStore> selectCustomerStore(Map map) {
        return super.selectList("customer.selectCustomerStore",map);
    }
}
