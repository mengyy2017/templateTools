package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.entity.WbInfo;
import com.spider.bussiness.service.WbInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import com.spider.pub.consts.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping(value = "/wbInfo")
public class WbInfoController extends BaseController {
    
    @Autowired
    private WbInfoService wbInfoService;

    @Autowired
    private EClient eClient;

    @RequestMapping("/addRelationDoc")
    @ResponseBody
    public Resp<String> addRelationDoc() {
        try {
            List<WbInfo> wbInfoList = wbInfoService.selectAll();
            eClient.addRelationDoc(Consts.indexName, wbInfoList.get(0), Boolean.TRUE);
            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/bulkAddRelationDoc")
    @ResponseBody
    public Resp<String> bulkAddRelationDoc() {
        try {
            List<WbInfo> wbInfoList = wbInfoService.selectAll();
            eClient.bulkAddRelationDoc(Consts.indexName, wbInfoList, Boolean.TRUE);
            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

}
