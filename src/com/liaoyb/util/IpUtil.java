package com.liaoyb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/** 
	 * 获取访问用户的客户端IP（适用于公网与局域网）. 
	 */  
	public static final String getIpAddr(final HttpServletRequest request)  
	        throws Exception {  
	    if (request == null) {  
	        throw (new Exception("getIpAddr method HttpServletRequest Object is null"));  
	    }  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip= request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip= request.getRemoteAddr();  
	    }  
	  

	  
	    return ip;  
	}

	public static String getFirstIP(final HttpServletRequest request) throws Exception {
		
		String ip=getIpAddr(request);
		// 多个路由时，取第一个非unknown的ip  
	    final String[] arr = ip.split(",");  
	    for (final String str : arr) {  
	        if (!"unknown".equalsIgnoreCase(str)) {  
	            ip = str;  
	            break;  
	        }  
	    }
		return ip;
	}
	
	public static String gainLocalHost(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip=addr.getHostAddress();//获得本机IP
		return ip;
	}
	
	
	public static String getServerIP()  {  
        InputStream ins = null;  
        String serverIp="";
        try {  
            URL url = new URL("http://1111.ip138.com/ic.asp");  
            URLConnection con = url.openConnection();  
            ins = con.getInputStream();  
            InputStreamReader isReader = new InputStreamReader(ins, "GBK");  
            BufferedReader bReader = new BufferedReader(isReader);  
            StringBuffer webContent = new StringBuffer();  
            String str = null;  
            while ((str = bReader.readLine()) != null) {  
                webContent.append(str+"\n");  

            }  
         
            int start = webContent.indexOf("[") + 1;  
            int end = webContent.indexOf("]");  
            serverIp= webContent.substring(start,end);  
        }catch(Exception e){
        	e.printStackTrace();
        }
        	finally {  
        }
            if (ins != null) {  
                try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }
			return serverIp;  
        }  
      
  
    public static String getMyIPLocal() throws IOException {  
        InetAddress ia = InetAddress.getLocalHost();  
        return ia.getHostAddress();  
    }  

	
	
	
}
