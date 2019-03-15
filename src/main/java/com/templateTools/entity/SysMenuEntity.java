package com.templateTools.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_menu")
public class SysMenuEntity {

    @Id
    private String id;

    private String url;

    private String permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
