package com.acme.repository_transaction_framework.repository.dao;

import com.acme.repository_transaction_framework.annotation.Select;
import com.acme.repository_transaction_framework.annotation.Update;

import java.util.List;

public interface BaseDao<T> {

    @Select("select * from @table")
    List<T> findAll();

    @Select("select * from @table where @primarykey=?")
    T findByPrimaryKey(Object value);

    @Update("update @table set @po=#po where @primarykey=?")
    int updateByPrimaryKey(T value,Object key);

    @Update("update @table set @po=#po where @condition=#condition")
    int updateByCondition(T value);



}
