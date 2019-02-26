package com.templateTools.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "columns")
public class ColumnEntity {

    @Id
    @Column(name = "column_name")
    private String id;

    private String tableName;

    private String columnName;

    @Transient
    private String camelColName;

    @Transient
    private String upperCamelColName;

    private String dataType;

    @Transient
    private String javaType;

    private String columnComment;

    private String tableSchema;

    private String columnKey;

    private String characterMaximumLength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCamelColName() {
        return camelColName;
    }

    public void setCamelColName(String camelColName) {
        this.camelColName = camelColName;
    }

    public String getUpperCamelColName() {
        return upperCamelColName;
    }

    public void setUpperCamelColName(String upperCamelColName) {
        this.upperCamelColName = upperCamelColName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(String characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }
}
