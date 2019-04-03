package com.template.bussiness.entity.model;

import com.template.utils.ThreadLocalUtil;
import com.common.util.BuildUtil;
import java.util.List;

public class CreateInfo extends BuildUtil {

    private String databaseAdress;

    private String databaseType;

    private String databasePort;

    private String databaseSchema;

    private String tableSchema;

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

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
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

    @Override
    public String toString() {
        return databaseAdress + "~" + databaseType + "~" + databasePort + "~" + databaseSchema + "~"
                + tableSchema + "~" + databaseUsername + "~" + databasePassword + "~" + codePackage;
    }

    public static CreateInfo toCreateInfo(String authToken) {
        String[] authTokenArr = authToken.split("~");
        return newAndSet0(CreateInfo::new, new String[]{authTokenArr[0], authTokenArr[1], authTokenArr[2]
                ,authTokenArr[3], authTokenArr[4], authTokenArr[5], authTokenArr[6], authTokenArr[7]}, CreateInfo::setDatabaseAdress
                , CreateInfo::setDatabaseType, CreateInfo::setDatabasePort, CreateInfo::setDatabaseSchema
                , CreateInfo::setTableSchema, CreateInfo::setDatabaseUsername, CreateInfo::setDatabasePassword
                , CreateInfo::setCodePackage);
    }

    public static CreateInfo creInfoFromToken() {
        return CreateInfo.toCreateInfo(ThreadLocalUtil.getAuthToken());
    }

}
