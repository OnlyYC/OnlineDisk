package com.liaoyb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5 {
	public static String EncoderByMd5(String str){
		//ȷ�����㷽��
		MessageDigest md5;
		String newStr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		
		BASE64Encoder base64en=new BASE64Encoder();
		//���ܺ���ַ���
		 newStr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newStr;
	}
	//oldpassword���������д�ŵ�����
	public static  boolean checkpassword(String newpassword,String oldpassword){
		if(EncoderByMd5(newpassword).equals(oldpassword))
			return true;
		
		return false;
		
	}
	
	
}
