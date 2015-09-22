package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liaoyb.po.User;
import com.liaoyb.service.IUserService;
import com.liaoyb.service.impl.UserService;
import com.liaoyb.util.Json;
import com.liaoyb.util.ServletUtil;
/**
 * �û���¼
 * ������ص�½,
 * ���½���԰���һ��������
 * @author Liao-Pc
 *
 */
public class LoginServelet extends HttpServlet {
	private Map<HttpSession,String >users=new HashMap<HttpSession,String>();
	
	private IUserService userServ=new UserService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext app=request.getServletContext();
		
		request.setCharacterEncoding("utf-8");
		String strname=request.getParameter("username");
		String password=request.getParameter("password");
	
		//�����ԭ���ĵ�½��Ϣ
		ServletUtil.clearLoginInfo(request);
		
		//��service���½
		User user=userServ.login(strname, password);
		
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(user!=null){
			if(users.containsValue(user.getUserId())){
				//�û����ڣ��������ߣ�����֪ͨԭ����
				
				//����,ȡ�ö�Ӧ��Session
				HttpSession key = null;
				for(Map.Entry<HttpSession, String >m:users.entrySet()){
					if(m.getValue().equals(user.getUserId())){
						key=m.getKey();
					}
				}
				
				//�п������session�Ѿ�ʧЧ�ˣ��Ͱ����Ƴ�
				
				try{
				
				//֪ͨ
			
				
				//Ӧ��ʹ������
				key.invalidate();
				System.out.println("key:"+key);
				
				
				}catch(Exception e){
					out.print(Json.writeLogState("0"));//�����쳣
				}
				
				//�Ƴ�
				users.remove(key);
				
				
			}
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			users.put(session, user.getUserId());
			app.setAttribute("users", users);
			
			
//			//�ض�����ҳ
//			response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
			
			//����json���ݣ����ߵ�½��״̬   "0"-��½ʧ��       "200"-�ɹ�
			
			
			out.print(Json.writeLogState("200"));
			
			
			
		}else{
//			//��½ʧ��,���µ�½
//			response.sendRedirect(getServletContext().getContextPath()+"/loginagain.jsp?message=Fail");
			
			
			out.print(Json.writeLogState("0"));
		}
		
		
		
		
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
