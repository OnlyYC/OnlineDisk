package com.liaoyb.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.liaoyb.util.ConnectionPool;

public class TestConnPool {

	public static void main(String[] args) {
		ConnectionPool connPool=new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/liaoyb", "liao", "5260liao");
		 try {
	            connPool.createPool();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        try {
	            Connection conn = connPool.getConnection();
	        } catch (SQLException ex1) {
	            ex1.printStackTrace();
	        }

	}

}
