package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.util.ServletUtil;

public class LogoutServlet extends HttpServlet {


	//ע����ǰ�û�,��ת����ҳ
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����½��Ϣ
		ServletUtil.clearLoginInfo(request);
		//��ת����ҳ
		response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
		
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

}
