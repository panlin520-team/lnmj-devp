package com.lnmj.authority.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.authority.business.IAuthorityService;
import com.lnmj.authority.entity.*;
import com.lnmj.authority.repository.IAuthorityDao;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAuthEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeExperiencecardEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("userService")
public class AuthorityService implements IAuthorityService {
    @Resource
    private IAuthorityDao iAuthorityDao;

    @Override
    public ResponseResult selectTMenu(Integer parentId) {
        List<Tmenu> voList = iAuthorityDao.selectTMenu(parentId);
        if (voList!=null&& voList.size()!=0){
            return ResponseResult.success(voList);
        }else{
            return ResponseResult.error(new Error(ResponseCodeAuthEnum.AUTH_MENU_NULL.getCode(),ResponseCodeAuthEnum.AUTH_MENU_NULL.getDesc()));
        }
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleId(Map map) {
        List<Tmenu> voList = iAuthorityDao.selectByParentIdAndRoleId(map);
        return voList;
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleIds(Integer pId,Integer userId) {
        //根据id查看用户的权限
        List<Trole> troles= iAuthorityDao.selectRolesByUserId(userId);
        List<String> roleIdList = new ArrayList<>();
        for (Trole trole: troles) {
            roleIdList.add(trole.getId().toString());
        }
        if (roleIdList.size()==0){
            return null;
        }
        Map map = new HashMap();
        map.put("list",roleIdList);
        map.put("pId",pId);
        List<Tmenu> voList = iAuthorityDao.selectByParentIdAndRoleIds(map);
        return voList;
    }


    @Override
    public Integer selectCountByParentId(Integer parentId) {
        return iAuthorityDao.selectCountByParentId(parentId);
    }

    @Override
    public Tmenu selectByKey(Integer id) {
        return iAuthorityDao.selectByKey(id);
    }



    @Override
    public  List<Tmenu> selectByParentIdDes(Integer parentId) {
        return iAuthorityDao.selectByParentIdDes(parentId);
    }

    @Override
    public List<Tmenu> selectByState(Integer state) {
        return iAuthorityDao.selectByState(state);
    }

    @Override
    public Integer saveMenu(Tmenu tmenu) {
        return iAuthorityDao.saveMenu(tmenu);
    }

    @Override
    public Integer updateMenu(Tmenu tmenu) {
        return iAuthorityDao.updateMenu(tmenu);
    }

    @Override
    public Integer deleteByRoleMenuId(Integer id) {
        return iAuthorityDao.deleteByRoleMenuId(id);
    }

    @Override
    public Integer deleteByParentId(Integer parentId) {
        return iAuthorityDao.deleteByParentId(parentId);
    }

    @Override
    public Integer deleteById(Integer id) {
        return iAuthorityDao.deleteById(id);
    }

    @Override
    public List<Trolemenu> selectRoleMenuList() {
        return iAuthorityDao.selectRoleMenuList();
    }

    @Override
    public List<Trole> selectRoleList(String keyword) {
        return iAuthorityDao.selectRoleList(keyword);
    }

    @Override
    public List<Trole> selectRolesByUserId(Integer userId) {
        return iAuthorityDao.selectRolesByUserId(userId);
    }

    @Override
    public ResponseResult selectRolesByUserIdPage(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Trole> troleList = iAuthorityDao.selectRolesByUserId(userId);
        if (troleList.size() > 0) {
            PageInfo pageInfo = new PageInfo(troleList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAuthEnum.USER_ROLE_NULL.getCode(), ResponseCodeAuthEnum.USER_ROLE_NULL.getDesc()));

    }

    @Override
    public List<Tuser> selectAdminUserListStore(Map map) {
        return iAuthorityDao.selectAdminUserListStore(map);
    }

    @Override
    public List<Tuser> selectAdminUserListCompany(Map map) {
        return iAuthorityDao.selectAdminUserListCompany(map);
    }

    @Override
    public List<Tuser> selectAdminUserListSubCompany(Map map) {
        return iAuthorityDao.selectAdminUserListSubCompany(map);
    }

    @Override
    public List<Tuser> selectAdminUserListAllCompany(Map map) {
        return iAuthorityDao.selectAdminUserListAllCompany(map);
    }

    @Override
    public Tuser selectByUserName(Map map) {
        return iAuthorityDao.selectByUserName(map);
    }

    @Override
    public Tuser selectStoreUserByUserNameAndStoreCode(Tuser tuser) {
        return iAuthorityDao.selectStoreUserByUserNameAndStoreCode(tuser);
    }

    @Override
    public Integer saveAdminUser(Tuser tuser) {
        return iAuthorityDao.saveAdminUser(tuser);
    }

    @Override
    public Tuser selectById(Integer userId) {
        return iAuthorityDao.selectById(userId);
    }

    @Override
    public Integer updateAdminUser(Tuser tuser) {
        return iAuthorityDao.updateAdminUser(tuser);
    }

    @Override
    public Integer deleteUserRole(Integer userId) {
        return iAuthorityDao.deleteUserRole(userId);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return iAuthorityDao.deleteUser(userId);
    }

    @Override
    public Integer deleteuserByCompany(Tuser tuser) {
        return iAuthorityDao.deleteuserByCompany(tuser);
    }

    @Override
    public Integer saveUserRole(Tuserrole tuserrole) {
        return iAuthorityDao.saveUserRole(tuserrole);
    }

    @Override
    public Boolean unlock(Integer userId, String password) {
        //获取管理id对应密码
        Tuser tuser = iAuthorityDao.selectById(userId);
        String userPassword = tuser.getPassword();
        boolean flag = new BCryptPasswordEncoder().matches(password, userPassword);

        return flag;
    }

}
