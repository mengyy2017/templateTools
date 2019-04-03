package com.common.pub.pubBo;

import com.common.pub.consts.RespConsts;

public class FailResp extends Resp {

    public FailResp(String msg) {
        this.setCode(RespConsts.CODE_SERVER_ERR);
        this.setMsg(msg);
    }

}
