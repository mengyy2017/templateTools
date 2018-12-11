package com.templateTools.utils;

import com.templateTools.pub.common.Consts;
import freemarker.template.Configuration;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FreeMarkerUtil {

    private static Configuration configuration;

    static {
        configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getResource("/template").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputBean(Map<String, Object> data) {
        try {
            Path beanPath = Consts.beanFilePath.resolve(data.get(Consts.UPPER_CAMEL_TABLE_NAME).toString() + Consts.JAVA_SUFFIX);
            if(!Files.exists(beanPath.getParent()))
                Files.createDirectories(beanPath.getParent());
            try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanPath.toFile())))){
                configuration.getTemplate(Consts.beanFtl).process(data, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
