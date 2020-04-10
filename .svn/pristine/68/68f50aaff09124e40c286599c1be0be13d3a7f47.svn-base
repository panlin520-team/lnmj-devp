package com.lnmj.authservice.Entity;

import com.lnmj.authservice.Entity.base.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 */
@Data
@Entity
@Table(name = "u_permission_info", schema = "test")
public class PermissionView extends BaseEntity implements GrantedAuthority {
    @Id
    private long id;
    @Column
    private String permissionCode;
    @Column
    private String permissionMethodType;
    @Column
    private String permissionName;
    @Column
    private String permissionType;
    @Column
    private String permissionUri;

    @Override
    public String getAuthority() {
        return permissionUri;
    }

    @Override
    public String toString() {
        return permissionUri;
    }
}
