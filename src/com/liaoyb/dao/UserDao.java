package com.liaoyb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.User;
import com.liaoyb.util.JdbcUtil;
import com.liaoyb.util.MD5;

public class UserDao {
	private JdbcUtil util = new JdbcUtil();

	// ע��
	public boolean addUser(User user) {
		util.executeUpdate("insert into users(userid,username,password) values (?,?,?)", user.getUserId(),user.getUsername(),user.getPassword());
		//�½�һ��diskinfo��,1��G
		OnlineDisk disk=new OnlineDisk(1024*1024*1024, 0, user.getUserId());
		
		util.executeUpdate("insert into diskinfo (userid,capacity,usespace) values(?,?,?)", disk.getUserId(),disk.getCapacity(),disk.getUserspace());
		
		
		
		return true;

	}

	// �����û�����
	public void updateUserInfo(User user) {

	}

	public User loginByUsername(String username, String password) {
		// ͨ���û����ҵ���Ӧ������

		ResultSet result = util.executeQuery(
				"select userid,username,password from users where username=?",
				username);
		User user = gainUser(result);
		if(user==null)
			return null;
		// ��֤����
		boolean state = MD5.checkpassword(password, user.getPassword());
		if (state)
			return user;
		return null;

	}

	// �õ�user

	public User gainUser(ResultSet result) {
		User user = null;
		try {
			if (result.next()) {
				user = new User();
				user.setUserId(result.getString("userid"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

	}
	
	public boolean userExitByUsername(String username){
		
		ResultSet result=util.executeQuery("select userid from users where username=?", username);
		try {
			if(result.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	
	public User gainUserByUserName(String username){
		ResultSet result=util.executeQuery("select * from users where username=?", username);
		return gainUser(result);
	}
	

	public User loginByEmail(String email, String password) {
		// ͨ�������ҵ���Ӧ������

		ResultSet result = util.executeQuery(
				"select userid,username,password from users where email=?",
				email);
		User user = gainUser(result);

		// ��֤����
		boolean state = MD5.checkpassword(password, user.getPassword());
		if (state)
			return user;
		return null;

	}

	// ���ѵ�

}
