package com.acme.repository_transaction_framework.repository.jdbcbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionManager {
	private ConnectionManager() {
	}

	private static ThreadLocal<Stack<Connection>> threadConn = new ThreadLocal<Stack<Connection>>();

	public static boolean isHaveTransaction(){
		Stack<Connection> connectionStack = threadConn.get();
		if(connectionStack == null){
			return false;
		}
		return true;
	}

	// 获取数据库连接,将同一线程开启多个事务时将事务对应的连接放入threadlocal
	public static Connection getConnection(String type) {
		Stack<Connection> connectionStack = threadConn.get();
		if(connectionStack == null){
			connectionStack =  new Stack<>();
		}
		Connection conn = null;

		//如果线程栈中的连接数为空或者需要开启新事务则新建数据库连接放入栈中
		if(connectionStack.empty() || type.equals("requireNew")){
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/test", "root", "root");
				connectionStack.push(conn);
				threadConn.set(connectionStack);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else {
			conn = connectionStack.peek();

		}
		return conn;
	}

	// 获取数据库连接
	public static Connection disConnection() {

		Stack<Connection> connectionStack = threadConn.get();
		Connection conn = connectionStack.pop();
		return conn;
	}

	// 设置事务手动提交
	public static void benigTransction(Connection conn) {
		try {
			if (conn != null) {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 设置事务手动提交
	public static void benigTransction(String type) {
		Connection conn = getConnection(type);
		try {
			if (conn != null) {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	// 提交事务
	public static void endTransction() {
		Connection conn = disConnection();
		try {
			if (conn != null) {
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(conn);
	}

	// 设置Connection的原始状态
	public static void recoverTransction(Connection conn) {
		try {
			if (conn != null) {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				} else {
					conn.setAutoCommit(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 发生异常回滚事务
	public static void rollback() {
		Connection conn = disConnection();
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 关闭连接,当栈中无连接时从threadlocal移除
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				Stack<Connection> connections = threadConn.get();
				if(connections.empty()){
					threadConn.remove();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}