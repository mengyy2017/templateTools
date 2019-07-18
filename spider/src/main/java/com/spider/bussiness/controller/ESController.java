package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.common.pub.pubInter.AnalyzerAnnotation;
import com.spider.bussiness.entity.*;
import com.spider.bussiness.service.CommentInfoService;
import com.spider.bussiness.service.UInfoService;
import com.spider.bussiness.service.WbInfoService;
import com.spider.pub.conf.elasticsearch.EClient;
import com.spider.pub.consts.Consts;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/es")
public class ESController extends BaseController {

    @Autowired
    private UInfoService uInfoService;
    @Autowired
    private WbInfoService wbInfoService;
    @Autowired
    private CommentInfoService commentInfoService;
    @Autowired
    private EClient eClient;

    @RequestMapping("/createIndex")
    @ResponseBody
    public Resp<String> createIndex() {
        try {

            List<NgramAnalyzerInfo> ngramAnalyzerInfoList = Stream.of(
                    newAndSet(NgramAnalyzerInfo::new, getVAndF(Consts.ngram, NgramAnalyzerInfo::setNgramType),
                            getVAndF(AnalyzerAnnotation.AnalyzerName.ngramAnalyzer, NgramAnalyzerInfo::setAnalyzerName),
                            getVAndF("ngram_tokenizer", NgramAnalyzerInfo::setTokenizerName))
                    , newAndSet(NgramAnalyzerInfo::new, getVAndF(Consts.edge_ngram, NgramAnalyzerInfo::setNgramType),
                            getVAndF(AnalyzerAnnotation.AnalyzerName.edgeNgramAnalyzer, NgramAnalyzerInfo::setAnalyzerName),
                            getVAndF("edge_ngram_tokenizer", NgramAnalyzerInfo::setTokenizerName),
                            getVAndF(10, NgramAnalyzerInfo::setMaxGram))
            ).collect(Collectors.toList());

            eClient.createIndex(Consts.indexName, UInfo.class, ngramAnalyzerInfoList);
            mkSuccResp("123456");
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/matchPhrase")
    @ResponseBody
    public Resp<String> matchPhrase(String indexName, String fieldName, String phrase) {
        try {

            List<SearchHit> searchHitList = eClient.matchPhrase("wb", fieldName, phrase);

            List<SearchResult> searchResultList = searchHitList.parallelStream().map(result -> {
                SearchResult searchResult = newAndSet0(SearchResult::new, new String[]{result.getId(),
                                (String) result.getSourceAsMap().get(fieldName)},
                        SearchResult::setValue, SearchResult::setText);
                return searchResult;
            }).collect(Collectors.toList());

            mkSuccResp(searchResultList);
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

    @RequestMapping("/addWb")
    @ResponseBody
    public Resp<String> addWb() {
        try {

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
