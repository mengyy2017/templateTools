package com.templateTools.base.entity;

public class Test {
    public static void main(String[] args) {
        SuccResp succResp = new SuccResp("1234");
        SuccResp succResp1 = new SuccResp("55555");
        Object a = succResp.get("data");
        Object a1 = succResp1.get("data");
        String aa = "";
    }
}
