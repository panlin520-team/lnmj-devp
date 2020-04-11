package com.lnmj.authority.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_role")
@Data
public class TStoreRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer isSuperAdmin;

    private String bz;

    private String name;

    private Integer storeId;

    private String remarks;


}