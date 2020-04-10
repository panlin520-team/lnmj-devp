package com.lnmj.k3cloud.util;

/**
 * @Author: yilihua
 * @Date: 2019-11-09 09:52
 * @Description:
 */
public class K3cloudConfig {
    public final static String PARENT_URL = "http://47.108.29.183:8001/k3cloud/";
    /*public final static String PARENT_URL = "http://192.168.68.188/K3Cloud/";*/
   /* public final static String PARENT_URL = "http://192.168.100.2/k3cloud/";*/
    public final static String LOGIN_URL = "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";
    public final static String ACCTID = "5e16d1621a091f";
    public final static String USERNAME = "Administrator";
    public final static String PASSWORD = "888888";
    public final static String LCID = "2052";
    public final static String LOGIN_FAIL = "暂未登录";

       public static Boolean login(String acctId ,String dataCentreUserName ,String dataCentrePassword){
           Boolean flag = false;
           InvokeHelper.POST_K3CloudURL = PARENT_URL;
           String dbId = acctId;
           String uid = dataCentreUserName;
           String pwd = dataCentrePassword;
           int lang = Integer.parseInt(LCID);
           try {
               flag = InvokeHelper.Login(dbId, uid, pwd, lang);
           } catch (Exception e) {
               e.printStackTrace();
           }
           return flag;
        }

}
