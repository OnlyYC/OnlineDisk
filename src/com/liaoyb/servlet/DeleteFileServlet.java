package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;
import com.liaoyb.util.ServletUtil;

public class DeleteFileServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=ServletUtil.checkUserLogMess(request, response);
		if(user==null)
			return;
		// �õ���������data_id,
		request.setCharacterEncoding("utf-8");
		String data_id = request.getParameter("data_id");
		System.out.println("Ҫɾ����data_id"+data_id);
	
		//������ļ��У�Ҫ�����ļ����µĶ�ɾ��
		
		
		diskServ.delete(data_id);
		//����session�е�files���
		request.getSession().removeAttribute("files");
		
		
		response.setContentType("text/json");
		/* �����ַ���Ϊ'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(Json.writeMess("ɾ���ɹ�"));
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
