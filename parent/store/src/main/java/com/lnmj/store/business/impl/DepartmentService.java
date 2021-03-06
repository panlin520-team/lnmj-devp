package com.lnmj.store.business.impl;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseDepartmentEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.store.business.IDepartmentService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.StoreVO;
import com.lnmj.store.repository.ICompanyDao;
import com.lnmj.store.repository.IDepartmentDao;
import com.lnmj.store.repository.IStoreDao;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.ProductApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Transactional
@Service("departmentService")
public class DepartmentService implements IDepartmentService {
    @Resource
    private IDepartmentDao iDepartmentDao;
    @Resource
    private ICompanyDao iCompanyDao;
    @Resource
    private IStoreDao iStoreDao;
    @Resource
    private K3Api_organization k3Api;
    @Resource
    private ProductApi productApi;

    @Override
    public ResponseResult selectDepartmentList(int pageNum, int pageSize, String keyWordName, String companyType, String companyId, String searchCompanyType,String searchCompanyId) {
        if (companyId != null && companyId.equals("0")) {
            companyId = null;
        }
        if (searchCompanyId!=null&&searchCompanyId.equals("0")){
            searchCompanyId=null;
        }

        Map map = new HashMap();
        if (companyType.equals("1")) {
            //总公司查看
            if (searchCompanyType.equals("1")) {
                //查看总公司
                String[] listArray = null;
                if (companyId!=null){
                    listArray = companyId.split(",");
                }
                map.put("list", listArray);
                map.put("companyType", 1);
                map.put("keyWordName", keyWordName);
            } else if (searchCompanyType.equals("2")) {
                //查看子公司
                String companyIdList = "";
                if (searchCompanyId==null){
                    List<Subsidiary> subsidiaryList = iCompanyDao.selectSubsidiaryByParentID(Long.parseLong(companyId));
                    for (Subsidiary subsidiary : subsidiaryList) {
                        companyIdList = companyIdList + "," + subsidiary.getSubsidiaryId();
                    }
                    map.put("list", companyIdList.substring(1).split(","));
                }else{
                   Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(searchCompanyId));
                    companyIdList = subsidiary.getSubsidiaryId().toString();
                    map.put("list", companyIdList.split(","));
                }

                map.put("companyType", 2);
                map.put("keyWordName", keyWordName);
            } else if (searchCompanyType.equals("3")) {
                //查看分公司
                String companyIdList = "";
                List<Store> storeList = iStoreDao.selectStoreListByCompanyId(Long.parseLong(companyId));
                for (Store store : storeList) {
                    companyIdList = companyIdList + "," + store.getStoreId();
                }
                map.put("list", companyIdList.substring(1).split(","));
                map.put("companyType", 3);
                map.put("keyWordName", keyWordName);
            }
        } else if (companyType.equals("2")) {
            //子公司查看
            if (searchCompanyType.equals("2")) {
                //查看子公司
                String[] listArray = null;
                if (companyId!=null){
                    listArray = companyId.split(",");
                }
                map.put("list", listArray);
                map.put("companyType", 2);
                map.put("keyWordName", keyWordName);
            } else if (searchCompanyType.equals("3")) {
                //查看分公司
                String companyIdList = "";
                HashMap<Object, Object> map1 = new HashMap<>();
                map.put("subsidiaryId", companyId);
                List<Store> storeList = iStoreDao.selectStoreListBySubCompany(map1);
                if (storeList.size() > 0) {
                    for (Store store : storeList) {
                        companyIdList = companyIdList + "," + store.getStoreId();
                    }
                    map.put("list", companyIdList.substring(1).split(","));
                }else{
                    map.put("list",companyIdList.split(","));
                }

                map.put("companyType", 3);
                map.put("keyWordName", keyWordName);
            }
        } else if (companyType.equals("3")) {
            //分公司查看
            String[] listArray = null;
            if (companyId!=null){
                 listArray = companyId.split(",");
            }
            map.put("list", listArray);
            map.put("companyType", 3);
            map.put("keyWordName", keyWordName);
        }


        List<Department> departmentList = iDepartmentDao.selectDepartmentList(map);

        List<Company> companyList = iCompanyDao.selectCompanyList(new HashMap());
        List<Subsidiary> subCompanyList = iCompanyDao.selectSubsidiaryList(new HashMap());
        List<StoreVO> storeList = iStoreDao.selectStoretList(new HashMap());
        //查看公司名称
        if (searchCompanyType.equals("1")) {
            //如果是总公司
            for (Department department : departmentList) {
                for (Company company : companyList) {
                    if (department.getCompanyId().equals(company.getCompanyId().toString())) {
                        department.setCompanyName(company.getCompanyName());
                    }
                }
            }

        } else if (searchCompanyType.equals("2")) {
            //如果是子公司
            for (Department department : departmentList) {
                for (Subsidiary subsidiary : subCompanyList) {
                    if (department.getCompanyId().equals(subsidiary.getSubsidiaryId().toString())) {
                        department.setCompanyName(subsidiary.getSubsidiaryName());
                    }
                }
            }
        } else if (searchCompanyType.equals("3")) {
            //如果是分公司
            for (Department department : departmentList) {
                for (StoreVO storeVO : storeList) {
                    if (department.getCompanyId().equals(storeVO.getStoreId().toString())) {
                        department.setCompanyName(storeVO.getName());
                    }
                }
            }
        }


        Map mapRsultPage = ListPageUntil.listPage(departmentList, pageSize, pageNum);
        Map mapResult = new HashMap();
        if (((List) mapRsultPage.get("list")).size() > 0) {
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        }
        return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_LIST_NULL.getCode(),
                ResponseDepartmentEnum.DEPARTMENT_LIST_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectDepartmentListNoPage(String companyType, String companyId) {
        Map map = new HashMap();

        map.put("list", companyId.split(","));
        map.put("companyType", companyType);
        List<Department> departmentList = iDepartmentDao.selectDepartmentList(map);
        if (departmentList.size() > 0) {
            return ResponseResult.success(departmentList);

        }
        return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_LIST_NULL.getCode(),
                ResponseDepartmentEnum.DEPARTMENT_LIST_NULL.getDesc()));

    }

    @Override
    public ResponseResult insertDepartment(Department department) {
        //查看部门名字是否重复
        int resultIntName = iDepartmentDao.checkDeptName(department);
        if (resultIntName>0){
            return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_NAME_EXIT.getCode(),
                    ResponseDepartmentEnum.DEPARTMENT_NAME_EXIT.getDesc()));
        }

        //如果默认部门字段不为null  查看是否已经存在默认部门
        if (department.getIsDefaultDept() != null && department.getIsDefaultDept().toString().equals("1")) {
            //查看是否有默认
            int resultInt = iDepartmentDao.checkDefault(department);
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_ADD_FAIL_DEFUALT_EXIT.getCode(),
                        ResponseDepartmentEnum.DEPARTMENT_ADD_FAIL_DEFUALT_EXIT.getDesc()));
            }
        }


        if (department.getCompanyType().equals("3")) {
            Store store = iStoreDao.selectStoreById(Long.parseLong(department.getCompanyId()));
            department.setK3OrgNumber(store.getK3Number());
        }
        int resultInt = iDepartmentDao.insertDepartment(department);
        //添加到k3
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (department.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(department.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(department.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(department.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        ResponseResult responseResult = k3Api.departmentSave(dataCentre, k3UserName, k3PassWord, department.getName(), department.getK3OrgNumber(), department.getK3OrgNumber(), null, null);
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("部门添加成功,K3部门添加未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (resultInt > 0) {
            String k3Id = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Id").toString();
            String k3Number = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Number").toString();
            Department departmentUpdate = new Department();
            departmentUpdate.setId(department.getId());
            departmentUpdate.setK3Number(k3Number);
            departmentUpdate.setK3Id(k3Id);
            iDepartmentDao.updateDepartment(departmentUpdate);
            return ResponseResult.success("部门添加成功,K3部门添加成功");
        } else {
            return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_ADD_FAIL.getCode(),
                    ResponseDepartmentEnum.DEPARTMENT_ADD_FAIL.getDesc()));

        }

    }

    @Override
    public ResponseResult deleteDepartment(Department department) {
        //判断部门是否被其他入库使用
        List<Map> mapList = (List<Map>) (productApi.checkInstorageByDepartment(department.getK3Number()).getResult());
        if (mapList != null && mapList.size() > 0) {
            return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_QITA_INSTORAGE_USE.getCode(),
                    ResponseDepartmentEnum.DEPARTMENT_QITA_INSTORAGE_USE.getDesc()));
        }

        int resultInt = iDepartmentDao.deleteDepartment(department);
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (department.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(department.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(department.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(department.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        ResponseResult responseResult = k3Api.departmentDelete(dataCentre, k3UserName, k3PassWord, null, department.getK3Id());
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("部门删除成功,K3部门删除未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        if (resultInt > 0) {
            return ResponseResult.success("部门删除成功,K3部门删除成功");
        } else {
            return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_DELETE_FAIL.getCode(),
                    ResponseDepartmentEnum.DEPARTMENT_DELETE_FAIL.getDesc()));

        }
    }

    @Override
    public ResponseResult updateDepartment(Department department) {
        int resultInt = iDepartmentDao.updateDepartment(department);
        //K3接入
        String k3UserName = null;
        String k3PassWord = null;
        String dataCentre = null;
        if (department.getCompanyType().equals("1")) {
            //如果是总公司
            Company company = iCompanyDao.selectCompanyByID(Long.parseLong(department.getCompanyId()));
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(department.getCompanyId()));
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        } else if (department.getCompanyType().equals("3")) {
            //如果是分公司
            Store store = iStoreDao.selectStoreById(Long.parseLong(department.getCompanyId()));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            Company company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
            k3UserName = company.getYewuDataCentreUserName();
            k3PassWord = company.getYewuDataCentrePassword();
            dataCentre = company.getDataCentre();
        }
        ResponseResult responseResult = k3Api.departmentSave(dataCentre, k3UserName, k3PassWord, department.getName(), department.getK3OrgNumber(), department.getK3OrgNumber(), "FName", department.getK3Id());
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("部门修改成功,K3部门修改未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (resultInt > 0) {
            return ResponseResult.success("部门修改成功,K3部门修改成功");
        } else {
            return ResponseResult.error(new Error(ResponseDepartmentEnum.DEPARTMENT_UPDATE_FAIL.getCode(),
                    ResponseDepartmentEnum.DEPARTMENT_UPDATE_FAIL.getDesc()));

        }
    }

    @Override
    public ResponseResult selectDefaultDept(Department department) {
        Department departmentResult = iDepartmentDao.selectDefaultDept(department);
        return ResponseResult.success(departmentResult);
    }
}
