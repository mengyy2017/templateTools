package com.templateTools.utils;

import com.templateTools.entity.model.CreateInfo;
import com.templateTools.pub.common.Consts;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FreeMarkerUtil {

    public static void outputFile(Map<String, Object> dataMap){

        String codePackage = CreateInfo.creInfoFromToken().getCodePackage();

        Consts.outParamMap.keySet().stream().forEach(key -> {

            String[] keyArr = key.toString().split("_");
            Path classiPath = Paths.get(keyArr[0]); String suffix = keyArr[1];

            String moduleName = dataMap.get(Consts.MODULENAME).toString();
            String upperCamelTableName = dataMap.get(Consts.UPPER_CAMEL_TABLE_NAME).toString();

            List<String> ftlsAndParamList = (List<String>)Consts.outParamMap.get(key);

            ftlsAndParamList.stream().forEach(ftlParam -> {
                String[] ftlParamArr = ftlParam.split("_");
                String fileTypeName = ftlParamArr[0];
                String ftlName = ftlParamArr[1];
                String outPutFileName = ftlParamArr[2].replace("@@", upperCamelTableName);

                Path outFilePath = Paths.get(Consts.basePath + File.separator + codePackage.replace(".", "\\")).resolve(classiPath).resolve(moduleName).resolve(fileTypeName)
                        .resolve(outPutFileName + suffix);

                if (Consts.daoTypePath.equals(fileTypeName) && dataMap.get("namespace") == null)
                    dataMap.put("namespace", getDotAllPath(outFilePath).replace("Mapper", ""));
                else if (Consts.entityTypePath.equals(fileTypeName) && dataMap.get("") == null)
                    dataMap.put("entityDotAllPath", getDotAllPath(outFilePath));

                processData(ftlName, dataMap, outFilePath);
            });
        });
    }

    private static String getDotAllPath(Path path) {
        return path.toString().replaceAll("\\..*", "").replaceAll(".*?com", "com").replace("\\", ".");
    }

    private static void processData(String ftlName,Map<String, Object> data, Path outFilePath){
        try {
            if(FreeMarkerUtil.class.getResource(Consts.fileSparator + "template" + Consts.fileSparator + ftlName) != null){
                Writer writer = createOutPutFile(outFilePath);
                Consts.configuration.getTemplate(ftlName).process(data, writer);
                writer.flush(); writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Writer createOutPutFile(Path outFilePath){
        Writer writer = null;
        try {
            if(!Files.exists(outFilePath.getParent()))
                Files.createDirectories(outFilePath.getParent());
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFilePath.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

}
