package com.templateTools.utils;

import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.model.DatabaseModel;
import com.templateTools.pub.common.Consts;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandelDataUtil {

    public static Map convertData(DatabaseModel databaseModel){
        Map databaseMap = new HashMap();

        String camelTableName = convert2Camel(databaseModel.getTableName());
        String upperCamelTableName = convert2UpperCamel(camelTableName);
        databaseMap.put(Consts.TABLENAME, databaseModel.getTableName());
        databaseMap.put(Consts.MODULENAME, databaseModel.getTableName().split("_")[0]);
        databaseMap.put(Consts.CAMEL_TABLE_NAME, camelTableName);
        databaseMap.put(Consts.UPPER_CAMEL_TABLE_NAME, upperCamelTableName);

        List<ColumnEntity> columnList = databaseModel.getColList().stream()
                .map(columnEntity -> columnEntity2Java(columnEntity)).collect(Collectors.toList());
        databaseMap.put(Consts.COLUMNLIST, columnList);

        return databaseMap;
    }

    private static ColumnEntity columnEntity2Java(ColumnEntity columnEntity){
        String columnName = columnEntity.getColumnName();

        String camelColName = convert2Camel(columnName);
        String upperCamelColName = convert2UpperCamel(camelColName);
        columnEntity.setCamelColName(camelColName);
        columnEntity.setUpperCamelColName(upperCamelColName);

        setColumnJavaType(columnEntity);

        return columnEntity;
    }

    private static void setColumnJavaType(ColumnEntity columnEntity){
        switch (columnEntity.getDataType()){
            case "char":
                columnEntity.setJavaType("String");
                break;
            case "varchar":
                columnEntity.setJavaType("String");
                break;
            case "text":
                columnEntity.setJavaType("String");
                break;
            case "integer":
                columnEntity.setJavaType("long");
                break;
            case "samllint":
                columnEntity.setJavaType("int");
                break;
            case "int":
                columnEntity.setJavaType("int");
                break;
            case "decimal":
                columnEntity.setJavaType("double");
                break;
            case "date":
                columnEntity.setJavaType("Date");
                break;
            case "datetime":
                columnEntity.setJavaType("Date");
                break;
            default:
                columnEntity.setJavaType("String");
                break;
        }
    }

    public static String convert2Camel(String nameInDatabase){
        Pattern pattern = Pattern.compile("_(.)");
        Matcher matcher = pattern.matcher(nameInDatabase);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()){
            matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String convert2UpperCamel(String camelName){
        char[] charArr = camelName.toCharArray();
        charArr[0] -= 32;
        return  String.valueOf(charArr);
    }
}
