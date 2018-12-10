package com.templateTools.utils;

import com.templateTools.entity.ColumnEntity;
import com.templateTools.entity.model.DatabaseModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandelDataUtil {

    public static Map convert2Camel(DatabaseModel databaseModel){
        Map databaseMap = new HashMap();

        String tableName = databaseModel.getTableName()
                .replaceAll("(.)(.*)", "$1".toUpperCase() + "$2")
                .replaceAll("_(.)", "$1".toUpperCase());
        databaseMap.put("tableName", tableName);

        List<ColumnEntity> columnList = databaseModel.getColumnList().stream().map(column -> {
            // column.replaceAll("_(.)", "$1".toUpperCase());
            return  column;
        }).collect(Collectors.toList());
        databaseMap.put("columnList", columnList);

        return databaseMap;
    }
}
