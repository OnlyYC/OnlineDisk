package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.util.Json;
/**
 * ����֤�������֤
 * ����json����
 * @author Liao-Pc
 *
 */
public class CheckValideCode extends HttpServlet {

//����״̬��Ϣ,    "0"---��ʾ��֤ʧ��,      "200"---�ɹ�
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String valicodepar=request.getParameter("verifyCode");
		
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(valicodepar==""||valicodepar.trim()==""){
			//�������֤��Ϊ��
			out.print(Json.writeLogState("0"));
			
		}
		
		String valicode=(String) request.getSession().getAttribute("sessionVerifyCode");
		if(valicode!=null&&valicode.equals(valicodepar)){
			//��֤����ȷ
			
			out.print(Json.writeLogState("200"));
		}else{
			out.print(Json.writeLogState("0"));
		}
		
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
