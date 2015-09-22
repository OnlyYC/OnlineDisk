package com.liaoyb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liaoyb.po.FileType;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.util.JdbcUtil;

public class DiskDao {
	private JdbcUtil util = new JdbcUtil();

	//�õ����е��ļ�
	public List<MyFile>gainAllFiles(String userid){
		List<MyFile> userFiles=new ArrayList<MyFile>();
		ResultSet result=util.executeQuery("select fileiddisk from myfiles where userid=?", userid);
		try {
			boolean isExitData=false;
			while(result.next()){
				isExitData=true;
				//ͨ��fileiddisk�õ�MyFile
				
				userFiles.add(gainMyFileByFileIdDisk(result.getString("fileiddisk")));
				
			}
			if(!isExitData){
				//û�е�ǰuser���ߣ�û���ļ����ļ���,����null
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userFiles;
		
	}
	
	
	
	
	
	
	
	// �ҵ���������
	public float gainCapacity(String userId) {
		float capacity=-1;
		ResultSet result=util.executeQuery("select capacity from diskinfo where userid=?", userId);
		try {
			if(result.next()){
				capacity=result.getFloat("capacity");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capacity;

	}

	// ����ʹ�ÿռ�
	public float gainUseSapce(String userId) {
		// û�д�user������-1
		float useSapce = -1;
		ResultSet result = util.executeQuery(
				"select usespace from diskinfo where userid=?", userId);
		try {
			if (result.next()) {
				useSapce = result.getFloat("usespace");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return useSapce;
	}

	public SourceFile gainSourceFileByFileId(String fileId) {
		
		SourceFile sourFile=new SourceFile();
		
		ResultSet result=util.executeQuery("select * from upload where fileid=?", fileId);
		try {
			if(result.next()){
				sourFile.setFileId(fileId);
				sourFile.setFileUrl(result.getString("fileurl"));
				int fitype=result.getInt("filetype");
				sourFile.setFileType(transformToFileType(fitype));
				sourFile.setAddTime(result.getDate("addtime"));
				sourFile.setUserId(result.getString("userid"));
				sourFile.setDownloadtime(result.getInt("downloadtime"));
				sourFile.setSize(result.getFloat("size"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourFile;

	}
	
	//�������ش����������Ѿ���1��ֱ�Ӹ���
	public void updateDownloadTime(SourceFile sourFile){
		util.executeUpdate("update upload set downloadtime=? where userid=?", sourFile.getDownloadtime(),sourFile.getUserId());
	}
	
	
	//��int ת��Ϊtype����
	public FileType transformToFileType(int fitype){
//		txt,//0
//		film,//1
//		music,//2
//		picture,//3
//		compress,//4ѹ����
//		other//5
		
		FileType fileType=FileType.other;
		switch(fitype){
		case 0:fileType=FileType.txt;break;
		case 1:fileType=FileType.film;break;
		case 2:fileType=FileType.music;break;
		case 3:fileType=FileType.picture;break;
		case 4:fileType=FileType.compress;break;
		case 5:fileType=FileType.other;break;
		}
		
		return fileType;
		
	}
	
	
	
	public ShareInfo gainShareInfoByShareId(String shareid){
		return null;
		
	}
	
	public MyFile gainMyFileByFileIdDisk(String fileiddisk){
		//myFile
		MyFile myFile=new MyFile();
		myFile.setFileIdInDisk(fileiddisk);
		String fileid=null;
		ResultSet result=util.executeQuery("select * from myfiles where fileiddisk=?", fileiddisk);
		try {
			if(result.next()){
				myFile.setPath(result.getString("path"));
				myFile.setFileName(result.getString("filename"));
				myFile.setType(result.getInt("type"));
				myFile.setUserId(result.getString("userid"));
				myFile.setCreateTime(result.getDate("createtime"));
				myFile.setIsdelete(result.getInt("isdelete"));
				fileid=result.getString("fileid");
				
				
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fileid==null||fileid.isEmpty()){
			//û��fileid�����ļ���,SourceFile�Ͳ����ã�Ϊnull
			return myFile;
			
			
		}
		
		
		//ͨ��fileid�õ�sourcefile
		SourceFile sourFile=gainSourceFileByFileId(fileid);
		myFile.setSourceFile(sourFile);
		
		
		
		
		return myFile;
		
	}
	
	
	//�������ض����ļ����������ļ���
	// ����,��¼����
		public void download(MyFile file) {
			// ����Դ�ļ����ش���
			
			
			
			
			
		}

	// �ϴ�,д�����ݿ��ϴ�����Ϣ���ϴ�ʱ�䣬�ϴ��ļ���С�������λ��()
	// �������б����λ��,��MyFile��path��
	// txt,//0
	// film,//1
	// music,//2
	// picture,//3
	// compress,//4ѹ����
	// other//5
	public void upload(MyFile myFile) {
		SourceFile sourFile = myFile.getSourceFile();
		int filetype = 4;
		switch (sourFile.getFileType()) {
		case txt:
			filetype = 0;
			break;
		case film:
			filetype = 1;
			break;
		case music:
			filetype = 2;
			break;
		case picture:
			filetype = 3;
			break;
		case compress:
			filetype = 4;
			break;
		case other:
			filetype = 5;
			break;
		}

		// д��upload��
		util.executeUpdate(
				"insert into upload(fileid,fileurl,filetype,addtime,size,userid) values(?,?,?,?,?,?) ",
				sourFile.getFileId(), sourFile.getFileUrl(), filetype,
				new java.sql.Date(sourFile.getAddTime().getTime()),
				sourFile.getSize(), sourFile.getUserId());

		// д��myfiles��
		util.executeUpdate(
				"insert into myfiles(fileiddisk,path,filename,type,fileid,userid,createtime) values(?,?,?,?,?,?,?)",
				myFile.getFileIdInDisk(), myFile.getPath(),
				myFile.getFileName(), myFile.getType(), sourFile.getFileId(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
		// ��������diskinfo��Ϣ,����
		
		float origuse=gainUseSapce(myFile.getUserId());
		util.executeUpdate("update diskinfo set usespace=? where userid=?", origuse+sourFile.getSize(),myFile.getUserId());

	}

	

	// ��֤�Ƿ���Ի�÷����ļ�
	public boolean valiGetShareFile(String shareUrl, String evidence) {
		return false;

	}

	// ͨ����֤���õ������ļ�
	public MyFile gainShareFile(String shareUrl) {
		return null;

	}

	// ���ط����ļ�(��ʱ�����ط����ļ���һ����) sharePath���ڷ����ļ��е�path
	public MyFile downShareFile(ShareInfo shareInfo, String sharePath) {
		return null;

	}

	// ����,��ShareInfoд�����ݿ� ��share
	public void share(ShareInfo shareInfo) {

	}

	// ת��,����ת��һ����
	// savePath�����λ��
	public boolean dumped(ShareInfo shareInfo, String sharePath, String savePath) {
		return false;

		// ��Ҫ��֤�ռ��Ƿ�

	}



	

	public MyFile gainMyFileByName(String fileName) {

		return null;
	}

	public MyFile gainMyFileByDiskPath(String diskPath) {

		return null;
	}

	public ShareInfo gainShafoByshareUrl(String shareInfo) {

		return null;
	}

	// ������Ϣ
	public OnlineDisk myDisk(User user) {
		ResultSet result = util.executeQuery(
				"select * from diskinfo where userid=?", user.getUserId());
		OnlineDisk myDisk = new OnlineDisk();
		myDisk.setUserId(user.getUserId());
		try {
			myDisk.setCapacity(result.getFloat("capacity"));
			myDisk.setUserspace(result.getFloat("usespace"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.setDisk(myDisk);
		return myDisk;

	}
	
	public void addMyFile(MyFile myFile){
		//���ļ���
		if(myFile.getType()==0){
		// д��myfiles��
		util.executeUpdate(
				"insert into myfiles(fileiddisk,path,filename,type,userid,createtime) values(?,?,?,?,?,?)",
				myFile.getFileIdInDisk(), myFile.getPath(),
				myFile.getFileName(), myFile.getType(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
		}else{
			//���ļ�
			util.executeUpdate(
					"insert into myfiles(fileiddisk,path,filename,type,fileid,userid,createtime) values(?,?,?,?,?,?,?)",
					myFile.getFileIdInDisk(), myFile.getPath(),
					myFile.getFileName(), myFile.getType(), myFile.getSourceFile().getFileId(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
			
		}
		
	}
	
	
	//ɾ���ļ�����isdelete��Ϊ1
	public void deleteMyFile(String fileiddisk){

		util.executeUpdate("update myfiles set isdelete=? where fileiddisk=?", 1,fileiddisk);
		
		
		
	}
	
	//���ɾ���ļ�
	public void deleteMyFileReal(String fileiddisk){
		util.executeUpdate("delete from myfiles where fileiddisk=?", fileiddisk);
	}
	
}
