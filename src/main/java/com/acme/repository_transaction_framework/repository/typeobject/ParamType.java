package com.acme.repository_transaction_framework.repository.typeobject;

public class ParamType {

    private Object value;

    private Class baseType;

    public ParamType(Object value){
        this.value = value;
        this.baseType = value.getClass();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getBaseType() {
        return baseType;
    }

    public void setBaseType(Class baseType) {
        this.baseType = baseType;
    }
}
