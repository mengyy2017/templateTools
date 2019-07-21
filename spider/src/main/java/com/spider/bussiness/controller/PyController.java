package com.spider.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
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

    @RequestMapping("/exePy")
    @ResponseBody
    public Resp<String> exeSearch(String searchUser) {
        try {
            // Process process = Runtime.getRuntime().exec("python D:\\Dev\\WorkSpace\\pythonWorkspace\\collect\\spider\\sina_main.py " + searchUser);
            // try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            //     String line;
            //     while ((line = bufferedReader.readLine()) != null){
            //         System.out.println(line + "\n");
            //         rabbitTemplate.convertAndSend("FanoutExchange1","",line, new CorrelationData(UUID.randomUUID().toString()));
            //     }
            // }
            // System.out.println("\n\n-----------------------------------------------------------------\n");
            // try (BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))){
            //     String line;
            //     while ((line = errorBufferedReader.readLine()) != null){
            //         System.out.println(line + "\n");
            //     }
            // }
            // process.waitFor();
            rabbitTemplate.convertAndSend("FanoutExchange1","","aaaaaaaa", new CorrelationData(UUID.randomUUID().toString()));
        } catch (Exception e) {
            mkFailResp(e);
        }
        return respResult.get();
    }

}
