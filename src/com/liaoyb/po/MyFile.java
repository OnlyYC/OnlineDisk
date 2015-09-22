package com.liaoyb.po;

import java.io.Serializable;
import java.util.Date;

public class MyFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6495831090639080647L;
	//�������е��ļ�id
	private String fileIdInDisk;
	private String userId;
	private String fileName;//�ļ���
	//�������е�path,
	private String path;
	private int type;//0Ϊ�ļ��У�1Ϊ�ļ�
	
	private Date createTime;
	private int isdelete;//�Ƿ�ɾ�������ڻ���վ����
	
	
	
	
	


	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	//Դ�ļ�(����Ϊ�գ���ΪĿ¼ʱ)
	private SourceFile sourceFile;
	
	//
	
	

	public String getFileIdInDisk() {
		return fileIdInDisk;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFileIdInDisk(String fileIdInDisk) {
		this.fileIdInDisk = fileIdInDisk;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SourceFile getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(SourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}
	public MyFile(String fileIdInDisk, String userId, String fileName,
			String path, int type) {
		super();
		this.fileIdInDisk = fileIdInDisk;
		this.userId = userId;
		this.fileName = fileName;
		this.path = path;
		this.type = type;
	}
	public MyFile(String fileIdInDisk, String userId, String fileName,
			String path, int type, SourceFile sourceFile) {
		super();
		this.fileIdInDisk = fileIdInDisk;
		this.userId = userId;
		this.fileName = fileName;
		this.path = path;
		this.type = type;
		this.sourceFile = sourceFile;
	}

	public MyFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
