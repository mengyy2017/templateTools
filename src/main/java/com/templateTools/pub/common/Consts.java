package com.templateTools.pub.common;

import com.templateTools.utils.BuildUtil;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Consts {
    public static String TABLENAME = "tableName";
    public static String MODULENAME = "moduleName";
    public static String CAMEL_TABLE_NAME = "camelTableName";
    public static String UPPER_CAMEL_TABLE_NAME = "upperCamelTableName";
    public static String COLUMNLIST = "columnList";

    public static String JAVA_SUFFIX = ".java";
    public static String HTML_SUFFIX = ".html";
    public static String XML_SUFFIX = ".xml";

    public static String fileSparator = "/";

    public static String basePath = "C:" + File.separator + "template";

    public static String bussiClassiPath = basePath + File.separator + "bussi"; // bussiness
    public static String mapperClassiPath = basePath  + File.separator + "mapper"; // resource
    public static String actionClassiPath = basePath + File.separator + "controller"; // action
    public static String pageClassiPath = basePath  + File.separator + "page"; // page

    public static String entityTypePath = "entity";
    public static String daoTypePath = "dao";
    public static String serviceTypePath = "service";
    public static String serviceImplTypePath = "service" + File.separator + "impl";

    public static Configuration configuration;

    static {
        configuration = new Configuration();
        try {
            URL url = Consts.class.getResource( Consts.fileSparator+ "template");
            if(url != null)
                configuration.setDirectoryForTemplateLoading(new File(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap outParamMap;

    static {
        outParamMap = BuildUtil.newAndPuts(HashMap::new, HashMap::put, mapperClassiPath + "_" + XML_SUFFIX, Arrays.asList("_Mapper.ftl_@@Mapper")
                        , actionClassiPath + "_" + JAVA_SUFFIX, Arrays.asList("_Action.ftl_@@Controller")
                        , pageClassiPath + "_" + HTML_SUFFIX, Arrays.asList("_ListPage.ftl_list@@", "_NewPage.ftl_new@@",
                                                                            "_AddPage.ftl_add@@", "_EditPage.ftl_edit@@"
                                                                        )
                        , bussiClassiPath + "_" + JAVA_SUFFIX, Arrays.asList(entityTypePath + "_Bean.ftl_@@", daoTypePath + "_Dao.ftl_@@Mapper",
                                                                            serviceImplTypePath + "_ServiceImpl.ftl_@@ServiceImpl",
                                                                            serviceTypePath + "_Service.ftl_I@@Service"
                                                                        )
        );
    }

}
