package com.templateTools.pub.common;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consts {
    public static String TABLENAME = "tableName";
    public static String MODULENAME = "moduleName";
    public static String CAMEL_TABLE_NAME = "camelTableName";
    public static String UPPER_CAMEL_TABLE_NAME = "upperCamelTableName";
    public static String COLUMNLIST = "columnList";

    public static String JAVA_SUFFIX = ".java";
    public static String HTML_SUFFIX = ".html";
    public static String XML_SUFFIX = ".xml";

    public static String basePath = "C://template";

    // bussiness
    public static String bussiClassiPath = basePath + "bussi";
    public static String entityTypePath = "entity";
    public static String daoTypePath = "dao";
    public static String serviceTypePath = "service";
    public static String serviceImplTypePath = "service//impl";

    // resource
    public static String mapperClassiPath = basePath + "mapper";

    // action
    public static String actionClassiPath = basePath + "controller";

    // page
    public static String pageClassiPath = basePath + "page";


    // param 1.xxx.ftl 2.classification  +|模块名|+  3.type 4.suffix
//    Map bussiMap = Stream.of("bussi")
    public static List<String> beanList = Arrays.asList("Bean.ftl", bussiClassiPath, entityTypePath, JAVA_SUFFIX);
    public static List<String> daoList = Arrays.asList("Dao.ftl", bussiClassiPath, daoTypePath, JAVA_SUFFIX);
    public static List<String> serviceList = Arrays.asList("Service.ftl", bussiClassiPath, serviceTypePath, JAVA_SUFFIX);
    public static List<String> serviceImplList = Arrays.asList("ServiceImpl.ftl", bussiClassiPath, serviceImplTypePath, JAVA_SUFFIX);

    // mapper
    public static List<String> mapperList = Arrays.asList("Mapper.ftl", mapperClassiPath, "", XML_SUFFIX);

    // action
    public static List<String> actionList = Arrays.asList("Action.ftl", actionClassiPath, "", JAVA_SUFFIX);

    // page
    public static List<String> listPageList = Arrays.asList("ListPage.ftl", pageClassiPath, "", HTML_SUFFIX);
    public static List<String> newPageList = Arrays.asList("NewPage.ftl", pageClassiPath, "", HTML_SUFFIX);
    public static List<String> addPageList = Arrays.asList("AddPage.ftl", pageClassiPath, "", HTML_SUFFIX);
    public static List<String> editPageList = Arrays.asList("EditPage.ftl", pageClassiPath, "", HTML_SUFFIX);

//    Map<Integer, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));

    List<Object> listBussi = Arrays.asList(bussiClassiPath, JAVA_SUFFIX,
            Arrays.asList(
                    Arrays.asList("Bean.ftl", entityTypePath),
                    Arrays.asList("Dao.ftl", daoTypePath),
                    Arrays.asList("Service.ftl", serviceTypePath),
                    Arrays.asList("ServiceImpl.ftl", serviceImplTypePath)
            )
    );

    List<Object> listMapper = Arrays.asList(mapperClassiPath, XML_SUFFIX, "Mapper.ftl");

    List<Object> actionMapper = Arrays.asList(actionClassiPath, JAVA_SUFFIX, "Action.ftl");

    List<Object> pageMapper = Arrays.asList(pageClassiPath, HTML_SUFFIX,
            Arrays.asList(
                    Arrays.asList("ListPage.ftl", entityTypePath),
                    Arrays.asList("NewPage.ftl", daoTypePath),
                    Arrays.asList("AddPage.ftl", serviceTypePath),
                    Arrays.asList("EditPage.ftl", serviceImplTypePath)
            )
    );



}
