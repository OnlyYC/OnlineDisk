package com.liaoyb.service.impl;

import java.util.UUID;

import com.liaoyb.dao.DiskDao;
import com.liaoyb.dao.UserDao;
import com.liaoyb.po.User;
import com.liaoyb.service.IUserService;
import com.liaoyb.util.MD5;

public class UserService implements IUserService {
	private UserDao userDao=new UserDao();
	@Override
	public User login(String strname, String password) {
		//�ж���������û�������email
		User user=null;
		if(strname.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
			//�����ʽ
			user=userDao.loginByEmail(strname, password);
			
		}else{
			user=userDao.loginByUsername(strname, password);
		}
		
		
		return user;
	}

	@Override
	public User register(String username, String password) {
		
		
		//���ж��û����Ƿ����
		if(userDao.userExitByUsername(username)){
			//�û�������
			return null;
		}
		
		//�����Ƿ���Ϲ淶
		
		
		//����һ��User
		User user=new User();
		//UUID����userid
		user.setUserId(UUID.randomUUID().toString());
		user.setUsername(username);
		//password����
		user.setPassword(MD5.EncoderByMd5(password));
		userDao.addUser(user);
		return user;
	}

	@Override
	public boolean updateInfo(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User gainUserByUserName(String username) {


		
		
		
		return null;
	}

	@Override
	public boolean userExitByName(String username) {
		// TODO Auto-generated method stub
		return userDao.userExitByUsername(username);

	}
	
	
	

}
