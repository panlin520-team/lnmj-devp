package com.lnmj.authority.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_role")
public class Trole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bz;

    private String name;

    private String remarks;

    private Integer companyDefault;

    private Integer subCompanyDefault;
    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return bz
     */
    public String getBz() {
        return bz;
    }

    /**
     * @param bz
     */
    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getCompanyDefault() {
        return companyDefault;
    }

    public void setCompanyDefault(Integer companyDefault) {
        this.companyDefault = companyDefault;
    }

    public Integer getSubCompanyDefault() {
        return subCompanyDefault;
    }

    public void setSubCompanyDefault(Integer subCompanyDefault) {
        this.subCompanyDefault = subCompanyDefault;
    }
}