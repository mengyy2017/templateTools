package com.templateTools.base.controller;

import com.templateTools.base.entity.FailResp;
import com.templateTools.base.entity.Resp;
import com.templateTools.base.entity.SuccResp;
import com.templateTools.utils.BuildUtil;
import com.templateTools.utils.RespUtil;

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
