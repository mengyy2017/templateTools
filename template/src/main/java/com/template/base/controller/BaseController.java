package com.template.base.controller;

import com.template.base.entity.FailResp;
import com.template.base.entity.Resp;
import com.template.base.entity.SuccResp;
import com.template.utils.BuildUtil;
import com.template.utils.RespUtil;

public class BaseController extends BuildUtil {

    public static RespUtil respResult = new RespUtil();

    public Resp mkSuccResp(Object data) {
        Resp succResp = respResult.get();

        if (succResp instanceof FailResp)
            return respResult.mkEmpSuccResp().setRespData(data);
        else
            return succResp.setRespData(data);

    }

    public Resp mkFailResp(String msg) {
        Resp failResp = respResult.get();

        if (failResp instanceof SuccResp)
            return respResult.mkFailSuccResp(msg);
        else
            return failResp.setMsg(msg);

    }

}
