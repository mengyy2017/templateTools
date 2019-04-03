package com.template.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
import javax.persistence.Table;

@Table(name = "sys_menu")
public class MenuEntity extends BaseEntity {

    private String url;

    private String permission;

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
}
