package com.templateTools.entity.model;

import java.util.List;

public class DatabaseModel {

    private String tableName;

    private List<String> columnNameList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnNameList() {
        return columnNameList;
    }

    public void setColumnNameList(List<String> columnNameList) {
        this.columnNameList = columnNameList;
    }
}
