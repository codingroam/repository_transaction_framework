package com.acme.repository_transaction_framework.repository.typeobject;

public class TableInfo {

    private String name;
    private String key;

    private Class poClass;

    public TableInfo(String name, String key, Class poClass) {
        this.name = name;
        this.key = key;
        this.poClass = poClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class getPoClass() {
        return poClass;
    }

    public void setPoClass(Class poClass) {
        this.poClass = poClass;
    }
}
