package com.acme.repository_transaction_framework.repository.sqlexecute;

import com.acme.repository_transaction_framework.repository.sqlexecute.AbstractSqlExecuteStrategy;
import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;

public  class UpdateSqlExecuteStrategy extends AbstractSqlExecuteStrategy {
    
        public UpdateSqlExecuteStrategy(SqlInfo sqlInfo) {
            super(sqlInfo);
        }
    
        @Override
        public Object executeSql() {
            try {
                int i = this.pstat.executeUpdate();
                return i;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }