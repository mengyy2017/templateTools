package com.common.pub.pubBo;

import com.common.pub.consts.RespConsts;
import java.util.HashMap;

public class Resp<T> extends HashMap<String,Object>{

    public Resp() {
    }

    public Resp(T respData) {
        this.put("respData", respData);
    }

    public T getRespData() {
        Object respData = this.get("respData");
        if(respData != null)
            return (T) respData;
        else
            return null;
    }

    public Resp setRespData(T respData) {
        this.put("respData", respData);
        return this;
    }

    public Resp setCode(int code) {
        this.put(RespConsts.CODE, code);
        return this;
    }

    public Resp setMsg(String msg) {
        this.put(RespConsts.MSG, msg);
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

