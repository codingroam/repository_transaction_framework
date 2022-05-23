package com.acme.repository_transaction_framework.repository.sqlresultparse;

import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler {

    List<JSONObject> handlerResult(ResultSet resultSet, SqlInfo sqlInfo);
}
