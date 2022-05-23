package com.acme.repository_transaction_framework.repository.sqlresultparse;

import com.acme.repository_transaction_framework.repository.typeobject.EnumSqlType;
import com.acme.repository_transaction_framework.repository.typeobject.ParamType;
import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;
import com.acme.repository_transaction_framework.annotation.Select;
import com.acme.repository_transaction_framework.annotation.Table;
import com.acme.repository_transaction_framework.annotation.Update;
import com.acme.repository_transaction_framework.repository.dao.BaseDao;
import com.acme.repository_transaction_framework.repository.typeobject.TableInfo;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Parse {

    public static SqlInfo parseAnnotationGetSqlInfo(Annotation[] declaredAnnotations, Object[] args, Class target) {
        TableInfo tableInfo = getTableInfoByDaoClass(target);
        for (int i = 0; i < declaredAnnotations.length; i++) {
            if (declaredAnnotations[i] instanceof Select) {
                Select select = (Select) declaredAnnotations[i];
                String value = select.value();
                String sql = value.replace("@table",tableInfo.getName());
                if(sql.contains("@primarykey")){
                    sql = sql.replace("@primarykey",tableInfo.getKey());
                }
                return new SqlInfo(sql, EnumSqlType.SELECT, args == null ? null : getParamTypesByArgs(args), tableInfo);

            }else if(declaredAnnotations[i] instanceof Update){

                Update select = (Update) declaredAnnotations[i];
                String value = select.value();
                String sql = value.replace("@table",tableInfo.getName());

                if(sql.contains("@po=#po")){
                   String setPartSql = getSetPartSql(tableInfo,args);
                   sql = sql.replace("@po=#po",setPartSql);
                }

                if(sql.contains("@primarykey")){
                    sql = sql.replace("@primarykey",tableInfo.getKey());
                    ParamType[] paramTypes = {new ParamType(args[1])};
                    SqlInfo sqlInfo = new SqlInfo(sql,EnumSqlType.UPDATE,paramTypes,tableInfo);
                    return sqlInfo;
                }

            }
        }
        return null;
    }

    private static String getSetPartSql(TableInfo tableInfo,Object[] args) {
        Class poClass = tableInfo.getPoClass();
        Field[] declaredFields = poClass.getDeclaredFields();
        Object[] param = {};
        String partSql = "";
        for(Field field : declaredFields){
            String fieldName = field.getName();
            String name = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            try {
                Method method = poClass.getMethod("get" + name);
                Object result = method.invoke(args[0], param);
                if(result != null){
                    if(result instanceof Integer){
                        partSql = partSql + fieldName + "=" +result + ",";
                    }else{
                        partSql = partSql + fieldName + "='" +result + "'" + ",";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return partSql.substring(0,partSql.length()-1);
    }

    private static TableInfo getTableInfoByDaoClass(Class target) {
        Type[] genericInterfaces = target.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
            if (parameterizedType.getRawType() == BaseDao.class) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Class<?> poClass = (Class)actualTypeArguments[0];

                Table table = poClass.getDeclaredAnnotation(Table.class);
                return new TableInfo(table.name(),table.key(),poClass);

            }
        }
        throw new RuntimeException("");

    }

    private static ParamType[] getParamTypesByArgs(Object[] args) {
        ParamType[] paramTypes = new ParamType[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = new ParamType(args[i]);
        }
        return paramTypes;

    }








    }

