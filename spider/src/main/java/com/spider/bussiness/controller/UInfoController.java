package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.entity.UInfo;
import com.spider.bussiness.service.UInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping(value = "/uInfo")
public class UInfoController extends BaseController {

    @Autowired
    private UInfoService uInfoService;

    @Autowired
    private EClient eClient;

    @RequestMapping("/test")
    @ResponseBody
    public Resp<String> login() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.addDoc("u_info", uInfoList.get(0));
            mkSuccResp("123456");
        } catch (Exception e) {
            mkFailResp(e.getMessage());
        }
        return respResult.get();
    }

}
