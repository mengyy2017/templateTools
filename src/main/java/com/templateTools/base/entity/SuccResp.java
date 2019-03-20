package com.templateTools.base.entity;

public class SuccResp extends Resp {

//    private static ThreadLocal succRestLocal = new ThreadLocal();

    public SuccResp(Object data) {
        this.put("data", data);
    }

}
