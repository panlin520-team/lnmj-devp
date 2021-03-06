package com.lnmj.authority.controller.backend;


import com.lnmj.authority.business.IAuthorityService;
import com.lnmj.authority.entity.*;
import com.lnmj.authority.serviceProxy.CompanyAndStoreApi;
import com.lnmj.authority.serviceProxy.ProductApi;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAuthEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.BPwdEncoderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/5/17 11:28
 * @Description: 登录权限管理
 */
@RestController
@RequestMapping("/manager/authority")
public class LoginAuthorityController {
    @Resource
    IAuthorityService iAuthorityService;
    @Resource
    CompanyAndStoreApi companyAndStoreApi;
    @Resource
    ProductApi productApi;
    /**
     * 用户平台后台登录请求
     */
    @ResponseBody
    @PostMapping("/login")
    public ResponseResult login(String userName, String password, String companyId, String companyType, String isSuperAdmin) {


        //查找管理员用户
        Map mapUser = new HashMap();
        mapUser.put("userName", userName);
        Tuser tuser = iAuthorityService.selectByUserName(mapUser);
        //用户验证
        if (null == tuser) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getCode(), ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getDesc()));
        }
        if (!BPwdEncoderUtil.matches(password, tuser.getPassword())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getCode(), ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getDesc()));
        }

        //查看用户所属公司名称
        String companyName = null;
        if (tuser.getCompanyType().equals("1")) {
            //如果是总公司登陆
            //根据总公司id查看公司名称
            ResponseResult responseResult = companyAndStoreApi.selectCompanyByID(Long.parseLong(tuser.getCompanyId()));
            Map companyMap = (Map) responseResult.getResult();
            if(companyMap==null){
                return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(), ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
            }
            companyName = companyMap.get("companyName").toString();
            tuser.setOrgK3Number(companyMap.get("k3Number").toString());
        } else if (tuser.getCompanyType().equals("2")) {
            //如果是子公司登陆
            //根据子公司id查看公司名称
            ResponseResult responseResult = companyAndStoreApi.selectSubsidiaryByID(Long.parseLong(tuser.getCompanyId()));
           if (!responseResult.isSuccess()){
               return responseResult;
           }
            ResponseResult responseResultCompany = companyAndStoreApi.selectCompanyByID(Long.parseLong(((Map)responseResult.getResult()).get("parentCompany").toString()));
            Map companyMap = (Map) responseResult.getResult();
            if(companyMap==null){
                return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(), ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
            }
            companyName = companyMap.get("subsidiaryName").toString();
            tuser.setOrgK3Number(companyMap.get("k3Number").toString());
            tuser.setParentCompanyId(Long.parseLong(((Map)responseResultCompany.getResult()).get("companyId").toString()));
        }

        tuser.setCompanyName(companyName);
        //获取管理员拥有权限组
        List<Trole> troleList = iAuthorityService.selectRolesByUserId(tuser.getId());
        tuser.setRoleList(troleList);

        // 获取token
      /*  JWT jwt = null;
        try {
            jwt = client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", user.getUserName(), user.getPassword());
        } catch (Exception e) {
            System.out.println(e);
        }
        if (jwt == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.LOGIN_FAILURE.getCode(), ResponseCodeAccountEnum.LOGIN_FAILURE.getDesc()));
        }
        //将token和refreshToken及过期时间存入redis
        long exp = System.currentTimeMillis() + jwt.getExpires_in() * 1000l;
        CacheForHash.set("access_token", jwt.getAccess_token(), exp);
        CacheForHash.set("refresh_token", jwt.getRefresh_token(), exp);
        CacheForHash.expire("access_token", 1, TimeUnit.DAYS);//保存一天
        CacheForHash.expire("refresh_token", 1, TimeUnit.DAYS);//保存一天*/
        AdminUserLoginDTO adminUserLoginDTO = new AdminUserLoginDTO();
        /*adminUserLoginDTO.setJwt(jwt);*/
        adminUserLoginDTO.setUser(tuser);
        Integer pId = 1;
        List<Tmenu> tmenuOneClassList = iAuthorityService.selectByParentIdAndRoleIds(pId, tuser.getId());
        if (tmenuOneClassList == null) {
            return ResponseResult.error(new Error(ResponseCodeAuthEnum.ROLES_NO_MENU.getCode(), ResponseCodeAuthEnum.ROLES_NO_MENU.getDesc()));
        }
        tuser.setRoleId(troleList.get(0).getId());
        tuser.setRoles(troleList.get(0).getName());
        adminUserLoginDTO.setTmenu(tmenuOneClassList);
        adminUserLoginDTO.setUser(tuser);
        return ResponseResult.success(adminUserLoginDTO);
    }

    /**
     * 用户店铺登录请求
     */
    @ResponseBody
    @PostMapping("/storeLogin")
    public ResponseResult storeLogin(String imageCode,/* String storeCode,*/ String userName, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
/*        if(StringUtils.isEmpty(imageCode)){
            map.put("success", false);
            map.put("errorInfo", "请输入验证码！");
            return map;
        }*/
/*        if(!session.getAttribute("checkcode").equals(imageCode)){
            map.put("success", false);
            map.put("errorInfo", "验证码输入错误！");
            return map;
        }*/
        //查找管理员用户
        Tuser tuser = new Tuser();
        tuser.setUserName(userName);
        tuser.setCompanyType("3");
        //根据门店code查看门店id
/*        List<Map> mapList = (List<Map>)(companyAndStoreApi.selectStoreByCodeOrName(*//*storeCode*//*).getResult());
        tuser.setCompanyId(mapList.get(0).get("storeId").toString());*/
        Tuser tuserResult = iAuthorityService.selectStoreUserByUserNameAndStoreCode(tuser);

        //用户验证
        if (null == tuserResult) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getCode(), ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getDesc()));
        }

        if (!BPwdEncoderUtil.matches(password, tuserResult.getPassword())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getCode(), ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getDesc()));
        }

        String companyId = tuserResult.getCompanyId();
        //通过门店id找门店
        Map mapStore = (Map)companyAndStoreApi.selectStoreById(Long.parseLong(companyId)).getResult();
        //查看子公司
        ResponseResult responseResultSubCompany = companyAndStoreApi.selectSubsidiaryByID(Long.parseLong(mapStore.get("subCompanyId").toString()));
        //查看总公司
        ResponseResult responseResultCompany = companyAndStoreApi.selectCompanyByID(Long.parseLong(((Map)responseResultSubCompany.getResult()).get("parentCompany").toString()));
        tuserResult.setPassword("");
        tuserResult.setStoreCode(mapStore.get("code").toString());
        tuserResult.setIndustry(Long.parseLong(mapStore.get("industryID").toString()));
        tuserResult.setCompanyName(mapStore.get("name").toString());
        //根据门店id查看门店的仓库code
        Map mapStock = ((List<Map>)productApi.selectStockList(3L,Long.parseLong(companyId)).getResult()).get(0);
        String stockCode = mapStock.get("stockCode").toString();
        String stockId = mapStock.get("stockId").toString();
        tuserResult.setStockCode(stockCode);
        tuserResult.setStockId(stockId);
        tuserResult.setOrgK3Number(mapStore.get("k3Number").toString());
        tuserResult.setParentParentCompanyId(Long.parseLong(((Map)responseResultCompany.getResult()).get("companyId").toString()));
        return ResponseResult.success(tuserResult);
    }

    @ResponseBody
    @PostMapping("/unlock")
    public ResponseResult unlock(Integer userId, String password) {
        if (userId == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));

        }
        if (StringUtils.isBlank(password)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.PASSWORD_NULL.getCode(), ResponseCodeAccountEnum.PASSWORD_NULL.getDesc()));
        }
        return ResponseResult.success(iAuthorityService.unlock(userId, password));
    }
}




