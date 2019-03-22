package com.templateTools.pub.common;

import com.templateTools.utils.BuildUtil;
import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Consts extends BuildUtil {

    public static String LOGIN_CHEK_URL = "/j_logi";

    public static String LOGI_CONN = "logiConn";

    public static String JAVA_SUFFIX = ".java", HTML_SUFFIX = ".html", XML_SUFFIX = ".xml";

    public static String fileSparator = "/", basePath = "C:" + File.separator + "template";

    public static String TABLENAME = "tableName", MODULENAME = "moduleName", COLUMNLIST = "columnList",
            UPPER_CAMEL_TABLE_NAME = "upperCamelTableName", CAMEL_TABLE_NAME = "camelTableName";

    public static String bussiClassiPath = "", //"bussi", // bussiness
            mapperClassiPath = "mapper", // resource
            actionClassiPath = "controller", // action
            pageClassiPath = "page"; // page

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

    public static TreeMap outParamMap;

    static {
        outParamMap = newAndPuts(TreeMap::new, TreeMap::put
                        , bussiClassiPath + "_" + JAVA_SUFFIX, Arrays.asList(entityTypePath + "_Bean.ftl_@@Entity",
                                                        serviceImplTypePath + "_ServiceImpl.ftl_@@ServiceImpl",
                                                        serviceTypePath + "_Service.ftl_I@@Service",
                                                        daoTypePath + "_Dao.ftl_@@Mapper")
                                                        // asList每个字段是 "xxx_xxx.ftl_xxx@@xxx"
                                                        // 以_分割 分成3份
                                                        // [0]fileTypeName [1]ftlName  [2]outPutFileName(其中@@会被实体名替换)
                        , mapperClassiPath + "_" + XML_SUFFIX, Arrays.asList("_Mapper.ftl_@@Mapper", "_FieldMapper.ftl_@@FieldMapper")
                        , actionClassiPath + "_" + JAVA_SUFFIX, Arrays.asList("_Action.ftl_@@Controller")
                        , pageClassiPath + "_" + HTML_SUFFIX, Arrays.asList("_ListPage.ftl_list@@", "_NewPage.ftl_new@@",
                                                        "_AddPage.ftl_add@@", "_EditPage.ftl_edit@@")

            );
    }

}
