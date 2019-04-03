package com.template.utils;

import com.alibaba.fastjson.JSONObject;
import com.template.base.entity.FailResp;
import com.template.base.entity.Resp;
import com.template.base.entity.SuccResp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RespUtil extends ThreadLocal<Resp> {

    @Override
    public Resp get() {
        System.out.println("----------" + Thread.currentThread());
        System.out.println("----------" + super.get());
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

    public static HttpServletResponse printFailResponse(String errorMessage, int code, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("json/application");
        resp.setStatus(code);
        resp.getWriter().print(JSONObject.toJSON(new FailResp(errorMessage).setCode(code)));
        return resp;
    }

}
