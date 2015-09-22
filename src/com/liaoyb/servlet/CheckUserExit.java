package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.service.IUserService;
import com.liaoyb.service.impl.UserService;
import com.liaoyb.util.Json;
/**
 * ������첽�����û����Ƿ��Ѿ�ע�����
 * ����json����,   
 * @author Liao-Pc
 *
 */
public class CheckUserExit extends HttpServlet {

	private IUserService userServ=new UserService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		
		
		boolean isExit=userServ.userExitByName(username);
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(isExit){
			//�û�������,   ����json    "0"--�û�������,    "200"---
		
			out.print(Json.writeLogState("0"));
		}else{
			out.print(Json.writeLogState("200"));
		}
		
		
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	
	}

}
