package com.acme.repository_transaction_framework.servicetran.proxy;

import com.acme.repository_transaction_framework.annotation.Transaction;
import com.acme.repository_transaction_framework.repository.jdbcbase.ConnectionManager;
import com.acme.repository_transaction_framework.repository.typeobject.ParamType;
import com.acme.repository_transaction_framework.servicetran.service.StudentService;
import com.acme.repository_transaction_framework.servicetran.service.BaseService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class ServiceProxy<T> implements InvocationHandler {

    private BaseService target;
    private static Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

    public ServiceProxy(Class<T> clazz){
        try {
            this.target = (BaseService)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return ServiceMethodHandler(method,args);

    }

    private Object ServiceMethodHandler(Method method, Object[] args) {
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        Object result = null;
        if (declaredAnnotations[0] instanceof Transaction){
            Transaction transaction = (Transaction)declaredAnnotations[0];
            if(transaction.type().equals("requireNew")){
                ConnectionManager.benigTransction("requireNew");
            }else if(!ConnectionManager.isHaveTransaction()){
                ConnectionManager.benigTransction("");
            }
        }
        try {
            result = method.invoke(target,args);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollback();
           throw new RuntimeException("Transaction rollback");
        }
        ConnectionManager.endTransction();

        return result;




//        JDBCUtils.execute(sql);

    }

    private ParamType[] getParamTypesByArgs(Object[] args) {
        ParamType[] paramTypes = new ParamType[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = new ParamType(args[i]);
        }
        return paramTypes;

    }

    public T newInstance(){
        Type[] genericInterfaces = target.getClass().getGenericInterfaces();


        try {
            return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),new Class[]{StudentService.class},this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
