package com.liaoyb.web.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * ��������
 * @author Liao-Pc
 *��һ���������һ�η�����վ��ʱ��J2EEӦ�÷��������½�һ��HttpSession���� ��
 *������ HttpSession�����¼� �����ע����HttpSessionListener�¼���������
 *������HttpSessionListener�¼���������sessionCreated�������෴��
 *�������������ʽ�����ʱ��ʱ��J2EEӦ�÷�������������Ӧ��HttpSession����
 *���� HttpSession�����¼���
 *ͬʱ������ע��HttpSessionListener�¼���������sessionDestroyed������
 *
 *
 */
public class SessionCounter implements HttpSessionListener {
	//Session�����¼�
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ServletContext servCont=event.getSession().getServletContext();
		Integer numSessions=(Integer)servCont.getAttribute("numSessions");
		if(numSessions==null){
			numSessions=1;
		}else{
			int count=numSessions.intValue();
			numSessions=++count;
		}
		servCont.setAttribute("numSessions", numSessions);
		System.out.println("�����û�����");

	}
	
	
	//SessionʧЧ�¼�
	//��ʧЧ���û��Ƴ�
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext servCont=se.getSession().getServletContext();
		Integer numSessions=(Integer) servCont.getAttribute("numSessions");
		if(numSessions==null){
			numSessions=0;
		}else{
			int count=numSessions.intValue();
			numSessions=count-1;
		}
		servCont.setAttribute("numSessions", numSessions);
		
		//
		 Map<HttpSession,String >users=(Map<HttpSession, String>) servCont.getAttribute("users");
		 if(users!=null){
			 users.remove(se.getSession());
		 }
		
		System.out.println("���û��뿪");
	}

}
