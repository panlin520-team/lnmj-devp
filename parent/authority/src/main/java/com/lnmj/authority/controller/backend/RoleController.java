package com.lnmj.authority.controller.backend;

import com.github.pagehelper.PageHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lnmj.authority.business.IAuthorityService;
import com.lnmj.authority.entity.*;
import com.lnmj.authority.repository.IAuthorityDao;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAuthEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseRoleEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Auther: panlin
 * @Date: 2019/6/21 13:00
 * @Description:
 */
@Controller
@RequestMapping("/manager/role")
public class RoleController {
    @Resource
    private IAuthorityService iAuthorityService;
    @Resource
    private IAuthorityDao iAuthorityDao;


    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseResult list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String keyword)  {
        PageHelper.startPage(pageNum, pageSize);
        List<Trole> roleList = iAuthorityService.selectRoleList(keyword);
        PageRusult<Trole> pageRusult = new PageRusult<Trole>(roleList);
        return ResponseResult.success(pageRusult);
    }


    @ResponseBody
    @RequestMapping(value = "/addupdaterole")
    public ResponseResult addupdaterole(Trole trole) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (trole.getId() == null) {//新建
                //如果总公司默认开启 判断是否已经有了
                if (trole.getCompanyDefault().toString().equals("1")){
                    Trole resultTrole = iAuthorityDao.checkRoleDefaultCompany(new HashMap());
                    if (resultTrole!=null){
                        return ResponseResult.error(new Error(ResponseRoleEnum.COMPANY_DEFAULT_ROLE_EXIT.getCode(), ResponseRoleEnum.COMPANY_DEFAULT_ROLE_EXIT.getDesc()));
                    }
                }
                //如果子公司默认开启 判断是否已经有了
                if (trole.getSubCompanyDefault().toString().equals("1")){
                    Trole resultTrole = iAuthorityDao.checkRoleDefaultSubCompany(new HashMap());
                    if (resultTrole!=null){
                        return ResponseResult.error(new Error(ResponseRoleEnum.SUB_COMPANY_DEFAULT_ROLE_EXIT.getCode(), ResponseRoleEnum.SUB_COMPANY_DEFAULT_ROLE_EXIT.getDesc()));
                    }
                }
                //首先判断用户名是否可用
                Integer resultInt = iAuthorityDao.selectRoleByName(trole.getName());
                if (resultInt > 0) {
                    return ResponseResult.error(new Error(ResponseRoleEnum.ROLE_EXIT.getCode(), ResponseRoleEnum.ROLE_EXIT.getDesc()));

                }
                iAuthorityDao.saveRole(trole);
            } else {//编辑
                //如果总公司默认开启 判断是否已经有了
                if (trole.getCompanyDefault().toString().equals("1")){
                    Map map = new HashMap();
                    map.put("id",trole.getId());
                    Trole resultTrole = iAuthorityDao.checkRoleDefaultCompany(map);
                    if (resultTrole!=null){
                        return ResponseResult.error(new Error(ResponseRoleEnum.COMPANY_DEFAULT_ROLE_EXIT.getCode(), ResponseRoleEnum.COMPANY_DEFAULT_ROLE_EXIT.getDesc()));
                    }
                }
                //如果子公司默认开启 判断是否已经有了
                if (trole.getSubCompanyDefault().toString().equals("1")){
                    Map map = new HashMap();
                    map.put("id",trole.getId());
                    Trole resultTrole = iAuthorityDao.checkRoleDefaultSubCompany(map);
                    if (resultTrole!=null){
                        return ResponseResult.error(new Error(ResponseRoleEnum.SUB_COMPANY_DEFAULT_ROLE_EXIT.getCode(), ResponseRoleEnum.SUB_COMPANY_DEFAULT_ROLE_EXIT.getDesc()));
                    }
                }
                Trole trole1 = iAuthorityDao.selectRoleById(trole.getId());
                if (trole1 == null) {
                    return ResponseResult.error(new Error(ResponseRoleEnum.ROLE_EXIT.getCode(), ResponseRoleEnum.ROLE_EXIT.getDesc()));
                } else {
                    iAuthorityDao.updateRole(trole);
                }
            }
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseRoleEnum.SYSTEM_ERROR.getCode(), ResponseRoleEnum.SYSTEM_ERROR.getDesc()));
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleterole")
    public ResponseResult deleteuser(Trole trole) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (trole.getId() != null && !trole.getId().equals(0)) {
                Trole trole1 = iAuthorityDao.selectRoleById(trole.getId());
                if (trole1 == null) {
                    return ResponseResult.error(new Error(ResponseRoleEnum.DELETE_FAIL_NO_RECORD.getCode(), ResponseRoleEnum.DELETE_FAIL_NO_RECORD.getDesc()));
                } else {
                    //还需删除用户角色中间表
                    iAuthorityDao.deleteRoleUserByRoleId(trole.getId());
                    //还需删除菜单角色中间表
                    iAuthorityDao.deleteRoleMenuByRoleId(trole.getId());
                    iAuthorityDao.deleteRoleById(trole.getId());
                }
            } else {
                return ResponseResult.error(new Error(ResponseRoleEnum.DELETE_FAIL.getCode(), ResponseRoleEnum.DELETE_FAIL.getDesc()));
            }
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseRoleEnum.SYSTEM_ERROR.getCode(), ResponseRoleEnum.SYSTEM_ERROR.getDesc()));
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectRoleById")
    public ResponseResult selectRoleById(Trole trole) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (trole.getId() != null && !trole.getId().equals(0)) {
                trole = iAuthorityDao.selectRoleById(trole.getId());
                if (trole == null) {
                    return ResponseResult.error(new Error(ResponseRoleEnum.NO_RECORD.getCode(), ResponseRoleEnum.NO_RECORD.getDesc()));
                }
            } else {
                return ResponseResult.error(new Error(ResponseRoleEnum.NO_ID.getCode(), ResponseRoleEnum.NO_ID.getDesc()));
            }

            return ResponseResult.success(trole);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseRoleEnum.SYSTEM_ERROR.getCode(), ResponseRoleEnum.SYSTEM_ERROR.getDesc()));
        }
    }


    @ResponseBody
    @PostMapping("/loadCheckMenuInfo")
    public ResponseResult loadCheckMenuInfo(Integer parentId, Integer roleId) throws Exception {
        List<Tmenu> menuList = iAuthorityDao.selectMenusByRoleId(roleId);// 根据角色查询所有权限菜单信息
        //移除所有没有pid的menuid
        Iterator<Tmenu> it = menuList.iterator();
        while (it.hasNext()) {
            Tmenu tmenu = it.next();
            if (tmenu.getPId() == null) {
                it.remove();
            }
        }
        List<Integer> menuIdList = new LinkedList<Integer>();
        for (Tmenu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        String json = getAllCheckedMenuByParentId(parentId, menuIdList).toString();
        //System.out.println(json);

        return ResponseResult.success(json);
    }
    private JsonArray getAllCheckedMenuByParentId(Integer parentId, List<Integer> menuIdList) {
        JsonArray jsonArray = this.getCheckedMenuByParentId(parentId, menuIdList);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            //判断该节点下时候还有子节点
            if (iAuthorityService.selectCountByParentId(jsonObject.get("id").getAsInt()) == 0) {
                continue;
            } else {
                jsonObject.add("children", getAllCheckedMenuByParentId(jsonObject.get("id").getAsInt(), menuIdList));
            }
        }
        return jsonArray;
    }
    private JsonArray getCheckedMenuByParentId(Integer parentId, List<Integer> menuIdList) {
        List<Tmenu> menuList = (List<Tmenu>) iAuthorityService.selectTMenu(parentId).getResult();
        JsonArray jsonArray = new JsonArray();
        for (Tmenu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            Integer menuId = menu.getId();
            jsonObject.addProperty("id", menuId); // 节点id
            jsonObject.addProperty("name", menu.getName()); // 节点名称
            //判断该节点下时候还有子节点
            if (iAuthorityService.selectCountByParentId(parentId) == 0) {
                jsonObject.addProperty("open", "true"); // 无子节点
            } else {
                jsonObject.addProperty("open", "false"); // 有子节点
            }
            if (menuIdList.contains(menuId)) {
                jsonObject.addProperty("checked", true);
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }



    @ResponseBody
    @RequestMapping("/saveMenuSet")
    public ResponseResult saveMenuSet(String menuIds, Integer roleId) throws Exception {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();

        if (StringUtils.isNotEmpty(menuIds)) {
            //先根据roleid查询出原有的对应的所有menuid集合
            List<Tmenu> menuList = iAuthorityDao.selectMenusByRoleId(roleId);
            //移除所有没有pid的menuid
            Iterator<Tmenu> it = menuList.iterator();
            while (it.hasNext()) {
                Tmenu tmenu = it.next();
                if (tmenu.getPId() == null) {
                    it.remove();
                }
            }
            List<Integer> menuIdList = new LinkedList<Integer>();
            for (Tmenu menu : menuList) {
                menuIdList.add(menu.getId());
            }

            if (menuIdList != null && menuIdList.size() > 0) {
                //如果角色菜单中有了数据先删除
                iAuthorityDao.deleteRoleMenuByRoleId(roleId);
            }

            String idsStr[] = menuIds.split(",");
            for (int i = 0; i < idsStr.length; i++) { // 然后添加所有角色权限关联实体
                Trolemenu trolemenu = new Trolemenu();
                trolemenu.setRoleId(roleId);
                trolemenu.setMenuId(Integer.parseInt(idsStr[i]));
                iAuthorityDao.saveRoleMenu(trolemenu);
            }
        } else {
            return ResponseResult.error(new Error(ResponseRoleEnum.NO_CHOOSE.getCode(), ResponseRoleEnum.NO_CHOOSE.getDesc()));

        }
        return ResponseResult.success();

    }



}
