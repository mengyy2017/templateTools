package com.templateTools.utils;

import com.templateTools.base.entity.FailResp;
import com.templateTools.base.entity.Resp;
import com.templateTools.base.entity.SuccResp;

public class RespUtil extends ThreadLocal<Resp> {

    @Override
    public Resp get() {
        if (super.get() == null)
            return mkEmpSuccResp();
        else
            return super.get();
    }

    public SuccResp mkEmpSuccResp() {
            SuccResp succResp = new SuccResp();
            this.set(succResp);
            return succResp;
    }

    public FailResp mkFailSuccResp(String msg) {
        FailResp failResp = new FailResp(msg);
        this.set(failResp);
        return failResp;
    }

}
