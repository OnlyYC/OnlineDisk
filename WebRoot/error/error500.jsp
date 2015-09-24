<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" import="java.util.*,java.io.*,org.apache.commons.io.IOUtils,com.liaoyb.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'errorPage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <h1>出错了，怪我咯!</h1> 
   <%
   //设置isErrorPage="true"，可以拿到内置对象exception
   exception.printStackTrace(response.getWriter());//向页面输出
  
   
   //发送邮件
   
   
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
   
   exception.printStackTrace(pw);

   Mail.sendMail(sw.toString());
   %>
  </body>
</html>
