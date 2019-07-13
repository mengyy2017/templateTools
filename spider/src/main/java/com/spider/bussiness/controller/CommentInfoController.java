package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.entity.CommentInfo;
import com.spider.bussiness.service.CommentInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping(value = "/commentInfo")
public class CommentInfoController extends BaseController {

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private EClient eClient;

    @RequestMapping("/addRelationDoc")
    @ResponseBody
    public Resp<String> createIndex() {
        try {
            List<CommentInfo> commentInfoList = commentInfoService.selectAll();
            eClient.addRelationDoc("wb", commentInfoList.get(30), Boolean.TRUE);
            mkSuccResp(commentInfoList);
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

}
