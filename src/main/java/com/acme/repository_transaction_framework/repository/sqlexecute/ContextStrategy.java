package com.acme.repository_transaction_framework.repository.sqlexecute;

import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;

public class ContextStrategy {
    public static SqlExecuteStrategy getSqlExecuteStrategy(SqlInfo sqlInfo){
        switch (sqlInfo.getSqlType()){
            case SELECT:
                return new SelectSqlExecuteStrategy(sqlInfo);
            case UPDATE:
                return new UpdateSqlExecuteStrategy(sqlInfo);
        }
        return null;
    }
}
