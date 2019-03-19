package com.templateTools.utils;

import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.model.TableColsInfo;
import com.templateTools.pub.common.Consts;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandelDataUtil extends BuildUtil{

    public static Map convertData(TableColsInfo tableColsInfo){

        String camelTableName = convert2Camel(tableColsInfo.getTableName());
        String upperCamelTableName = convert2UpperCamel(camelTableName);
        String moduleName = tableColsInfo.getTableName().split("_")[0];
        List<ColumnEntity> columnList = tableColsInfo.getColList().stream().map(
                                columnEntity -> columnEntity2Java(columnEntity)).collect(Collectors.toList());

        Map databaseMap = BuildUtil.newAndPuts(HashMap::new, HashMap::put,

//                Consts.UPPER_CAMEL_TABLE_NAME, upperCamelTableName, // 用来做最终输出文件的名字的 这个含有module的名字 需要去掉
                Consts.UPPER_CAMEL_TABLE_NAME, upperCamelTableName.replace(convert2UpperCamel(moduleName), ""),
                Consts.COLUMNLIST, columnList, Consts.MODULENAME, moduleName,
                Consts.TABLENAME, tableColsInfo.getTableName(), Consts.CAMEL_TABLE_NAME, convert2LowerCamel(camelTableName.replace(moduleName, ""))

        );

        return databaseMap;
    }

    private static ColumnEntity columnEntity2Java(ColumnEntity columnEntity){
        String columnName = columnEntity.getColumnName();

        String camelColName = convert2Camel(columnName);
        String upperCamelColName = convert2UpperCamel(camelColName);

        BuildUtil.setVals(columnEntity, getVAndF(camelColName, ColumnEntity::setCamelColName)
                                , getVAndF(upperCamelColName, ColumnEntity::setUpperCamelColName)
                                , getVAndF(getColumnJavaType(columnEntity), ColumnEntity::setJavaFiledType));

        return columnEntity;
    }

    private static String getColumnJavaType(ColumnEntity columnEntity){
        String javaType = "String";
        switch (columnEntity.getDataType()){
//            case "char":
//                columnEntity.setJavaType("String");
//                break;
//            case "varchar":
//                columnEntity.setJavaType("String");
//                break;
//            case "text":
//                columnEntity.setJavaType("String");
//                break;
            case "integer":
                javaType = "long";
                break;
            case "samllint":
                javaType = "int";
                break;
            case "int":
                javaType = "int";
                break;
            case "decimal":
                javaType = "double";
                break;
            case "date":
                javaType = "Date";
                break;
            case "datetime":
                javaType = "Date";
                break;
            default:
                break;
        }
        return javaType;
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

    public static String convert2LowerCamel(String camelName){
        char[] charArr = camelName.toCharArray();
        charArr[0] += 32;
        return  String.valueOf(charArr);
    }
}
