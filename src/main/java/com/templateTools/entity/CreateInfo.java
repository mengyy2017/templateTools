package com.templateTools.entity;

import java.util.List;

public class CreateInfo {

    private String databaseType;

    private String codePackage;

    private String author;

    private List<TableColsInfo> tableColsInfoList;

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getCodePackage() {
        return codePackage;
    }

    public void setCodePackage(String codePackage) {
        this.codePackage = codePackage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<TableColsInfo> getTableColsInfoList() {
        return tableColsInfoList;
    }

    public void setTableColsInfoList(List<TableColsInfo> tableColsInfoList) {
        this.tableColsInfoList = tableColsInfoList;
    }
}
