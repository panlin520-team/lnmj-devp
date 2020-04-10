package com.lnmj.authority.business;


import com.lnmj.authority.entity.*;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userService")
public interface IAuthorityService {
    //菜单
    ResponseResult selectTMenu(Integer parentId);
    List<Tmenu> selectByParentIdAndRoleId(Map map);
    List<Tmenu> selectByParentIdAndRoleIds(Integer pId,Integer userId);
    Integer selectCountByParentId(Integer parentId);
    Tmenu selectByKey(Integer parentId);
    List<Tmenu> selectByParentIdDes(Integer parentId);
    List<Tmenu> selectByState(Integer state);
    Integer saveMenu(Tmenu tmenu);
    Integer updateMenu(Tmenu tmenu);
    Integer deleteByRoleMenuId(Integer id);
    Integer deleteByParentId(Integer parentId);
    Integer deleteById(Integer parentId);
    List<Trolemenu> selectRoleMenuList();
    //角色
    List<Trole> selectRoleList(String keyword);
    List<Trole> selectRolesByUserId(Integer userId);
    ResponseResult selectRolesByUserIdPage(int pageNum, int pageSize,Integer userId);
    //用户
    List<Tuser> selectAdminUserListStore(Map map);
    List<Tuser> selectAdminUserListCompany(Map map);
    List<Tuser> selectAdminUserListSubCompany(Map map);
    List<Tuser> selectAdminUserListAllCompany(Map map);
    Tuser selectByUserName(Map map);
    Tuser selectStoreUserByUserNameAndStoreCode(Tuser tuser);
    Integer saveAdminUser(Tuser tuser);
    Tuser selectById(Integer userId);
    Integer updateAdminUser(Tuser tuser);
    Integer deleteUserRole(Integer userId);
    Integer deleteUser(Integer userId);
    Integer deleteuserByCompany(Tuser tuser);
    Integer saveUserRole(Tuserrole tuserrole);
    Boolean unlock(Integer userId,String password);
}
