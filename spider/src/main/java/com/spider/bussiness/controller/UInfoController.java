package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.entity.UInfo;
import com.spider.bussiness.service.UInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import com.spider.pub.consts.Consts;
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

    @RequestMapping("/addDoc")
    @ResponseBody
    public Resp<String> addDoc() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.addDoc(Consts.indexName, uInfoList.get(0));
            mkSuccResp("7890");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/addRelationDoc")
    @ResponseBody
    public Resp<String> addRelationDoc() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.addRelationDoc(Consts.indexName, uInfoList.get(0), Boolean.FALSE);
            mkSuccResp("7890");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/bulkAdd")
    @ResponseBody
    public Resp<String> bulkAdd() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.bulkAddDoc(Consts.indexName, uInfoList);
            mkSuccResp("11111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/bulkAddRelationDoc")
    @ResponseBody
    public Resp<String> bulkAddRelationDoc() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.bulkAddRelationDoc(Consts.indexName, uInfoList, Boolean.FALSE);
            mkSuccResp("11111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/deleteDoc")
    @ResponseBody
    public Resp<String> deleteDoc() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.deleteDoc(Consts.indexName, uInfoList.get(5).getId());
            mkSuccResp("7890");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/bulkDelete")
    @ResponseBody
    public Resp<String> bulkDelete() {
        try {
            List<UInfo> uInfoList = uInfoService.selectAll();
            eClient.bulkDeleteDoc(Consts.indexName, uInfoList);
            mkSuccResp("11111");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }


}
