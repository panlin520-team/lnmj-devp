package com.lnmj.authority.entity;

import lombok.Data;

import javax.persistence.*;
@Data
public class TStoreUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bz;

    private String password;

    @Column(name = "true_name")
    private String trueName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "store_code")
    private String storeCode;

    @Column(name = "store_id")
    private String storeId;

    private String remarks;

}