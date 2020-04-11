package com.lnmj.authority.controller.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lnmj.authority.business.IAuthorityService;
import com.lnmj.authority.entity.Tmenu;
import com.lnmj.authority.entity.TStoreMenu;
import com.lnmj.authority.entity.Trole;
import com.lnmj.authority.entity.Trolemenu;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseMenuEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @param
 * @Author: panlin
 * @Description:
 * @Date: 2019/6/20 11:18
 */
@Controller
@RequestMapping("/manager/menu")
public class MenuController {
    @Resource
    private IAuthorityService iAuthorityService;

    //根据角色id 查看角色所有的一级菜单及二级菜单
    @ResponseBody
    @GetMapping("/loadMenuInfo")
    public String loadMenuInfo(Integer parentId, Integer roleId) {
        long startTime = System.currentTimeMillis(); //获取开始时间
        //查询所有的一级菜单
        List<Tmenu> menuListOne1 = iAuthorityService.selectByState(1);
        List<Tmenu> menuListOne = new ArrayList<>();
        //查询所有的二级菜单
        List<Tmenu> menuListTwo1 = iAuthorityService.selectByState(2);
        List<Tmenu> menuListTwo = new ArrayList<>();
        //查询所有的三级菜单
        List<Tmenu> menuListThree1 = iAuthorityService.selectByState(3);
        List<Tmenu> menuListThree = new ArrayList<>();
        //查看所有角色菜单中间表
        List<Trolemenu> trolemenuList = iAuthorityService.selectRoleMenuList();
        for (Trolemenu trolemenu : trolemenuList) {
            for (Tmenu tmenu : menuListOne1) {
                if (trolemenu.getMenuId().toString().equals(tmenu.getId().toString()) && trolemenu.getRoleId().toString().equals(roleId.toString())) {
                    menuListOne.add(tmenu);
                }
            }
        }
        for (Trolemenu trolemenu : trolemenuList) {
            for (Tmenu tmenu : menuListTwo1) {
                if (trolemenu.getMenuId().toString().equals(tmenu.getId().toString()) && trolemenu.getRoleId().toString().equals(roleId.toString())) {
                    menuListTwo.add(tmenu);
                }
            }
        }
        for (Trolemenu trolemenu : trolemenuList) {
            for (Tmenu tmenu : menuListThree1) {
                if (trolemenu.getMenuId().toString().equals(tmenu.getId().toString()) && trolemenu.getRoleId().toString().equals(roleId.toString())) {
                    menuListThree.add(tmenu);
                }
            }
        }


        //给所有一级菜单添加二级菜单列表
        JsonObject jsonObjectOne = new JsonObject();
        for (Tmenu tmenuOne : menuListOne) {
            JsonArray jsonArrayTwo = new JsonArray();
            for (Tmenu tmenuTwo : menuListTwo) {
                JsonObject jsonObjectTwo = new JsonObject();
                if (tmenuOne.getId().equals(tmenuTwo.getPId())) {
                    jsonObjectTwo.addProperty("id", tmenuTwo.getId()); // 节点id
                    jsonObjectTwo.addProperty("title", tmenuTwo.getName()); // 节点名称
                    jsonObjectTwo.addProperty("spread", false); // 不展开
                    jsonObjectTwo.addProperty("icon", tmenuTwo.getIcon());
                    if (StringUtils.isNotEmpty(tmenuTwo.getUrl())) {
                        jsonObjectTwo.addProperty("href", tmenuTwo.getUrl()); // 菜单请求地址
                    }
                    JsonArray jsonArrayThree = new JsonArray();
                    for (Tmenu tmenuThree : menuListThree) {
                        JsonObject jsonObjectThree = new JsonObject();
                        if (tmenuTwo.getId().equals(tmenuThree.getPId())) {
                            jsonObjectThree.addProperty("id", tmenuThree.getId()); // 节点id
                            jsonObjectThree.addProperty("title", tmenuThree.getName()); // 节点名称
                            jsonObjectThree.addProperty("spread", false); // 不展开
                            jsonObjectThree.addProperty("icon", tmenuThree.getIcon());
                            jsonObjectThree.addProperty("href", tmenuThree.getUrl());
                            jsonArrayThree.add(jsonObjectThree);
                        }

                    }
                    jsonObjectTwo.add("children", jsonArrayThree);
                    jsonArrayTwo.add(jsonObjectTwo);
                }
            }
            jsonObjectOne.add(tmenuOne.getName(), jsonArrayTwo);
        }
        /* String json=getAllMenuByParentId(parentId,1).toString();*/
        String json = jsonObjectOne.toString();
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        return json;
    }

    private JsonObject getAllMenuByParentId(Integer parentId, Integer roleId) {
        JsonObject resultObject = new JsonObject();
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);//得到所有一级菜单
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            //判断该节点下时候还有子节点
            if (iAuthorityService.selectCountByParentId(parentId) == 0) {
                //如果没有子节点
                continue;
            } else {
                //如果子节点
                //由于后台模板的规定，一级菜单以title最为json的key
                resultObject.add(jsonObject.get("title").getAsString(), getAllMenuJsonArrayByParentId(jsonObject.get("id").getAsInt(), roleId));
            }
        }
        return resultObject;
    }

    private JsonArray getAllMenuJsonArrayByParentId(Integer parentId, Integer roleId) {
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            //判断该节点下是否还有子节点
            if (iAuthorityService.selectCountByParentId(parentId) == 0) {
                continue;
            } else {
                //二级或三级菜单
                jsonObject.add("children", getAllMenuJsonArrayByParentId(jsonObject.get("id").getAsInt(), roleId));
            }
        }
        return jsonArray;
    }

    private JsonArray getMenuByParentId(Integer parentId, Integer roleId) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("pId", parentId);
        paraMap.put("roleId", roleId);
        List<Tmenu> menuList = iAuthorityService.selectByParentIdAndRoleId(paraMap);
        JsonArray jsonArray = new JsonArray();
        for (Tmenu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", menu.getId()); // 节点id
            jsonObject.addProperty("title", menu.getName()); // 节点名称
            jsonObject.addProperty("spread", false); // 不展开
            jsonObject.addProperty("icon", menu.getIcon());
            if (StringUtils.isNotEmpty(menu.getUrl())) {
                jsonObject.addProperty("href", menu.getUrl()); // 菜单请求地址
            }
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }


    @ResponseBody
    @PostMapping("/loadCheckMenuInfo")
    public ResponseResult loadCheckMenuInfo(Integer parentId) {
        String json = getAllMenuByParentId(parentId).toString();
        return ResponseResult.success(json);
    }

    private JsonArray getAllMenuByParentId(Integer parentId) {
        JsonArray jsonArray = this.getMenuByParentId(parentId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            //判断该节点下时候还有子节点
            if (iAuthorityService.selectCountByParentId(jsonObject.get("id").getAsInt()) == 0) {
                continue;
            } else {
                jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt()));
            }
        }
        return jsonArray;
    }

    private JsonArray getMenuByParentId(Integer parentId) {
        List<Tmenu> menuList = (List<Tmenu>) iAuthorityService.selectTMenu(parentId).getResult();
        JsonArray jsonArray = new JsonArray();
        for (Tmenu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            Integer menuId = menu.getId();
            jsonObject.addProperty("id", menuId); // 节点id
            jsonObject.addProperty("name", menu.getName()); // 节点名称
            //判断该节点下是否还有子节点
            //if (menu.getState() == 1) {
            if (iAuthorityService.selectCountByParentId(parentId) == 0) {
                jsonObject.addProperty("open", "false"); // 无子节点
            } else {
                jsonObject.addProperty("open", "true"); // 有子节点
            }
            jsonObject.addProperty("state", String.valueOf(menu.getState()));
            jsonObject.addProperty("iconValue", menu.getIcon());
            jsonObject.addProperty("pId", String.valueOf(menu.getPId()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @ResponseBody
    @RequestMapping(value = "/selectMenuById")
    public ResponseResult selectMenuById(Integer id) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (id == null || id == 0) {
                return ResponseResult.error(new Error(ResponseMenuEnum.CAN_NOT_GET_NODE_ID.getCode(), ResponseMenuEnum.CAN_NOT_GET_NODE_ID.getDesc()));
            } else {
                Tmenu tmenu = iAuthorityService.selectByKey(id);
                if (tmenu == null) {
                    return ResponseResult.error(new Error(ResponseMenuEnum.CAN_NOT_GET_NODE_OBJ.getCode(), ResponseMenuEnum.CAN_NOT_GET_NODE_OBJ.getDesc()));
                } else {
                    return ResponseResult.success(tmenu);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseMenuEnum.SYSTEM_ERROR.getCode(), ResponseMenuEnum.SYSTEM_ERROR.getDesc()));
        }

    }

    @ResponseBody
    @RequestMapping(value = "/addupdatemenu")
    public ResponseResult addupdatemenu(HttpSession session, Tmenu tmenu) {
        try {
            if (tmenu.getId() == null) {//新建
                //校验是否提交了pId
                if (tmenu.getPId() == null || tmenu.getPId() == 0) {
                    return ResponseResult.error(new Error(ResponseMenuEnum.CAN_NOT_GET_PARENT_ID.getCode(), ResponseMenuEnum.CAN_NOT_GET_PARENT_ID.getDesc()));
                } else {
                    Tmenu pmenu = iAuthorityService.selectByKey(tmenu.getPId());//父节点对象
                    if (pmenu.getState() == 3) {
                        return ResponseResult.error(new Error(ResponseMenuEnum.THREE_MENU_CAN_NOT_ADD_CHILD_NODE.getCode(), ResponseMenuEnum.THREE_MENU_CAN_NOT_ADD_CHILD_NODE.getDesc()));
                    }
                    if ("-1".equalsIgnoreCase(String.valueOf(pmenu.getPId()))
                            && "1".equalsIgnoreCase(String.valueOf(pmenu.getState()))) {//如果父节点是最顶级那一个，则本次新增为一级菜单

                        //一级菜单的名字不可为纯数字
                        if (isNumeric(tmenu.getName())) {
                            return ResponseResult.error(new Error(ResponseMenuEnum.ONE_MENU_NAME_CAN_NOT_NUMBER.getCode(), ResponseMenuEnum.ONE_MENU_NAME_CAN_NOT_NUMBER.getDesc()));
                        }

                        tmenu.setState(1);
                    } else if ("1".equalsIgnoreCase(String.valueOf(pmenu.getPId()))
                            && "1".equalsIgnoreCase(String.valueOf(pmenu.getState()))) {//如果父节点是一级菜单，本次新增为2级菜单
                        tmenu.setState(2);
                    } else if (!"1".equalsIgnoreCase(String.valueOf(pmenu.getPId()))
                            && "2".equalsIgnoreCase(String.valueOf(pmenu.getState()))) {//如果父节点是二级菜单，本次新增为3级菜单
                        tmenu.setState(3);
                    }

                    //指定pid的值，根据id倒序查询同级菜单集合
                    List<Tmenu> list = iAuthorityService.selectByParentIdDes(tmenu.getPId());
                    if (list != null && list.size() > 0) {//如果本次新增的菜单实体的同一级菜单集合不为空
                        tmenu.setId(list.get(0).getId() + 1);//获取已经存在的同级菜单的id的最大值+1
                    } else {//如果本次新增的菜单实体还没有同一级的菜单的话，则根据父节点生成子节点id
                        if ("1".equalsIgnoreCase(String.valueOf(tmenu.getPId()))) {
                            tmenu.setId(tmenu.getPId() * 10);//第一个一级菜单id为1*10
                        } else {
                            tmenu.setId(tmenu.getPId() * 100);//二级三级菜单id生成策略为根据父菜单id*100
                        }
                    }

                }

                iAuthorityService.saveMenu(tmenu);
            } else {//编辑(对于节点的编辑只允许编辑icon、name、url)
                //首先校验本次编辑操作提交的菜单对象中的name属性的值是否存在
                Tmenu tmenuNew = new Tmenu();
                tmenuNew.setId(tmenu.getId());
                if (StringUtils.isNotEmpty(tmenu.getIcon())) {
                    tmenuNew.setIcon(tmenu.getIcon());
                }
                if (StringUtils.isNotEmpty(tmenu.getName())) {
                    tmenuNew.setName(tmenu.getName());
                }
                if (StringUtils.isNotEmpty(tmenu.getUrl())) {
                    tmenuNew.setUrl(tmenu.getUrl());
                }
                iAuthorityService.updateMenu(tmenuNew);
            }


            return ResponseResult.success(String.valueOf(tmenu.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseMenuEnum.SYSTEM_ERROR.getCode(), ResponseMenuEnum.SYSTEM_ERROR.getDesc()));
        }

    }

    @ResponseBody
    @RequestMapping(value = "/deletemenu")
    public ResponseResult deletemenu(HttpSession session, Tmenu tmenu) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tmenu.getId() != null && !tmenu.getId().equals(0)) {
                Tmenu menu = iAuthorityService.selectByKey(tmenu.getId());
                if (menu == null) {
                    return ResponseResult.error(new Error(ResponseMenuEnum.DELETE_FAIL_NO_RECORD.getCode(), ResponseMenuEnum.DELETE_FAIL_NO_RECORD.getDesc()));
                } else {
                    //还需删除中间表
                    if (true) {
                        iAuthorityService.deleteByRoleMenuId(tmenu.getId());
                    }
                    //删除该节点的所有子节点
                    if (true) {
                        iAuthorityService.deleteByParentId(tmenu.getId());
                    }
                    //删除该节点
                    iAuthorityService.deleteById(tmenu.getId());

                }
            } else {
                return ResponseResult.error(new Error(ResponseMenuEnum.DELETE_FAIL.getCode(), ResponseMenuEnum.DELETE_FAIL.getDesc()));
            }
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseMenuEnum.SYSTEM_ERROR.getCode(), ResponseMenuEnum.SYSTEM_ERROR.getDesc()));
        }
    }


}
