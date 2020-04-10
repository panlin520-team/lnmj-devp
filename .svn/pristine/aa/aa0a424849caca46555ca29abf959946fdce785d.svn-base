package com.lnmj.authority.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class TStoreMenu {
    private Integer id;
    private Integer storeId;
    private Integer isSuperMenu;
    private String icon;
    private String name;
    private Integer state;
    private String url;
    private Integer pId;
    @Transient
    private List<TChildMenu> childMenu;

}