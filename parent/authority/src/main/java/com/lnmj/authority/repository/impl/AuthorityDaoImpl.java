package com.lnmj.authority.repository.impl;

import com.lnmj.authority.entity.*;
import com.lnmj.authority.repository.IAuthorityDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/5/17 11:15
 * @Description: 权限dao
 */
@Repository
public class AuthorityDaoImpl extends BaseDao implements IAuthorityDao {
    @Override
    public List<Tmenu> selectTMenu(Integer pId) {
        return selectList("authority.selectTMenu", pId);
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleId(Map map) {
        return selectList("authority.selectByParentIdAndRoleId", map);
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleIds(Map map) {
        return selectList("authority.selectByParentIdAndRoleIds", map);
    }

    @Override
    public Integer selectCountByParentId(Integer parentId) {
        return select("authority.selectCountByParentId", parentId);
    }

    @Override
    public Tmenu selectByKey(Integer id) {
        return select("authority.selectByKey", id);
    }


    @Override
    public List<Tmenu> selectByParentIdDes(Integer parentId) {
        return selectList("authority.selectByParentIdDes", parentId);
    }

    @Override
    public List<Tmenu> selectByState(Integer state) {
        return selectList("authority.selectByState", state);
    }

    @Override
    public Integer saveMenu(Tmenu tmenu) {
        return insert("authority.saveMenu", tmenu);
    }

    @Override
    public Integer updateMenu(Tmenu tmenu) {
        return update("authority.updateMenu", tmenu);
    }

    @Override
    public Integer deleteByRoleMenuId(Integer id) {
        return delete("authority.deleteByRoleMenuId", id);
    }

    @Override
    public Integer deleteByParentId(Integer parentId) {
        return delete("authority.deleteByParentId", parentId);
    }

    @Override
    public Integer deleteById(Integer id) {
        return delete("authority.deleteById", id);
    }

    @Override
    public List<Trolemenu> selectRoleMenuList() {
        return selectList("authority.selectRoleMenuList");
    }

    @Override
    public List<Trole> selectRoleList(String keyword) {
        return selectList("authority.selectRoleList", keyword);
    }

    @Override
    public Integer selectRoleByName(String name) {
        return select("authority.selectRoleByName", name);
    }

    @Override
    public Trole checkRoleDefaultCompany(Map map) {
        return select("authority.checkRoleDefaultCompany", map);
    }

    @Override
    public Trole checkRoleDefaultSubCompany(Map map) {
        return select("authority.checkRoleDefaultSubCompany", map);
    }

    @Override
    public Integer saveRole(Trole trole) {
        return insert("authority.saveRole", trole);
    }

    @Override
    public Trole selectRoleById(Integer id) {
        return select("authority.selectRoleById", id);
    }

    @Override
    public Integer updateRole(Trole trole) {
        return update("authority.updateRole", trole);
    }

    @Override
    public Integer deleteRoleUserByRoleId(Integer id) {
        return delete("authority.deleteRoleUserByRoleId", id);
    }

    @Override
    public Integer deleteRoleMenuByRoleId(Integer id) {
        return delete("authority.deleteRoleMenuByRoleId", id);
    }

    @Override
    public Integer deleteRoleById(Integer id) {
        return delete("authority.deleteRoleById", id);
    }

    @Override
    public List<Tmenu> selectMenusByRoleId(Integer id) {
        return selectList("authority.selectMenusByRoleId", id);
    }

    @Override
    public Integer saveRoleMenu(Trolemenu trolemenu) {
        return insert("authority.saveRoleMenu", trolemenu);
    }

    @Override
    public List<Trole> selectRolesByUserId(Integer userId) {
        return selectList("authority.selectRolesByUserId", userId);
    }

    @Override
    public List<Tuser> selectAdminUserListCompany(Map map) {
        return selectList("authority.selectAdminUserListCompany", map);
    }

    @Override
    public List<Tuser> selectAdminUserListSubCompany(Map map) {
        return selectList("authority.selectAdminUserListSubCompany", map);
    }

    @Override
    public List<Tuser> selectAdminUserListStore(Map map) {
        return selectList("authority.selectAdminUserListStore", map);
    }

    @Override
    public List<Tuser> selectAdminUserListAllCompany(Map map) {
        return selectList("authority.selectAdminUserListAllCompany", map);
    }

    @Override
    public Tuser selectByUserName(Map map) {
        return select("authority.selectByUserName", map);
    }

    @Override
    public Tuser selectStoreUserByUserNameAndStoreCode(Tuser tuser) {
        return select("authority.selectStoreUserByUserNameAndStoreCode", tuser);
    }

    @Override
    public Integer saveAdminUser(Tuser tuser) {
        return insert("authority.saveAdminUser", tuser);
    }

    @Override
    public Tuser selectById(Integer userId) {
        return select("authority.selectById", userId);
    }

    @Override
    public Integer updateAdminUser(Tuser tuser) {
        return update("authority.updateAdminUser", tuser);
    }

    @Override
    public Integer deleteUserRole(Integer userId) {
        return delete("authority.deleteUserRole", userId);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return delete("authority.deleteUser", userId);
    }

    @Override
    public Integer deleteuserByCompany(Tuser tuser) {
        return delete("authority.deleteuserByCompany", tuser);
    }

    @Override
    public Integer saveUserRole(Tuserrole tuserrole) {
        return insert("authority.saveUserRole", tuserrole);
    }


}
