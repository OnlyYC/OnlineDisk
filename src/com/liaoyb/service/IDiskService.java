package com.liaoyb.service;

import java.util.List;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.ShareType;
import com.liaoyb.po.User;

public interface IDiskService {

	
	
	public MyFile gainMyFileByName(String fileName);
	public MyFile gainMyFileByDiskPath(String diskPath);
	public ShareInfo gainShafoByshareUrl(String shareInfo);
	
	//�½��ļ���
	public void newDir(MyFile dirFile);
	//�õ����е��ļ�
	public List<MyFile>gainUserFiles(String userid);
	
	//�����ļ������ط���·�����ļ��б�
	//path   root/..
	public List<MyFile>FilesUnderPath(List<MyFile> files,String path);
	
	
	
	//�ϴ�ǰ��֤,��С�Ƿ��ˣ��ҵ����������Ƿ�
	public boolean validate(User user,float FileSize);
	
	//������Ϣ
	public OnlineDisk myDisk();
	
	
	//�ϴ�,д�����ݿ��ϴ�����Ϣ���ϴ�ʱ�䣬�ϴ��ļ���С�������λ��()
	//�������б����λ��,��MyFile��path��
	public void upload(MyFile file);
	
	
	//����,��¼����,�����ļ���url
	//�����Լ����ļ�
	MyFile downloadMySelf(User currentUser, String fileiddisk);
	
	public MyFile downloadShare(String shareid);
	
	//��֤�Ƿ���Ի�÷����ļ�
	public boolean valiGetShareFile(ShareInfo shareInfo,String evidence);
	
	//���ط����ļ�(��ʱ�����ط����ļ���һ����)  sharePath���ڷ����ļ��е�path
	public void downShareFile(ShareInfo shareInfo,String sharePath);
	
	//����,�������ļ���Ҳ�������ļ���,����һ��ShareInfo,��д�����ݿ�
	public ShareInfo share(MyFile myFile,ShareType sharetype);
	
	
	
	//ת��,����ת��һ����
	public boolean dumped(ShareInfo shareInfo,String sharePath);
	
	//ɾ��,��ɾ���ı�־��Ϊ1
	public void delete(String fileiddisk);
	
	//���ɾ��
	public void deleteReal(String fileiddisk);
	
	
	
	
}
