package com.spider.bussiness.service;

import com.spider.bussiness.entity.CommentInfo;
import com.spider.bussiness.entity.UInfo;
import com.spider.bussiness.entity.WbInfo;
import com.spider.pub.conf.elasticsearch.EClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class ESService {

    @Autowired
    private UInfoService uInfoService;
    @Autowired
    private WbInfoService wbInfoService;
    @Autowired
    private CommentInfoService commentInfoService;
    @Autowired
    private EClient eClient;

    public void deleteAllDoc(String indexName) throws IOException {
        eClient.deleteAllDoc(indexName);
    }

    public void addRelationDoc(String indexName) throws Exception {

        List<UInfo> uInfoList = uInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, uInfoList, Boolean.FALSE);

        List<WbInfo> wbInfoList = wbInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, wbInfoList, Boolean.TRUE);

        List<CommentInfo> commentInfoList = commentInfoService.selectAll();
        eClient.bulkAddRelationDoc(indexName, commentInfoList, Boolean.TRUE);
    }
}
