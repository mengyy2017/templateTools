package com.templateTools.entity;

import java.util.List;

public class CreateInfo {

    private String databaseAdress;

    private String databaseType;

    private String databasePort;

    private String databaseSchema;

    private String databaseUsername;

    private String databasePassword;

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

    public String getDatabaseAdress() {
        return databaseAdress;
    }

    public void setDatabaseAdress(String databaseAdress) {
        this.databaseAdress = databaseAdress;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
}
