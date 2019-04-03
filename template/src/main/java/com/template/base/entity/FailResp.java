package com.template.base.entity;

import com.template.pub.common.RespConsts;

public class FailResp extends Resp {

    public FailResp(String msg) {
        this.setCode(RespConsts.CODE_SERVER_ERR);
        this.setMsg(msg);
    }

}
