package com.liaoyb.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;


public class ServletUtil {
	
	//����û��Ƿ��½,�����½����user,���򷵻�null,
	public static User checkUserLogin(HttpServletRequest request, HttpServletResponse response){
		Object objUser=request.getSession().getAttribute("user");
		User user=null;
		if(objUser!=null){
			user=(User) objUser;
		}
		
		return user;
		
	}
	
	//����û���¼�����û�е�½�������������json��ʾ��Ϣ
	public static User checkUserLogMess(HttpServletRequest request, HttpServletResponse response){
		User user=checkUserLogin(request, response);
		if(user==null){
			/* ���ø�ʽΪtext/json */
			response.setContentType("text/json");
			/* �����ַ���Ϊ'UTF-8' */
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��ǰδ��¼����ת����½ҳ��
			System.out.println("��ǰ�û�δ��¼");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			out.print(Json.writeMess("��ǰ�û�δ��¼"));
			
		}
		return user;
	}
	public static List<MyFile> getAllFilesInSession(HttpServletRequest request){
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		}
		return allFiles;
	}
	
	
	public static void removeMyFileInSession(HttpServletRequest request,String fileiddisk){
		//���session�д���
		List<MyFile> allFiles=getAllFilesInSession(request);
		if(allFiles==null)
			return;
		
		//����
		for(MyFile file:allFiles){
			if(file.getFileIdInDisk().equals(fileiddisk)){
				MyFile del=file;
				allFiles.remove(del);
				return;
			}
			
		}
	}
	
	
	public static  void clearLoginInfo(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("files");
	}
	
	
	
}
