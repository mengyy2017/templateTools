package com.templateTools.utils;

import freemarker.template.Configuration;
import java.io.*;
import java.util.Map;

public class FreeMarkerUtil {

    private static Configuration configuration;

    static {
        configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getResource("template").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputFile(String templateName, String filePath, Map<String, Object> data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            configuration.getTemplate(templateName).process(data, writer);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
