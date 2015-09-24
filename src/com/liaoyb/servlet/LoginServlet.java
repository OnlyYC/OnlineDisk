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
 * 用户登录
 * 不能异地登陆,
 * 后登陆可以把另一个挤下线
 * @author Liao-Pc
 *
 */
public class LoginServlet extends HttpServlet {
	private Map<HttpSession,String >users=new HashMap<HttpSession,String>();
	
	private IUserService userServ=new UserService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext app=request.getServletContext();
		
		request.setCharacterEncoding("utf-8");
		String strname=request.getParameter("username");
		String password=request.getParameter("password");
	
		//清理掉原来的登陆信息
		ServletUtil.clearLoginInfo(request);
		
		//用service层登陆
		User user=userServ.login(strname, password);
		
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(user!=null){
			if(users.containsValue(user.getUserId())){
				//用户存在，被挤下线，并且通知原来的
				
				//遍历,取得对应的Session
				HttpSession key = null;
				for(Map.Entry<HttpSession, String >m:users.entrySet()){
					if(m.getValue().equals(user.getUserId())){
						key=m.getKey();
					}
				}
				
				//有可能这个session已经失效了，就把它移除
				
				try{
				
				//通知
			
				
				//应该使他到期
				key.invalidate();
				System.out.println("key:"+key);
				
				
				}catch(Exception e){
					out.print(Json.writeLogState("0"));//出现异常
				}
				
				//移除
				users.remove(key);
				
				
			}
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			users.put(session, user.getUserId());
			app.setAttribute("users", users);
			
			
//			//重定向到首页
//			response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
			
			//返回json数据，告诉登陆的状态   "0"-登陆失败       "200"-成功
			
			
			out.print(Json.writeLogState("200"));
			
			
			
		}else{
//			//登陆失败,重新登陆
//			response.sendRedirect(getServletContext().getContextPath()+"/loginagain.jsp?message=Fail");
			
			
			out.print(Json.writeLogState("0"));
		}
		
		
		
		
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
