package com.liaoyb.service;

import com.liaoyb.po.User;

public interface IUserService {
	//��½
	public User login(String strname,String password);
	public User register(String username,String password);
	//��������
	public boolean updateInfo(User user);
	//ͨ���û��������û�
	public User gainUserByUserName(String username);
	
	//�����û����Ƿ����
	public boolean userExitByName(String username);
}
