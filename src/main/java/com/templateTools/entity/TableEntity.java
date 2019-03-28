package com.templateTools.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tables")
public class TableEntity {

    @Id
    @Column(name = "table_name")
    private String id;

    private String tableName;

    @Transient
    private String camelTableName;

    @Transient
    private String upperCamelTableName;

    private String tableComment;

    private String tableSchema;

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

    public String getCamelTableName() {
        return camelTableName;
    }

    public void setCamelTableName(String camelTableName) {
        this.camelTableName = camelTableName;
    }

    public String getUpperCamelTableName() {
        return upperCamelTableName;
    }

    public void setUpperCamelTableName(String upperCamelTableName) {
        this.upperCamelTableName = upperCamelTableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
