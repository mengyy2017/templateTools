package com.common.bussiness.entity;

import javax.persistence.Id;

public class BaseEntity {

    @Id
    public String id;

    public Integer isdel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

}
