package com.templateTools.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        HashMap<Object, Object> a = BuildUtil.newAndPuts(HashMap::new, HashMap::put,
                Stream.of("123", Arrays.asList("999")).collect(Collectors.toCollection(LinkedList::new)));
        String aa = "";
    }
}
