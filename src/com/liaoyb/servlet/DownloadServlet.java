package com.liaoyb.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;

public class DownloadServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		Object objUser=request.getSession().getAttribute("user");
		User user=null;
		if(objUser==null){
			/* ���ø�ʽΪtext/json */
			response.setContentType("text/json");
			/* �����ַ���Ϊ'UTF-8' */
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			//��ǰδ��¼����ת����½ҳ��
			System.out.println("��ǰ�û�δ��¼");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			out.print(Json.writeMess("��ǰ�û�δ��¼"));
			return;
		}
		
		user=(User) objUser;
		
	

		MyFile myFile=null;
		request.setCharacterEncoding("utf-8");
		String data_id=request.getParameter("data_id");
		String share_id=request.getParameter("share_id");
		System.out.println(data_id);
		if(data_id!=null){
			//�û��Լ����ļ�
			//data_id���ǵ�ǰuser��,��Ȼ��������
			myFile=diskServ.downloadMySelf(user,data_id);
			//��������
			if(myFile==null){
				//ת��������ҳ��
				request.setAttribute("error", "�����������");
				System.out.println("���ش���");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				
				return ;
			}
			
			
		}
		if(share_id!=null){
			//ͨ����������
			//�Ƿ����share_id
			
			
			
			
		}
		
		
		String fileUrl=myFile.getSourceFile().getFileUrl();
		
		
		response.setContentType("application/x-msdownload");
		// String
		// str="attachment;filename="+URLEncoder.encode(fileName,"utf-8");
//		String str = "attachment;filename="
//				+ new String(fileName.getBytes("utf-8"), "iso8859-1");
		//���������������������
		String str="attachment;filename="+encodeFileName(request,myFile.getFileName());
		response.setHeader("Content-Disposition", str);
		/*
		 * servletʵ������ʱ������ͻ��˰�װ��Ѹ�ף����ֲ�����ȷ���ء�
		 * ����ԭ�������httpͷ���õ����⣬���ļ�����CONTEN-TYPE���ļ�����CONTEN-LENGTH��
		 * response.setContentLength(fileSize);
		 * response.setContentType(contentType);
		 */
		if(!new File(fileUrl).exists()){
			System.out.println("�ļ�������");
			//ת��������ҳ��
			
			request.setAttribute("error", "�����ļ�������");
			request.getRequestDispatcher(this.getServletContext().getContextPath()+"/error.jsp").forward(request, response);
			return;
		}
		
		
		InputStream is = new FileInputStream(fileUrl);
		ServletOutputStream sos = response.getOutputStream();
		byte[] data = new byte[2048];
		int len = 0;
		while ((len = is.read(data)) > 0) {
			sos.write(data, 0, len);
		}
		System.out.println("�������");
		sos.close();
		is.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}
	
	//�����ͬ�����������������������
		private  String encodeFileName(HttpServletRequest req, String name)
				throws UnsupportedEncodingException {

			String agent = req.getHeader("USER-AGENT").toLowerCase();
			System.out.println(agent);
			if (agent != null && agent.indexOf("firefox") < 0
					&& agent.indexOf("safari") < 0) {
				return URLEncoder.encode(name, "UTF8");
			}
			
			return new String(name.getBytes("UTF-8"), "ISO8859-1");

		}

}
