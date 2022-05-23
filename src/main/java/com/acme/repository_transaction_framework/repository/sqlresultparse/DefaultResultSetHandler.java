package com.acme.repository_transaction_framework.repository.sqlresultparse;

import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;
import com.acme.repository_transaction_framework.repository.typeobject.TableInfo;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {
    private static ResultSetHandler handler = new DefaultResultSetHandler();

    private DefaultResultSetHandler(){

    }

    public static ResultSetHandler getInstance(){
        return handler;
    }

    @Override
    public List<JSONObject> handlerResult(ResultSet resultSet, SqlInfo sqlInfo) {

        Class<? extends Class> aClass = Integer.class.getClass();
        TableInfo tableInfo = sqlInfo.getTableInfo();
        Class poClass = tableInfo.getPoClass();
        Field[] declaredFields = poClass.getDeclaredFields();
        List<JSONObject> resultList = new ArrayList<>();
        while(true){
            try {
                if (!resultSet.next()) break;
                JSONObject jsonObject = new JSONObject();
                for(Field field : declaredFields){
                    String fieldName = field.getName();
                    if(field.getType()==Integer.class || field.getType()==int.class){
                        int intValue = resultSet.getInt(fieldName);
                        jsonObject.put(fieldName,intValue);
                    }else{
                        String stringValue = resultSet.getString(fieldName);
                        jsonObject.put(fieldName,stringValue);
                    }
                }
                resultList.add(jsonObject);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }


        return resultList;
    }
}
