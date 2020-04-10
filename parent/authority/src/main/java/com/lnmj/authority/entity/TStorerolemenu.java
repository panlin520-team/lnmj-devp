package com.lnmj.authority.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "t_role_menu")
@Data
public class TStorerolemenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "store_id")
    private Integer storeId;


}