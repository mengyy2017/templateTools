package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.spider.bussiness.service.ESService;
import com.spider.pub.consts.Consts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

@Controller
@RequestMapping(value = "/py")
public class PyController extends BaseController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ESService esService;

    // private static String pyPath = "python D:\\Dev\\WorkSpace\\pythonWorkspace\\collect\\spider\\sina_main.py ";
    private static String pyPath = "python E:\\Yonyou\\WorkSpace\\pythonWorkspace\\spider\\spider\\sina_main.py ";

    @RequestMapping("/exeSearch")
    @ResponseBody
    public Resp<String> exeSearch(String searchUser) {
        try {
            Process process = Runtime.getRuntime().exec(pyPath + searchUser);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    if(StringUtils.isNotBlank(line)){
                        line = line.replaceAll(".*\\w.*\\d.*", "").replaceAll("\\w", "")
                            .replaceAll("[`#$%^&*()_\\-+=<>:\"{}|,.\\/;'\\[\\]·~！@#￥%……&*（）——\\-+={}|《》【】]", "");
                        if(StringUtils.isNotBlank(line)) {
                            System.out.println(line + "\n");
                            rabbitTemplate.convertAndSend("FanoutExchange1","",line, new CorrelationData(UUID.randomUUID().toString()));
                        }
                    }
                }

                rabbitTemplate.convertAndSend("FanoutExchange1","","搜索完毕", new CorrelationData(UUID.randomUUID().toString()));
            }
            System.out.println("\n\n-----------------------------------------------------------------\n");
            try (BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))){
                String line;
                while ((line = errorBufferedReader.readLine()) != null){
                    System.out.println(line + "\n");
                }
            }
            process.waitFor();

            esService.deleteAllDoc(Consts.indexName);
            esService.addRelationDoc(Consts.indexName);

//            for(int i = 0; i < 100; i++)
//                rabbitTemplate.convertAndSend("FanoutExchange1","","aaaaaaaa", new CorrelationData(UUID.randomUUID().toString()));
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

}
