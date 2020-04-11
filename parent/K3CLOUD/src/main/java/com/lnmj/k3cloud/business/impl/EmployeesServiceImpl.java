package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IEmployeesService;
import com.lnmj.k3cloud.entity.base.BaseAuditBean;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.employees.employees.addSendParam.*;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description: 金蝶service
 */
@Service("employeesServiceImpl")
public class EmployeesServiceImpl implements IEmployeesService {
    @Override
    public ResponseResult employeesSave(String acctId, String dataCentreUserName, String dataCentrePassword, String FName, String FCreateOrgId, String FUseOrgId, String FStaffNumber, String deptName, String postName) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String nowDate = format.format(date);

                String model = "{\n" +
                        " \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FName\": \""+FName+"\",\n" +
                        "        \"FStaffNumber\": \""+FStaffNumber+"\",\n" +
                        "        \"FUseOrgId\": {\n" +
                        "            \"FNumber\": \""+FUseOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FCreateOrgId\": {\n" +
                        "            \"FNumber\": \""+FCreateOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FCreateSaler\": false,\n" +
                        "        \"FCreateUser\": false,\n" +
                        "        \"FCreateCashier\": false,\n" +
                        "        \"FJoinDate\": \""+nowDate+"\",\n" +
                        "        \"FSHRMapEntity\": {},\n" +
                        "        \"FPostEntity\": [\n" +
                        "            {\n" +
                        "                \"FWorkOrgId\": {\n" +
                        "                    \"FNumber\": \""+FUseOrgId+"\"\n" +
                        "                },\n" +
                        "                \"FPostDept\": {\n" +
                        "                    \"FNumber\": \""+deptName+"\"\n" +
                        "                },\n" +
                        "                \"FPost\": {\n" +
                        "                    \"FNumber\": \""+postName+"\"\n" +
                        "                },\n" +
                        "                \"FStaffStartDate\": \""+nowDate+"\",\n" +
                        "                \"FIsFirstPost\": true\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}";



//                String employee = JSON.toJSONString(employeeSaveRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jsonObject = InvokeHelper.Save("BD_Empinfo", model);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult employeesDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String deleteEmp = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_Empinfo", deleteEmp);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }

    }

    @Override
    public ResponseResult employeesView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_Empinfo", a);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult employeesSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> numberList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(numberList);
                baseSubmitBean.setIds(ids);
                String submitEmp = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_Empinfo", submitEmp);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }

    }

    @Override
    public ResponseResult employeesAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_Empinfo", a);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }

    }

    @Override
    public ResponseResult employeesUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_Empinfo", a);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }

    }

    @Override
    public ResponseResult saveYeWuYuan(String acctId, String dataCentreUserName, String dataCentrePassword, String fBizOrgId, String fStaffId) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                String model = "{\n" +
                        "    \"Creator\": \"\",\n" +
                        "    \"NeedUpDateFields\": [],\n" +
                        "    \"NeedReturnFields\": [],\n" +
                        "    \"IsDeleteEntry\": \"true\",\n" +
                        "    \"SubSystemId\": \"\",\n" +
                        "    \"IsVerifyBaseDataField\": \"false\",\n" +
                        "    \"IsEntryBatchFill\": \"true\",\n" +
                        "    \"ValidateFlag\": \"true\",\n" +
                        "    \"NumberSearch\": \"true\",\n" +
                        "    \"InterationFlags\": \"\",\n" +
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FOperatorId\": 0,\n" +
                        "        \"FOperatorType\": \"XSY\",\n" +
                        "        \"FEntity\": [\n" +
                        "            {\n" +
                        "              \n" +
                        "                \"FOperatorType_ETY\": \"XSY\",\n" +
                        "                \"FBizOrgId\": {\n" +
                        "                    \"FNumber\": \""+fBizOrgId+"\"\n" +
                        "                },\n" +
                        "                \"FStaffId\": { \n" +
                        "                     \"FNumber\": \""+fStaffId+"\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("BD_OPERATOR", model);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult delYeWuYuan(String acctId, String dataCentreUserName, String dataCentrePassword, String ids) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                String model = "{\n" +
                        "    \"CreateOrgId\": 0,\n" +
                        "    \"Ids\": \""+ids+"\"\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Delete("BD_OPERATOR", model);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }


}
