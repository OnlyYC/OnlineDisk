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
		SimpleEmail email=new SimpleEmail();//���������ͨ���ʼ���ʹ�������Ϳ�����
		email.setHostName("smtp.163.com");//ָ��Ҫʹ�õ��ʼ�������
		email.setAuthentication("18098678953", "5260liao");//ʹ��163���ʼ����������ṩ��163��ע����û���������  
		email.setCharset("utf-8");
		email.setFrom("18098678953@163.com");
		email.addTo("876714394@qq.com");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate=format.format(new Date());
		String ip=IpUtil.getServerIP();
		System.out.println(strDate);
		email.setSubject("����");
		email.setMsg(ip+"\n"+strDate+"\n"+message);
		email.send();
		System.out.println("���ͳɹ�");
		}catch(EmailException e){
			e.printStackTrace();
		}
	}
	
	
	
}
