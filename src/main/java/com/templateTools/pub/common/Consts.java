package com.templateTools.pub.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Consts {
    public static String CAMEL_TABLE_NAME = "camelTableName";
    public static String UPPER_CAMEL_TABLE_NAME = "upperCamelTableName";
    public static String COLUMNLIST = "columnList";

    public static String JAVA_SUFFIX = ".java";
    public static String beanFtl = "Bean.ftl";

    public static Path basePath = Paths.get("C:", "src");
    public static Path beanFilePath = basePath.resolve("java");



}
