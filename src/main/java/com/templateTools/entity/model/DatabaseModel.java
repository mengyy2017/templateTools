package com.templateTools.entity.model;

import com.templateTools.entity.ColumnEntity;

import java.util.List;

public class DatabaseModel {

    private String tableName;

    private List<ColumnEntity> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnEntity> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnEntity> columnList) {
        this.columnList = columnList;
    }
}
