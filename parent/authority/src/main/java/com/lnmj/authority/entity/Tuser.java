package com.lnmj.authority.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_user")
@Data
public class Tuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bz;

    private String password;

/*    @Column(name = "true_name")*/
    private String trueName;

   /* @Column(name = "user_name")*/
    private String userName;

    /*@Column(name = "store_id")*/
    private String storeId;

    /*@Column(name = "company_id")*/
    private String companyId;

   /* @Column(name = "company_type")*/
    private String companyType;

  /*  @Column(name = "is_super_admin")*/
    private Integer isSuperAdmin;

    private String remarks;

    @Transient
    private List<Trole> roleList;//zjt


    @Transient
    private String roles;//zjt

    @Transient
    private String oldPassword;

    @Transient
    private String companyName;

    @Transient
    private Integer roleId;

    @Transient
    private String storeCode;

    @Transient
    private Long industry;


    @Transient
    private String stockCode;
    @Transient
    private String stockId;
    @Transient
    private String orgK3Number;
    @Transient
    private Long parentCompanyId;
    @Transient
    private Long parentParentCompanyId;
}