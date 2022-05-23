package com.acme.repository_transaction_framework.repository.proxy;

import com.acme.repository_transaction_framework.repository.typeobject.EnumSqlType;
import com.acme.repository_transaction_framework.repository.po.BasePO;
import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;
import com.acme.repository_transaction_framework.repository.sqlexecute.ContextStrategy;
import com.acme.repository_transaction_framework.repository.sqlresultparse.Parse;
import com.acme.repository_transaction_framework.repository.sqlexecute.SqlExecuteStrategy;
import com.alibaba.fastjson.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class DaoProxy<T> implements InvocationHandler {

    private Class target;

    public DaoProxy(Class target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return DaoMethodHandler(method,args);


    }

    private Object DaoMethodHandler(Method method, Object[] args) {
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        SqlInfo sqlInfo = Parse.parseAnnotationGetSqlInfo(declaredAnnotations,args,this.target);
        SqlExecuteStrategy sqlExecuteStrategy = ContextStrategy.getSqlExecuteStrategy(sqlInfo);
        Object result = sqlExecuteStrategy.executeSql();
        if(sqlInfo.getSqlType() == EnumSqlType.SELECT){
            Class<?> returnType = method.getReturnType();
            List<JSONObject> resultObjectList = (List<JSONObject>) result;
            List<BasePO> basePOList = new ArrayList<>();
            for(JSONObject jsonObject : resultObjectList){
                BasePO o = (BasePO)JSONObject.toJavaObject(jsonObject, sqlInfo.getTableInfo().getPoClass());
                basePOList.add(o);
            }
            if(returnType == List.class){
                return basePOList;
            }
            return basePOList.size() == 0 ? null : basePOList.get(0);
        }
        return result;

    }


    public T newInstance(){
        return (T)Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
