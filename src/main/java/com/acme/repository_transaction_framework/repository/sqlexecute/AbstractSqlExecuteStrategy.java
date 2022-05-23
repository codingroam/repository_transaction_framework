package com.acme.repository_transaction_framework.repository.sqlexecute;




import com.acme.repository_transaction_framework.repository.jdbcbase.ConnectionManager;
import com.acme.repository_transaction_framework.repository.typeobject.ParamType;
import com.acme.repository_transaction_framework.repository.typeobject.SqlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractSqlExecuteStrategy implements SqlExecuteStrategy {

    protected Connection connection;
    protected PreparedStatement pstat;
    protected SqlInfo sqlInfo;
    private static Logger logger = LoggerFactory.getLogger(AbstractSqlExecuteStrategy.class);


    public AbstractSqlExecuteStrategy(SqlInfo sqlInfo){

        this.connection = ConnectionManager.getConnection("");
        this.sqlInfo = sqlInfo;
        try {
            this.pstat = this.connection.prepareStatement(this.sqlInfo.getSql());
            logger.info("SQL: "+this.sqlInfo.getSql());
            this.pstatSetParams();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void pstatSetParams() throws SQLException {
        ParamType[] paramTypes = this.sqlInfo.getParamTypes();

        if(paramTypes != null){
            for (int i = 0; i < paramTypes.length; i++) {
                if(paramTypes[i].getValue() instanceof Integer){
                    this.pstat.setInt(i+1,Integer.valueOf(paramTypes[i].getValue().toString()));
                }else if(paramTypes[i].getValue() instanceof String){
                    this.pstat.setString(i+1,paramTypes[i].getValue().toString());
                }
            }

        }

    }



}
