package com.liaoyb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.liaoyb.po.FileType;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;


public class UploadServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
        
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�ȴ�session�в����Ƿ����ļ�����,����У��ϴ��ļ��󣬶�����и���
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		}
		
		
		
		
		System.out.println("���ϴ�");
		/* ���ø�ʽΪtext/json */
		response.setContentType("text/json");
		/* �����ַ���Ϊ'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter printout = response.getWriter();
		
		//��session���õ���ǰuser,���������õ��ļ������������е�path(���Ŀ¼��·��)
		User user=(User) request.getSession().getAttribute("user");
		
		if(user==null){
			//��ǰδ��¼����ת����½ҳ��
			System.out.println("δ��½");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			
			printout.print(Json.writeMess("δ��½"));
			return;
		}
		
		String path="";
		String filename="";
		float size;
		
		
		
		//���·��
		
		String savePath=this.getServletContext().getRealPath("/WEB-INF/upload");
		File file=new File(savePath);
		//�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
		if(!file.exists()&&!file.isDirectory()){
			System.out.println(savePath+"Ŀ¼�����ڣ����ڴ���");
			file.mkdirs();
		}
		//��ʾ��Ϣ
		String message=".......";
		
		
		 //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
		//1.����һ��DiskFileItemFactory����
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//2.����һ���ļ��ϴ�������
		ServletFileUpload upload=new ServletFileUpload(factory);
		//����ϴ��ļ�������������
		upload.setHeaderEncoding("utf-8");

		//upload.setProgressListener(new UploadProgressListener(request.getSession(),new UploadStatus()));//�����ϴ����ȼ���,�������еĽ���
		
		//3.�ж��ύ�����������Ƿ����ϴ���������
		if(!ServletFileUpload.isMultipartContent(request)){
			//���մ�ͳ��ʽ��ȡ����
			
			System.out.println("��ͨ��");
			return;
		}
		
		//4.ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>����
		//��ÿһ��FileItem��Ӧһ��Form����������
		try {
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list){
				
				//���fileitem�з�װ������ͨ�����������
				if(item.isFormField()){
					String name=item.getFieldName();
					//�����ͨ����������ݵ�������������
					String value=item.getString("utf-8");
					
					
					//�����õ�path
					if("path".equals(name)){
						path=value;
						// ��ʽ��path
						path = path.replace("_", "/");
						System.out.println("ת�����path:" + path);
					}
					
					
					//����Ӧ�����ļ���
					if("filename".equals(name)){
						filename=value;
					}
					if("size".equals(name)){
						size=Float.parseFloat(value);
						//����Ҫ��֤�����Ƿ�
						if(!diskServ.validate(user,size)){
							//�ռ䲻��
							message="�ռ䲻��";
							break;
						}
					}
					
					System.out.println(name+"="+value);
					
				}else{
					System.out.println("���ļ�");
					
					
					//���fileitem�з�װ�����ϴ��ļ�
					//�õ��ϴ����ļ�����
					 String origfilename=item.getName();
					System.out.println(origfilename);//�ļ��������ļ�·��
					if(origfilename==null||origfilename.trim().equals("")){
						continue;
					}
					
					
					long fileSize=item.getSize();//�ļ���С
					System.out.println("�ļ���С:"+fileSize+"�ֽ�");
					
					
					
					if(filename==null||filename==""){
						filename=origfilename;
					}
					//���ܺ�ԭ��Ŀ¼�������ļ�ͬ��
					
					
					
					
					
					
					
					//���ﱣ������֣�ʵ�����ִ������ݿ��У�
					
					String saveFileName=UUID.randomUUID().toString();
					String fileUrl=savePath+"\\"+saveFileName;
					
					//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
				   //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename=filename.substring(filename.lastIndexOf("\\")+1);
					//ȡ��׺
					fileUrl+=filename.substring(filename.lastIndexOf("."));
					
					
					//��ȡitem�е��ϴ��ļ���������
					InputStream in=item.getInputStream();
					//����һ���ļ���������
					FileOutputStream out=new FileOutputStream(fileUrl);
					byte buffer[]=new byte[1024];
					int len=0;
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
						out.flush();
					}
					out.close();
					in.close();
					//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					message="�ļ��ϴ��ɹ�";
					
					
					
					//diskServ�е��ϴ�,�����ϴ���Ϣ
					//����һ��MyFile
					
					//�ļ�ʵ��λ��
					
					MyFile myfile=createMyFile(user.getUserId(),filename,fileSize,fileUrl,path);
					
					diskServ.upload(myfile);
					//����session��Myfile����
					if(allFiles!=null){
						allFiles.add(myfile);
					}
					
					
					
				}
			}
			
			
			//�ϴ��ɹ�,
			
			
			
			
			
		} catch (FileUploadException e) {
			message="�ļ��ϴ�ʧ��";
			e.printStackTrace();
		}
//		message=URLEncoder.encode(message,"utf-8");
		System.out.println("json_mess:"+Json.writeMess(message));
		printout.print(Json.writeMess(message));
		//
		System.out.println(message);
		
//		response.sendRedirect(this.getServletContext().getContextPath()+"/upload_message.jsp?message="+message);
//		
	}
	
	
	//�½�һ��MyFile 
	public MyFile createMyFile(String userId,String filename,float size,String fileUrl,String path){
		//SourceFile
		//��filename�еõ�FileType
		
		FileType fileType=FileType.other;
		
		if(filename.matches("(.*\\.txt)|(.*\\.doc)|(.*\\.java)")){
			//�ı��ļ�
			fileType=FileType.txt;
		}
		if(filename.matches("(.*\\.mp4)|(.*\\.avi)|(.*\\.wmv)")){
			//��Ƶ
			fileType=FileType.film;
		}
		if(filename.matches("(.*\\.jpg)|(.*\\.png)|(.*\\.jpeg)")){
			//ͼƬ
			fileType=FileType.picture;
		}
		if(filename.matches("(.*\\.zip)|(.*\\.rar)|(.*\\.zip)|(.*\\.7z)|(.*\\.iso)")){
			fileType=FileType.compress;
		}
		if(filename.matches("(.*\\.mp3)|(.*\\.wav)|(.*\\.aac)")){
			//����
			fileType=FileType.music;
		}
		
		
		
		
		SourceFile sourFile=new SourceFile(UUID.randomUUID().toString(), fileUrl, new Date(), size, userId, fileType, 0);
		MyFile myFile=new MyFile(UUID.randomUUID().toString(), userId, filename, path+"/"+filename, 1, sourFile);
		myFile.setCreateTime(new Date());
		
		//MyFile
		
		return myFile;
		
	}
	
	
	
	
	

}
