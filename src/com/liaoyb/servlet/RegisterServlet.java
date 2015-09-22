package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liaoyb.dao.UserDao;
import com.liaoyb.po.User;
import com.liaoyb.service.IUserService;
import com.liaoyb.service.impl.UserService;

public class RegisterServlet extends HttpServlet {
	private IUserService userServ=new UserService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String verifyCode=request.getParameter("verifyCode");
		
		System.out.println(username+":"+password);
		
		/**
		 * У����
		 */
		String sessionVerifyCode = (String) request.getSession().getAttribute("sessionVerifyCode");
		if (!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
			//��ת
			response.sendRedirect(getServletContext().getContextPath()+"/register.jsp?message=Fail");
			return;
		}
		
		User user=userServ.register(username, password);
		if(user!=null){
			//ע��ɹ�,������ҳ
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
		}else{
			//ע��ʧ��
			response.sendRedirect(getServletContext().getContextPath()+"/register.jsp?message=Fail");
		}
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
