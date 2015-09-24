package com.liaoyb.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.mail.EmailException;

import com.sun.rowset.CachedRowSetImpl;

public class JdbcUtil {
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static ConnectionPool connPool;
	static {
		Properties prop = new Properties();

		  try {

			prop.load(JdbcUtil.class.getClassLoader().getResourceAsStream(
					"mysql.properties"));
			// 加载驱动
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		    connPool=new ConnectionPool(driver, url, username, password);
		 
				connPool.createPool();
				
				
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("数据库连接失败");
					Mail.sendMail("数据库连接失败");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(url + username + password);

	

	}

	public Connection getConnection() {
		Connection conn=null;
		try {
			conn=connPool.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */

	public CachedRowSetImpl executeQuery(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		CachedRowSetImpl rowset = null;

		try {
			conn = getConnection();
			if(conn==null){
				Mail.sendMail("连接为空");
			}
			pStatement = conn.prepareStatement(sql);
			// 设置参数
			setParameters(pStatement, parameters);
			result = pStatement.executeQuery();
			rowset = new CachedRowSetImpl();
			rowset.populate(result);
			release(conn, pStatement, result);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rowset;

	}

	/**
	 * insert 、update、delete
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public int executeUpdate(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement pStatement = null;
		int affectConut = 0;
		try {
			conn = getConnection();
			pStatement = conn.prepareStatement(sql);
			setParameters(pStatement, parameters);
			affectConut = pStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			release(conn, pStatement, null);
		}

		return affectConut;

	}

	public void setParameters(PreparedStatement statement, Object... parameters) {
		if (parameters == null)
			return;
		for (int index = 1; index <= parameters.length; index++) {
			try {
				statement.setObject(index, parameters[index - 1]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void release(Connection conn, Statement statement, ResultSet result) {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 归还conn
		connPool.returnConnection(conn);

	}

}
