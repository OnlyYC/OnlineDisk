package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.FileType;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;
import com.liaoyb.util.ServletUtil;

public class NewDirServlet extends HttpServlet {

	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String dirname=request.getParameter("dirname");
		System.out.println("dirname:"+dirname);
		
		//���user�Ƿ��½
		User user=ServletUtil.checkUserLogMess(request, response);
		if(user==null)
			return;
		// ��ʽ��path
		String path=request.getParameter("path");
		 path = path.replace("_", "/");
		System.out.println("ת�����path:" + path);
		//�ļ������ܺ�ͬһ�����ļ���ͬ��,������������Ѿ�����У����
		
		
		
		
		
		MyFile myFile=createMyFile(user.getUserId(), dirname, path);
		//service���½��ļ���
		diskServ.newDir(myFile);
		//��myFile��ӵ�session�е�allfiles
		List<MyFile> files=ServletUtil.getAllFilesInSession(request);
		if(files!=null)
			files.add(myFile);
		
		//����json����
		/* ���ø�ʽΪtext/json */
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(Json.writeMess("�½��ɹ�"));
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	
	//�½�һ��MyFile 
		public MyFile createMyFile(String userId,String dirname,String path){
			MyFile myFile=new MyFile(UUID.randomUUID().toString(), userId, dirname, path+"/"+dirname, 0);
			myFile.setCreateTime(new Date());
			//MyFile
			
			return myFile;
			
		}

}
