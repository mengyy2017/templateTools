package com.templateTools.pub.common;

import com.templateTools.utils.BuildUtil;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Consts {
    public static String JAVA_SUFFIX = ".java", HTML_SUFFIX = ".html", XML_SUFFIX = ".xml";

    public static String fileSparator = "/", basePath = "C:" + File.separator + "template";

    public static String TABLENAME = "tableName", MODULENAME = "moduleName", COLUMNLIST = "columnList",
            UPPER_CAMEL_TABLE_NAME = "upperCamelTableName", CAMEL_TABLE_NAME = "camelTableName";

    public static String bussiClassiPath = basePath + File.separator + "bussi", // bussiness
            mapperClassiPath = basePath  + File.separator + "mapper", // resource
            actionClassiPath = basePath + File.separator + "controller", // action
            pageClassiPath = basePath  + File.separator + "page"; // page

    public static String entityTypePath = "entity", daoTypePath = "dao",
            serviceTypePath = "service", serviceImplTypePath = "service" + File.separator + "impl";

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
