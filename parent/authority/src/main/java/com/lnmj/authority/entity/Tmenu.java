package com.lnmj.authority.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class Tmenu {
    private Integer id;
    private String icon;
    private String name;
    private Integer state;
    private String url;
    private Integer pId;


}