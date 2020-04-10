package com.lnmj.authservice.service;

import com.lnmj.authservice.Dao.UserDao;
import com.lnmj.authservice.Entity.Account;
import com.lnmj.authservice.common.CheckMailUtil;
import com.lnmj.authservice.serviceProxy.RoleApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: panlin
 * @Date: 2019/4/25 12:27
 * @Description:
 */
@Service
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    private UserDao userRepository;
    @Resource
    private CheckMailUtil checkMailUtil;
    @Resource
    private RoleApi roleApi;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("xixi"+userRepository.findByAccount(username));
        Account user = null;
        if (checkMailUtil.isEmail(username)){
            user=userRepository.findByEmail(username);
        }else if (StringUtils.contains(username,"lnmj_")){
            user=userRepository.findByAccount(username);
        }else{
            user=userRepository.findByMobile(username);
        }
        return user;
    }
}