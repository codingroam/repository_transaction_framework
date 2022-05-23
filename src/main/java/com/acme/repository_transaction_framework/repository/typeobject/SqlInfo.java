package com.acme.repository_transaction_framework.repository.typeobject;

public class SqlInfo {

    private String sql;
    private EnumSqlType sqlType;
    private ParamType[] paramTypes;
    private TableInfo tableInfo;

    public SqlInfo(String sql, EnumSqlType sqlType) {
        this.sql = sql;
        this.sqlType = sqlType;
    }



    public SqlInfo(String sql, EnumSqlType sqlType, ParamType[] paramTypes, TableInfo tableInfo) {
        this.sql = sql;
        this.sqlType = sqlType;
        this.paramTypes = paramTypes;
        this.tableInfo = tableInfo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public EnumSqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(EnumSqlType sqlType) {
        this.sqlType = sqlType;
    }

    public ParamType[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(ParamType[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}
