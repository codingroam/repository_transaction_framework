package com.acme.repository_transaction_framework.repository.factory;

import com.acme.repository_transaction_framework.repository.dao.BaseDao;
import com.acme.repository_transaction_framework.repository.proxy.DaoProxy;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory{

    private static Map<Class , BaseDao> daoMap = new HashMap<>();
    private static  DaoFactory daoFactory = new DaoFactory();
    private DaoFactory(){
    }
    public  static DaoFactory getInstnce(){
        return daoFactory;
    }


    public <T extends BaseDao> T getDao(Class<T> clazz){
        BaseDao baseDaoCache = daoMap.get(clazz);
        if(baseDaoCache != null){
            return (T)baseDaoCache;
        }

        T baseDao = null;
        synchronized (DaoFactory.class){
            BaseDao baseDaoCacheCheck = daoMap.get(clazz);
            if(baseDaoCacheCheck != null){
                return (T)baseDaoCacheCheck;
            }

            baseDao = new DaoProxy<T>(clazz).newInstance();
            daoMap.put(clazz,baseDao);


        }

        return baseDao;


    }





}
