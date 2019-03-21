package com.templateTools.base.controller;

import com.templateTools.base.entity.FailResp;
import com.templateTools.base.entity.Resp;
import com.templateTools.base.entity.SuccResp;
import com.templateTools.utils.BuildUtil;

public class BaseController extends BuildUtil {

    private static ThreadLocal<Resp> respLocal = new ThreadLocal();

    Resp succResp(Object data) {
        if (respLocal.get() == null || respLocal.get() instanceof FailResp) {
            SuccResp succResp = oneConstr(SuccResp::new, data);
            respLocal.set(succResp);
            return succResp;
        } else {
            return respLocal.get();
        }
    }

    Resp failResp(Object data) {
        if (respLocal.get() == null || respLocal.get() instanceof SuccResp) {
            FailResp failResp = oneConstr(FailResp::new, data);
            respLocal.set(failResp);
            return failResp;
        } else {
            return respLocal.get();
        }
    }


}
