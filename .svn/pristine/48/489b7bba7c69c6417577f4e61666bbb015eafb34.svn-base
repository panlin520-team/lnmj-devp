package com.lnmj.authservice.Entity;


import com.lnmj.authservice.Entity.base.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "u_user", schema = "test")
/*@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)*/
public class Account extends BaseEntity implements UserDetails, Serializable  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(nullable = false,  unique = true)
  private String account;
  @Column
  private String password;
  @Column
  private Integer userType;
  @Column
  private String mobile;
  @Column
  private String email;
  @Column
  private String name;
  @Column
  private Integer sex;
  @Column
  private String nickName;
  @Column
  private Integer idType;
  @Column
  private String idNumber;
  @Column
  private String openId;
  @Column
  private Integer provinceId;
  @Column
  private Integer cityId;
  @Column
  private Integer countyId;
  @Column
  private String streetAddress;
  @Column
  private String addressDetail;
  @Column
  private String cardNumber;
  @Column
  private Integer membershipLevelId;
  @Column
  private String membershipLevelName;
  @Column
  private String registrationChannel;
  @Column
  private Date registrationTime;
  @Column
  private Integer emailAuthentication;
  @Column
  private Integer mobileAuthentication;
  @Column
  private Long parentId;
  @Column
  private String userIdentityKey;
  @Column
  private Integer storeId;
  @Column
  private Integer isEnable;
  @Column
  private Integer isLogin;
  @Transient
  private List<PermissionView> PermissionViews;  //用户拥有的资源权限-按钮


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return PermissionViews;
  }

  public void setAuthorities(List<PermissionView> authorities) {
    this.PermissionViews = authorities;
  }

  @Override
  public String getUsername() {
    return account;
  }

  public void setUsername(String account) {
    this.account = account;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public String toString() {
    return "Account{" +
            "userId=" + userId +
            ", account='" + account + '\'' +
            ", password='" + password + '\'' +
            ", userType=" + userType +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", sex=" + sex +
            ", nickName='" + nickName + '\'' +
            ", idType=" + idType +
            ", idNumber='" + idNumber + '\'' +
            ", openId='" + openId + '\'' +
            ", provinceId=" + provinceId +
            ", cityId=" + cityId +
            ", countyId=" + countyId +
            ", streetAddress='" + streetAddress + '\'' +
            ", addressDetail='" + addressDetail + '\'' +
            ", cardNumber='" + cardNumber + '\'' +
            ", membershipLevelId=" + membershipLevelId +
            ", membershipLevelName='" + membershipLevelName + '\'' +
            ", registrationChannel='" + registrationChannel + '\'' +
            ", registrationTime=" + registrationTime +
            ", emailAuthentication=" + emailAuthentication +
            ", mobileAuthentication=" + mobileAuthentication +
            ", parentId=" + parentId +
            ", userIdentityKey='" + userIdentityKey + '\'' +
            ", storeId=" + storeId +
            ", isEnable=" + isEnable +
            ", isLogin=" + isLogin +
            ", PermissionViews=" + PermissionViews +
            '}';
  }
}


/*  @Transient
  private Collection<? extends GrantedAuthority> authorities;//角色权限
*/




