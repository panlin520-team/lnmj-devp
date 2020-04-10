package com.lnmj.authority.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "t_user_role")
@Data
public class TStoreuserrole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "store_id")
    private Integer storeId;


}