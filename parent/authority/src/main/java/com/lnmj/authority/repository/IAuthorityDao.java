package com.lnmj.authority.repository;

import com.lnmj.authority.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/5/17 11:15
 * @Description: 权限dao
 */
public interface IAuthorityDao {
    //菜单
    List<Tmenu> selectTMenu(Integer pId);
    List<Tmenu> selectByParentIdAndRoleId(Map map);
    List<Tmenu> selectByParentIdAndRoleIds(Map map);
    Integer selectCountByParentId(Integer parentId);
    Tmenu selectByKey(Integer id);
    List<Tmenu> selectByParentIdDes(Integer parentId);
    List<Tmenu> selectByState(Integer state);
    Integer saveMenu(Tmenu tmenu);
    Integer updateMenu(Tmenu tmenu);
    Integer deleteByRoleMenuId(Integer id);
    Integer deleteByParentId(Integer parentId);
    Integer deleteById(Integer id);
    List<Trolemenu> selectRoleMenuList();
    //角色
    List<Trole> selectRoleList(String keyword);
    Integer selectRoleByName(String name);
    Trole checkRoleDefaultCompany(Map map);
    Trole checkRoleDefaultSubCompany(Map map);
    Integer saveRole(Trole trole);
    Trole selectRoleById(Integer id);
    Integer updateRole(Trole trole);
    Integer deleteRoleUserByRoleId(Integer id);
    Integer deleteRoleMenuByRoleId(Integer id);
    Integer deleteRoleById(Integer id);
    List<Tmenu> selectMenusByRoleId(Integer id);
    Integer saveRoleMenu(Trolemenu trolemenu);
    List<Trole> selectRolesByUserId(Integer userId);
    //用户
    List<Tuser> selectAdminUserListCompany(Map map);
    List<Tuser> selectAdminUserListSubCompany(Map map);
    List<Tuser> selectAdminUserListStore(Map map);
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






}
