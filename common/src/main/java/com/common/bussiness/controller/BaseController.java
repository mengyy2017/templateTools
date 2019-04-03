package com.common.bussiness.controller;

import com.common.pub.pubBo.FailResp;
import com.common.pub.pubBo.Resp;
import com.common.pub.pubBo.SuccResp;
import com.common.util.BuildUtil;
import com.common.util.RespUtil;

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
