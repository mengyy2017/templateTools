package com.templateTools.entity.model;

import com.templateTools.entity.ColumnEntity;

import java.util.List;

public class TableColsInfo {

    private String tableName;

    private List<ColumnEntity> colList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnEntity> getColList() {
        return colList;
    }

    public void setColList(List<ColumnEntity> colList) {
        this.colList = colList;
    }
}
