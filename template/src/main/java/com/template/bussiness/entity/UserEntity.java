package com.template.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "sys_user")
public class UserEntity extends BaseEntity implements Serializable {

    private String username;

    private String password;

    @Transient
    private List<RoleEntity> roleEntityList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoleEntityList() {
        return roleEntityList;
    }

    public void setRoleEntityList(List<RoleEntity> roleEntityList) {
        this.roleEntityList = roleEntityList;
    }
}
