package com.templateTools.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String str = "C://template\\com\\yishi\\AuditSatusMapper.java";
        Pattern p = Pattern.compile("(\\w)*\\..*");
        Matcher matcher = p.matcher(str);
        System.out.println(matcher.find());
        System.out.println(matcher.group()); // AuditSatusMapper.java

        List l = new ArrayList();
        l.sort((l1,l2) -> 1);
//        Collections.sort();

//        System.out.println(str.replaceAll("\\\\((?!\\\\).)*", ""));
    }
}
