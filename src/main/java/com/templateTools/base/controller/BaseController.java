package com.templateTools.base.controller;

import com.templateTools.base.entity.FailResp;
import com.templateTools.base.entity.Resp;
import com.templateTools.base.entity.SuccResp;
import com.templateTools.utils.BuildUtil;

public class BaseController extends BuildUtil {

    public static ThreadLocal<Resp> respResult = new ThreadLocal();

    Resp mkSuccResp(Object data) {
        if (respResult.get() == null || respResult.get() instanceof FailResp) {
            SuccResp succResp = oneConstr(SuccResp::new, data);
            // setVals0(respResult, getVAndF0(data, ThreadLocal::set));
            respResult.set(succResp);
            return succResp;
        } else
            return respResult.get().setData(data);
    }

    Resp mkFailResp(Object data) {
        if (respResult.get() == null || respResult.get() instanceof SuccResp) {
            FailResp failResp = oneConstr(FailResp::new, data);
            respResult.set(failResp);
            return failResp;
        } else
            return respResult.get().setData(data);
    }


}
