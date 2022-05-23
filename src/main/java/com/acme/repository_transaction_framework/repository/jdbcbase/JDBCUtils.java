package com.acme.repository_transaction_framework.repository.jdbcbase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;


public class JDBCUtils {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    /**
     * 文件的读取，只需要读取一次即可拿到这些值。使用静态代码块
     */
    static{
        //读取资源文件，获取值。

        try {
            //1. 创建Properties集合类。
            Properties pro = new Properties();

            //获取src路径下的文件的方式--->ClassLoader 类加载器
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res  = classLoader.getResource("jdbc.properties");
            String path = res.getPath();
            System.out.println(path);///D:/IdeaProjects/itcast/out/production/day04_jdbc/jdbc.properties
            //2. 加载文件
            // pro.load(new FileReader("D:\\IdeaProjects\\itcast\\day04_jdbc\\src\\jdbc.properties"));
            pro.load(new FileReader(path));


            //3. 获取数据，赋值
            url = pro.getProperty("url");
            user = pro.getProperty("username");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            //4. 注册驱动
            //Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean execute(String sql,Object...param) {
        boolean result = true;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            //开启事务
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<param.length;i++) {
                preparedStatement.setObject(i,param[i]);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            //提交事务
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 日志记录事务回滚失败
                    result = false;
                    return result;
                }
            }
            result = false;
        }finally {
            close(preparedStatement,connection);
        }
        return result;
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 功能描述:
     * 〈释放资源〉
     *
     * @params : [stmt, conn]
     * @return : void
     * @author : cwl
     * @date : 2019/12/4 13:52
     */
    public static void close(PreparedStatement preparedStatement, Connection connection){
        if( preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if( connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute(String sql) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);

            //2.定义sql
            //2.1 张三 - 500
            //String sql1 = "update account set balance = balance - ? where id = ?";
           // String sql1 = "update test set age=age-10 where id=1";
            //2.2 李四 + 500
            //String sql2 = "update test set age=age+10 where id=2";;
            //3.获取执行sql对象
            pstmt1 = conn.prepareStatement(sql);
           // pstmt2 = conn.prepareStatement(sql2);
            //4. 设置参数
//            pstmt1.setDouble(1,500);
//            pstmt1.setInt(2,1);
//
//            pstmt2.setDouble(1,500);
//            pstmt2.setInt(2,2);
            //5.执行sql
            ResultSet resultSet = pstmt1.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
            }

            // 手动制造异常
            //int i = 3/0;

          //  pstmt2.executeUpdate();
            //提交事务
            conn.commit();
        } catch (Exception e) {
            //事务回滚
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JDBCUtils.close(pstmt1,conn);
            JDBCUtils.close(pstmt2,null);
        }


    }



    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if( rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if( stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if( conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
