package com.templateTools.base.controller;

import com.templateTools.base.entity.FailResp;
import com.templateTools.base.entity.Resp;
import com.templateTools.base.entity.SuccResp;
import com.templateTools.utils.BuildUtil;

public class BaseController extends BuildUtil {

    private static ThreadLocal<Resp> respLocal = new ThreadLocal();

    private static ThreadLocal<SuccResp> succLocal = new ThreadLocal();

    private static ThreadLocal<FailResp> failLocal = new ThreadLocal();

    Resp succResp(Object data) {
        if (succLocal.get() == null) {
            SuccResp succResp = oneConstr(SuccResp::new, data);
            succLocal.set(succResp);
            return succResp;
        } else {
            return succLocal.get();
        }

    }


}
