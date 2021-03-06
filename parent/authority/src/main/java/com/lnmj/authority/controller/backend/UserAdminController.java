package com.lnmj.authority.controller.backend;

import com.github.pagehelper.PageHelper;
import com.lnmj.authority.business.IAuthorityService;
import com.lnmj.authority.entity.*;
import com.lnmj.authority.repository.IAuthorityDao;
import com.lnmj.authority.serviceProxy.CompanyAndStoreApi;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.BPwdEncoderUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 后台管理用户Controller
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
    @Resource
    private IAuthorityService iAuthorityService;
    @Resource
    private CompanyAndStoreApi companyAndStoreApi;
    @Resource
    private IAuthorityDao iAuthorityDao;

    /**
     * 分页查询用户信息-平台
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseResult list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               String isSuperAdmin,
                               String companyType,
                               String companyId,
                               String searchString,
                               String selectCompanyType) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keywords", searchString);//按照用户名称
        List<Tuser> userList = new ArrayList<>();
        map.put("companyId", companyId);
        if (companyType.equals("1")) {
            //总公司超级管理员
            List<Map> listSubComapny = (List<Map>) companyAndStoreApi.selectSubsidiaryByParentID(Long.parseLong(companyId)).getResult();
            List<Tuser> userListAll = iAuthorityService.selectAdminUserListAllCompany(new HashMap());//查询所有的管理员
            if (selectCompanyType.equals("1")) {
                userList = iAuthorityService.selectAdminUserListCompany(map);
            } else if (selectCompanyType.equals("2")) {
                //查看他所有子公司管理员
                for (Map map1 : listSubComapny) {
                    for (Tuser tuser : userListAll) {
                        if (tuser.getCompanyType().equals("2") && map1.get("subsidiaryId").toString().equals(tuser.getCompanyId())) {
                            userList.add(tuser);
                        }
                    }
                }
            } else if (selectCompanyType.equals("3")) {
                //查看他所有子公司的分公司管理员
                List<Map> listStoreAll = (List<Map>) companyAndStoreApi.selectStoreByCodeOrName(null).getResult();
                if (listStoreAll != null) {
                    List<Map> listStoreNeed = new ArrayList<>();
                    for (Map map1 : listSubComapny) {
                        for (Map map2 : listStoreAll) {
                            if (map1.get("subsidiaryId").toString().equals(map2.get("subCompanyId").toString())) {
                                listStoreNeed.add(map2);
                            }
                        }
                    }
                    for (Map map1 : listStoreNeed) {
                        for (Tuser tuser : userListAll) {
                            if (tuser.getCompanyType().equals("3") && map1.get("storeId").toString().equals(tuser.getCompanyId())) {
                                userList.add(tuser);
                            }
                        }
                    }
                }
            }
        } else if (companyType.equals("2")) {
            //子公司超级管理员
            List<Map> listSubComapny = (List<Map>) companyAndStoreApi.selectStoreListBySubCompanyNoPage(Long.parseLong(companyId)).getResult();
            List<Tuser> userListAll = iAuthorityService.selectAdminUserListAllCompany(new HashMap());//查询所有的管理员
            if (selectCompanyType.equals("2")) {
                userList = iAuthorityService.selectAdminUserListSubCompany(map);
            } else if (selectCompanyType.equals("3")) {
                if (listSubComapny == null) {
                    return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(),
                            ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
                }
                //查看他所有分公司的分公司管理员
                for (Map map1 : listSubComapny) {
                    for (Tuser tuser : userListAll) {
                        if (tuser.getCompanyType().equals("3") && map1.get("storeId").toString().equals(tuser.getCompanyId())) {
                            userList.add(tuser);
                        }
                    }
                }
            }


        } else if (companyType.equals("0")) {
            //系统超级管理员
            if (selectCompanyType.equals("1")) {
                userList = iAuthorityService.selectAdminUserListCompany(new HashMap());
            } else if (selectCompanyType.equals("2")) {
                userList = iAuthorityService.selectAdminUserListSubCompany(new HashMap());
            } else if (selectCompanyType.equals("3")) {
                userList = iAuthorityService.selectAdminUserListStore(new HashMap());
            } else if (StringUtils.isBlank(selectCompanyType) || selectCompanyType.equals("0")) {
                userList = iAuthorityService.selectAdminUserListAllCompany(new HashMap());//查询所有的管理员
            }
        }


        PageRusult<Tuser> pageRusult = new PageRusult<Tuser>(userList);

        //查看用户拥有的角色
        for (Tuser u : userList) {
            List<Trole> roleList = iAuthorityService.selectRolesByUserId(u.getId());
            StringBuffer sb = new StringBuffer();
            for (Trole r : roleList) {
                sb.append("," + r.getName());
            }
            u.setRoles(sb.toString().replaceFirst(",", ""));

            //查看用户的所在公司
            String companyName = null;
            if (u.getCompanyType().equals("1")) {
                //如果是总公司登陆
                //根据总公司id查看公司名称
                ResponseResult responseResult = companyAndStoreApi.selectCompanyByID(Long.parseLong(u.getCompanyId()));
                if (responseResult.getResult() == null && responseResult.getResponseStatusType().getError().getErrorMsg().equals("总公司不存在")) {
                    companyName = "暂无对应总公司，此总公司可能被删除";
                } else {
                    Map companyMap = (Map) responseResult.getResult();
                    companyName = companyMap.get("companyName").toString();
                }
            } else if (u.getCompanyType().equals("2")) {
                //如果是子公司登陆
                //根据子公司id查看公司名称
                ResponseResult responseResult = companyAndStoreApi.selectSubsidiaryByID(Long.parseLong(u.getCompanyId()));
                if (responseResult.getResult() == null && responseResult.getResponseStatusType().getError().getErrorMsg().equals("子公司不存在")) {
                    companyName = "暂无对应子公司，此子公司可能被删除";
                } else {
                    Map companyMap = (Map) responseResult.getResult();
                    companyName = companyMap.get("subsidiaryName").toString();
                }

            } else if (u.getCompanyType().equals("3")) {
                ResponseResult responseResult = companyAndStoreApi.selectStoreById(Long.parseLong(u.getCompanyId()));
                if (responseResult.getResult() == null) {
                    companyName = "暂无对应分公司，此分公司可能被删除";
                } else {
                    Map companyMap = (Map) responseResult.getResult();
                    companyName = companyMap.get("name").toString();
                }

            } else if (u.getCompanyType().equals("0")) {
                companyName = "平台系统";
            }
            u.setCompanyName(companyName);
        }


        return ResponseResult.success(pageRusult);
    }


    @ResponseBody
    @RequestMapping(value = "/addupdateuser")
    public Map<String, Object> addupdateuser(Tuser tuser) {
        String storeIdsArrayStr = "[" + tuser.getStoreId() + "]";
        tuser.setStoreId(storeIdsArrayStr);
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            boolean flag = false;
            if (tuser.getId() == 0) {//新建
                //首先判断用户名是否可用
                Map map = new HashMap();
                map.put("userName", tuser.getUserName());
                Tuser userlist = iAuthorityService.selectByUserName(map);
                if (userlist != null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前用户名已存在");
                    return resultmap;
                }
                tuser.setPassword(new BCryptPasswordEncoder().encode(tuser.getPassword()));
                if (tuser.getCompanyType().equals("1")) {
                    List<Long> storeIdList = new ArrayList<>();
                    //如果是总公司管理员，可查看所有门店
                    List<Map> storeListMap = (List<Map>) companyAndStoreApi.selectStoreListByCompanyId(Long.parseLong(tuser.getCompanyId())).getResult();
                    for (Map mapItem : storeListMap) {
                        storeIdList.add(Long.parseLong(mapItem.get("storeId").toString()));
                    }
                    tuser.setStoreId(storeIdList.toString().replaceAll(" ", ""));
                }
                iAuthorityService.saveAdminUser(tuser);
                if (tuser.getCompanyType().equals("1")) {
                    //如果添加的是总公司用户，查看是否有默认总公司角色
                    Trole trole = iAuthorityDao.checkRoleDefaultCompany(new HashMap());

                    if (trole != null) {
                        //建立管理员和默认角色的关系
                        Tuserrole tuserrole = new Tuserrole();
                        tuserrole.setRoleId(trole.getId());
                        tuserrole.setUserId(tuser.getId());
                        iAuthorityService.saveUserRole(tuserrole);
                        flag = true;
                    }
                }
                if (tuser.getCompanyType().equals("2")) {
                    //如果添加的是子公司用户，查看是否有默认总公司角色
                    Trole trole = iAuthorityDao.checkRoleDefaultSubCompany(new HashMap());
                    if (trole != null) {
                        //建立管理员和默认角色的关系
                        Tuserrole tuserrole = new Tuserrole();
                        tuserrole.setRoleId(trole.getId());
                        tuserrole.setUserId(tuser.getId());
                        iAuthorityService.saveUserRole(tuserrole);
                        flag = true;
                    }
                }
            } else {//编辑
                Tuser oldObject = iAuthorityService.selectById(tuser.getId());
                if (oldObject == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前用户名不存在");
                    return resultmap;
                } else {
                    /*if (tuser.getCompanyType().equals("1")) {
                        List<Long> storeIdList = new ArrayList<>();
                        //如果是总公司管理员，可查看所有门店
                        List<Map> storeListMap = (List<Map>) companyAndStoreApi.selectStoreListByCompanyId(Long.parseLong(tuser.getCompanyId())).getResult();
                        for (Map mapItem : storeListMap) {
                            storeIdList.add(Long.parseLong(mapItem.get("storeId").toString()));
                        }
                        tuser.setStoreId(storeIdList.toString().replaceAll(" ", ""));
                    }*/
                    tuser.setPassword(null);
                    iAuthorityService.updateAdminUser(tuser);
                }
            }
            resultmap.put("state", "success");
            if (flag){
                resultmap.put("mesg", "操作成功,已经设置默认角色");
            }else{
                resultmap.put("mesg", "操作成功");
            }

            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "操作失败，系统异常");
            return resultmap;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleteuser")
    public Map<String, Object> deleteuser(Tuser tuser) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if ((tuser.getId() != null && !tuser.getId().equals(0))) {
                Tuser oldObject = iAuthorityService.selectById(tuser.getId());
                if (oldObject == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "删除失败,无法找到该记录");
                    return resultmap;
                } else {
                    //还需删除用户角色中间表
                    iAuthorityService.deleteUserRole(tuser.getId());
                    iAuthorityService.deleteUser(tuser.getId());
                }
            } else {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "删除失败");
            }


            resultmap.put("state", "success");
            resultmap.put("mesg", "删除成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "删除失败，系统异常");
            return resultmap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteuserByCompany")
    public Map<String, Object> deleteuserByCompany(Tuser tuser) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tuser.getCompanyType() != null) {
                Map map = new HashMap();
                map.put("companyType", tuser.getCompanyType());
                map.put("companyId", tuser.getCompanyId());
                List<Tuser> tusers = iAuthorityService.selectAdminUserListAllCompany(map);
                if (tusers.size() == 0) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "删除失败,无法找到该记录");
                    return resultmap;
                } else {
                    //还需删除用户角色中间表
                    //根据公司及公司类型查看用户
                    for (Tuser tuser1 : tusers) {
                        iAuthorityService.deleteUserRole(tuser1.getId());
                    }
                    iAuthorityService.deleteuserByCompany(tuser);
                }
            } else {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "删除失败");
            }


            resultmap.put("state", "success");
            resultmap.put("mesg", "删除成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "删除失败，系统异常");
            return resultmap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectUserById")
    public Map<String, Object> selectUserById(Tuser tuser, Integer isSuperAdmin) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        Tuser oldObject = iAuthorityService.selectById(tuser.getId());
        try {
            if (tuser.getId() != null && !tuser.getId().equals(0)) {
                if (tuser == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "无法找到该记录");
                    return resultmap;
                }
            } else {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "无法找到该记录的id");
                return resultmap;
            }

            List<Trole> roleList = iAuthorityService.selectRolesByUserId(tuser.getId());
            StringBuffer sb = new StringBuffer();
            for (Trole r : roleList) {
                sb.append("," + r.getName());
            }
            tuser.setRoles(sb.toString().replaceFirst(",", ""));


            //所有角色
            List<Trole> allrolelist = iAuthorityService.selectRoleList(null);
            resultmap.put("roleList", roleList);//用户拥有的所有角色

            Iterator<Trole> it = allrolelist.iterator();
            while (it.hasNext()) {
                Trole temp = it.next();
                for (Trole e2 : roleList) {
                    if (temp.getId().compareTo(e2.getId()) == 0) {
                        it.remove();
                    }
                }
            }


            List<Trole> allrolelist2 = new ArrayList<>();
            for (Trole trole : allrolelist) {
                if (!trole.getId().toString().equals("1")) {//如果不是超级管理员  才返回
                    allrolelist2.add(trole);
                }
            }

            List<Trole> notinrolelist;
            if (isSuperAdmin.toString().equals("1")) {
                //如果操作人是超级管理员 返回所有
                notinrolelist = allrolelist;
            } else {
                //如果操作人不是超级管理员 只返回非超级管理员角色
                notinrolelist = allrolelist2;
            }


            resultmap.put("notinrolelist", notinrolelist);//用户不拥有的角色

            resultmap.put("tuser", oldObject);
            resultmap.put("state", "success");
            resultmap.put("mesg", "获取成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "获取失败，系统异常");
            return resultmap;
        }
    }


    //设置用户角色
    @ResponseBody
    @RequestMapping(value = "/saveRoleSet")
    public Map<String, Object> saveRoleSet(Integer[] role, Integer id) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            // 根据用户id删除所有用户角色关联实体
            iAuthorityService.deleteUserRole(id);
            if (role != null && role.length > 0) {
                for (Integer roleid : role) {
                    Tuserrole tuserrole = new Tuserrole();
                    tuserrole.setRoleId(roleid);
                    tuserrole.setUserId(id);
                    iAuthorityService.saveUserRole(tuserrole);
                }
            }
            resultmap.put("state", "success");
            resultmap.put("mesg", "设置成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "设置失败，系统异常");
            return resultmap;
        }
    }


    //修改密码
    @ResponseBody
    @PostMapping("/updatePassword")
    public Map<String, Object> updatePassword(Tuser tuser) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {

            if (tuser == null) {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "设置失败，缺乏字段信息");
                return resultmap;
            } else {
                if (tuser.getId() != null
                        && tuser.getId().intValue() != 0
                        && StringUtils.isNotEmpty(tuser.getUserName())
                        /*&& StringUtils.isNotEmpty(tuser.getOldPassword())*/
                        && StringUtils.isNotEmpty(tuser.getPassword())) {
                    Map map = new HashMap();
                    map.put("userName", tuser.getUserName());
                    Tuser tuserResult = iAuthorityService.selectByUserName(map);
                    if (tuser.getOldPassword() != null && !BPwdEncoderUtil.matches(tuser.getOldPassword(), tuserResult.getPassword())) {
                        resultmap.put("state", "fail");
                        resultmap.put("mesg", "原密码错误");
                        return resultmap;
                    } else {
                        Tuser newEntity = tuserResult;
                        newEntity.setPassword(new BCryptPasswordEncoder().encode(tuser.getPassword()));
                        iAuthorityService.updateAdminUser(newEntity);
                    }
                } else {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "设置失败，缺乏字段信息");
                    return resultmap;
                }
            }

            resultmap.put("state", "success");
            resultmap.put("mesg", "密码修改成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "密码修改失败，系统异常");
            return resultmap;
        }
    }

    //查询管理员权限
    @ResponseBody
    @PostMapping("/selectRoleListByUserId")
    public ResponseResult selectRoleListByUserId(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Tuser tuser) {
        return iAuthorityService.selectRolesByUserIdPage(pageNum, pageSize, tuser.getId());

    }
}
