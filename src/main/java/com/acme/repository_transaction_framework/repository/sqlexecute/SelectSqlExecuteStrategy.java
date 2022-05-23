package com.acme.repository_transaction_framework.repository.sqlexecute;

import com.acme.repository_transaction_framework.repository.sqlresultparse.DefaultResultSetHandler;
import com.acme.repository_transaction_framework.repository.sqlresultparse.ResultSetHandler;
import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;

import java.sql.ResultSet;

public class SelectSqlExecuteStrategy<List> extends AbstractSqlExecuteStrategy {

    private ResultSetHandler resultSetHandler;
    public SelectSqlExecuteStrategy(SqlInfo sqlInfo) {
        super(sqlInfo);
        this.resultSetHandler = DefaultResultSetHandler.getInstance();
    }

    @Override
    public Object executeSql() {
        try {
            ResultSet resultSet = this.pstat.executeQuery();

            return this.resultSetHandler.handlerResult(resultSet,sqlInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
