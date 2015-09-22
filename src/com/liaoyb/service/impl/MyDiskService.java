package com.liaoyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.liaoyb.dao.DiskDao;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.ShareType;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;

public class MyDiskService implements IDiskService {
	private DiskDao diskDao=new DiskDao();



	@Override
	public boolean validate(User user,float fileSize) {
		float num=diskDao.gainCapacity(user.getUserId())-(diskDao.gainUseSapce(user.getUserId())+fileSize);
		System.out.println("num:"+num);
		if(num<0)
			return false;
		
		//�ȷ���true
		return true;
//		return false;
	}

	

	


	@Override
	public void downShareFile(ShareInfo shareInfo, String sharePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public ShareInfo share(MyFile myFile, ShareType sharetype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dumped(ShareInfo shareInfo, String sharePath) {
		// TODO Auto-generated method stub
		return false;
	}

	

	

	@Override
	public boolean valiGetShareFile(ShareInfo shareInfo, String evidence) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MyFile gainMyFileByName(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyFile gainMyFileByDiskPath(String diskPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShareInfo gainShafoByshareUrl(String shareInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	//�ϴ�
	@Override
	public void upload(MyFile file) {
		
		
		diskDao.upload(file);
		
		
		
	}

	
	


	
	//������Ϣ
	@Override
	public OnlineDisk myDisk() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	//����,�����ļ�url
	@Override
	public MyFile downloadMySelf(User currentUser,String fileiddisk) {
		//�����ǰuser�����ļ���ӵ����,��δ��½,����null
		MyFile myFile=diskDao.gainMyFileByFileIdDisk(fileiddisk);
		if(currentUser==null){
			return null;
		}
		if(myFile==null){
			return null;
		}
		if(!myFile.getUserId().equals(currentUser.getUserId())){
			return null;
		}
		//�������ش���
		SourceFile sourFile=myFile.getSourceFile();
		sourFile.setDownloadtime(sourFile.getDownloadtime()+1);;
		diskDao.updateDownloadTime(sourFile);
		return myFile;
	}

	@Override
	public MyFile downloadShare(String shareid) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	//�õ����е��ļ�
		@Override
		public List<MyFile>gainUserFiles(String userid){
		return diskDao.gainAllFiles(userid);
		
			
		}
		
		//�����ļ������ط���·�����ļ��б�
		//path   root/..
	@Override
		public List<MyFile>FilesUnderPath(List<MyFile> files,String path){
		List<MyFile> finFiles=new ArrayList<MyFile>();
		//����
		for(MyFile file:files){
			if(file.getPath().matches(path+"/[^/]*")){
				finFiles.add(file);
			}
				
	
		}
		
			return finFiles;
		
	}

	
	
	//�½��ļ���
	@Override
	public void newDir(MyFile dirFile) {
		
		diskDao.addMyFile(dirFile);
		// TODO Auto-generated method stub
		
	}
	
	//ɾ��,��ɾ���ı�־��Ϊ1
		public void delete(String fileiddisk){
			//�ȵõ���Ӧ��MyFile
			MyFile delFile=diskDao.gainMyFileByFileIdDisk(fileiddisk);
			//���ļ���
			if(delFile.getType()==0){
				//���ļ���
				//�ļ����µ��ļ�
				List<MyFile> delFiles=FilesUnderPath(gainUserFiles(delFile.getUserId()),delFile.getPath());
				//����ɾ��
				for(MyFile file:delFiles){
					diskDao.deleteMyFile(file.getFileIdInDisk());
				}
				
				
			}
			//��ɾ���Լ�
			diskDao.deleteMyFile(fileiddisk);
		}
		
		//���ɾ��
		public void deleteReal(String fileiddisk){
			diskDao.deleteMyFileReal(fileiddisk);
		}






	

	

}
