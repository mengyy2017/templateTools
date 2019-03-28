package com.templateTools.base.entity;

import javax.persistence.Id;

public class BaseEntity {

    @Id
    private String id;

    public Integer isdel;

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
