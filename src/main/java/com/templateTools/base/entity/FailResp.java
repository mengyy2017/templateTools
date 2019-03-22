package com.templateTools.base.entity;

import com.templateTools.pub.common.RespConsts;

public class FailResp extends Resp {

    public FailResp(String msg) {
        this.setCode(RespConsts.CODE_SERVER_ERR);
        this.setMsg(msg);
    }

}
