package com.common.bussiness.entity;

import javax.persistence.Id;

public class BaseEntity {

    @Id
    protected String id;

    protected Integer isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

}
