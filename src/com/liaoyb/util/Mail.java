package com.liaoyb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class Mail {
	public static void sendMail(String message)  {
		try{
		SimpleEmail email=new SimpleEmail();//如果发送普通的邮件，使用这个类就可以了
		email.setHostName("smtp.163.com");//指定要使用的邮件服务器
		email.setAuthentication("18098678953", "5260liao");//使用163的邮件服务器需提供在163已注册的用户名、密码  
		email.setCharset("utf-8");
		email.setFrom("18098678953@163.com");
		email.addTo("876714394@qq.com");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate=format.format(new Date());
		String ip=IpUtil.getServerIP();
		System.out.println(strDate);
		email.setSubject("错误");
		email.setMsg(ip+"\n"+strDate+"\n"+message);
		email.send();
		System.out.println("发送成功");
		}catch(EmailException e){
			e.printStackTrace();
		}
	}
	
	
	
}
