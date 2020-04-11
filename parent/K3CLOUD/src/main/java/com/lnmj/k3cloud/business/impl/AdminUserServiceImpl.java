package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountBookService;
import com.lnmj.k3cloud.business.AdminUserService;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Map;

/**
 * @Description 客户金蝶service
 * @Param
 * @Return
 * @Author Mr.Ren
 * @Date 2019/11/11
 * @Time
 */
@Service("adminUserServiceImpl")
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private IK3CLOUDDao ik3CLOUDDao;
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveAdminUser(String acctId,
                                        String dataCentreUserName,
                                        String dataCentrePassword,
                                        String fUserAccount,
                                        String fName) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                String param = "{\n" +
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
                        "        \"FUserID\": 0,\n" +
                        "        \"FUserAccount\": \"" + fUserAccount + "\",\n" +
                        "        \"FName\": \"" + fName + "\",\n" +
                        "        \"FIsLockTerminal\": false,\n" +
                        "        \"FOrgInfo\": [\n" +
                        "            {\n" +
                        "                \"FOrgOrgId\": {\n" +
                        "                    \"FNumber\": \"100\"\n" +
                        "                },\n" +
                        "                \"FRoleInfo\": [\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"B2C01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BD01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BD02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BD09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"BOS08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"CRM_SV\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN14_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN20_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"FIN21_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"MFG13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM_PRJ_01\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM_PRJ_02\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM_PRJ_03\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM_PRJ_04\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM_PRJ_05\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"PLM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"QM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM14_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM15_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM16_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCM17_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCMCP01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNumber\": \"SCMCP02_SYS\"\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("SEC_User", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("管理员保存");
                    incident.setExcuteIncidentName("SEC_User,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
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
    public ResponseResult saveAdminUserById(String acctId, String dataCentreUserName, String dataCentrePassword, String fUserID) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                JSONObject jsonObject = InvokeHelper.Save("SEC_User", "{\n" +
                        "    \"Creator\": \"\",\n" +
                        "    \"NeedUpDateFields\": [],\n" +
                        "    \"NeedReturnFields\": [],\n" +
                        "    \"IsDeleteEntry\": \"false\",\n" +
                        "    \"SubSystemId\": \"\",\n" +
                        "    \"IsVerifyBaseDataField\": \"false\",\n" +
                        "    \"IsEntryBatchFill\": \"true\",\n" +
                        "    \"ValidateFlag\": \"true\",\n" +
                        "    \"NumberSearch\": \"true\",\n" +
                        "    \"InterationFlags\": \"\",\n" +
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FUserID\": "+fUserID+"\n" +
                        "    }\n" +
                        "}");
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
    public ResponseResult orgToUser(String dataCentreName, String userId, String orgId) {
        ik3CLOUDDao.orgToUser(dataCentreName, userId, orgId);
        return ResponseResult.success();
    }


}
