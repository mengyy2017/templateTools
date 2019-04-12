package com.system.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import com.common.pub.pubBo.MenuFunInter;

import javax.persistence.Table;

@Table(name = "sys_menu")
public class MenuEntity extends BaseEntity implements MenuFunInter {

    private String url;

    private String permission;

    private String module;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
