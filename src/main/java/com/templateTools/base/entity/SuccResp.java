package com.templateTools.base.entity;

import com.templateTools.pub.common.RespConsts;

public class SuccResp extends Resp {

    public SuccResp() {
        this.setCode(RespConsts.CODE_OK);
        this.setMsg(RespConsts.MSG_SUCCESS);
    }

    public SuccResp(Object data) {
        super(data);
        this.setCode(RespConsts.CODE_OK);
        this.setMsg(RespConsts.MSG_SUCCESS);
    }

}
