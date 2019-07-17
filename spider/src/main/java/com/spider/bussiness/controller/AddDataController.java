package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.entity.CommentInfo;
import com.spider.bussiness.entity.UInfo;
import com.spider.bussiness.entity.WbInfo;
import com.spider.bussiness.service.CommentInfoService;
import com.spider.bussiness.service.UInfoService;
import com.spider.bussiness.service.WbInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping(value = "/addData")
public class AddDataController extends BaseController {

    @Autowired
    private UInfoService uInfoService;
    @Autowired
    private WbInfoService wbInfoService;
    @Autowired
    private CommentInfoService commentInfoService;
    @Autowired
    private EClient eClient;

    @RequestMapping("/addWb")
    @ResponseBody
    public Resp<String> addWb() {
        try {

            eClient.createIndex("wb", UInfo.class, null);

            addRelationDoc("wb");

            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/addWb0")
    @ResponseBody
    public Resp<String> addWb0() {
        try {

            addRelationDoc("wb0");

            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/addWb1")
    @ResponseBody
    public Resp<String> addWb1() {
        try {

            addRelationDoc("wb1");

            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/addWb2")
    @ResponseBody
    public Resp<String> addWb2() {
        try {

            addRelationDoc("wb2");

            mkSuccResp("111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }


    void addRelationDoc(String indexName) throws Exception {
        List<UInfo> uInfoList = uInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, uInfoList, Boolean.FALSE);

        List<WbInfo> wbInfoList = wbInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, wbInfoList, Boolean.TRUE);

        List<CommentInfo> commentInfoList = commentInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, commentInfoList, Boolean.TRUE);
    }

}
