package com.liaoyb.po;

import java.util.Date;




//������Ϣ
public class ShareInfo {
	
	private String shareId;
	private String userId;
	//��������
	private Date shareDate;
	//�����ļ�������
	private String shareUrl;
	
	private ShareType shareType;
	//������ļ�
	private MyFile myFile;
	
	//
//	�����ƾ֤,˽�ܷ������ѷ���,���͸�����
	
	//ƾ֤,����Ϊ�գ�����ƾ֤
	private String evidence;

	public ShareInfo(String shareUrl) {
		super();
		this.shareUrl = shareUrl;
	}

	public ShareInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShareInfo(String shareUrl, String evidence) {
		super();
		this.shareUrl = shareUrl;
		this.evidence = evidence;
	}
	
	
	
}
