package com.templateTools.base.entity;

import com.templateTools.pub.common.ResultConsts;
import java.util.HashMap;

public class Resp extends HashMap<String,Object>{

    public Resp() {
    }

    public Resp(Object data) {
        this.put("data", data);
    }

    public static Resp instance(){
        return new Resp();
    }

    public Resp fail(){
        return fail(ResultConsts.CODE_ERR);
    }
    public Resp fail(int code){
        return this.setErrorCode(code);
    }

    public Resp success(){
        return this.setErrorCode(ResultConsts.CODE_SUCCESS);
    }

    public Resp setErrorCode(int code){
        this.put(ResultConsts.ERROR_CODE,code);
        return this;
    }

    public Resp setErrorMessage(String errorMessage) {
        this.put(ResultConsts.ERROR_MSG, errorMessage);
        return this;
    }

    public Object getData() {
        return this.get("data");
    }

    public Resp setData(Object data) {
        this.put("data", data);

        return this;
    }

    public Resp addAttr(String key, Object value) {
        this.put(key,value);
        return this;
    }

    public Object getAttr(String key) {
        return this.get(key);
    }

}

