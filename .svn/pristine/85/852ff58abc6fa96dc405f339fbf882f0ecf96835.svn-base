package com.lnmj.authservice.Dao;

import com.lnmj.authservice.Entity.Account;
import com.lnmj.authservice.Entity.PermissionView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/25 12:27
 * @Description:
 */
@Mapper
public interface UserDao  {
    Account findByAccount(@Param("account")String account);
    Account findByEmail(@Param("email")String email);
    Account findByMobile(@Param("mobile")String mobile);
    List<PermissionView> findPermisByAccount(@Param("account")String account);
}