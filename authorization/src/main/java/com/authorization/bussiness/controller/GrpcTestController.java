package com.authorization.bussiness.controller;

import com.grpc.client.SystemGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/grpc")
public class GrpcTestController {

    @Autowired
    private SystemGrpcClient systemGrpcClient;

    @RequestMapping("/sayHello")
    public String printMessage(String name) {
        return systemGrpcClient.sayHello(name);
    }

}
