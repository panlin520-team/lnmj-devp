package com.lnmj.authority.entity;

import lombok.Data;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/25 13:09
 * @Description:
 */
@Data
public class AdminUserLoginDTO {
    private JWT jwt;
    private Tuser user;
    private TStoreUser storeUser;
    private List<Tmenu> tmenu;
    private List<TStoreMenu> tStoreMenus;
    private Object roles;
    private Object auth;

}
