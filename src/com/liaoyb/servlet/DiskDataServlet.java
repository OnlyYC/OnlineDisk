package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.Table;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;

public class DiskDataServlet extends HttpServlet {
	private IDiskService diskServ = new MyDiskService();

	/**
	 * ����������������ļ���json����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/* ���ø�ʽΪtext/json */
		response.setContentType("text/json");
		/* �����ַ���Ϊ'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();


		// �����path��root_myDir_jj.java���ָ�ʽ
		String path = request.getParameter("path");
		System.out.println("��������·��:" + path);
		Object userobj = request.getSession().getAttribute("user");
		if (userobj == null) {
			// ��ǰ�û�δ��¼
			out.print("message=��ǰ�û�δ��¼");
			return;
		}
		User user = (User) userobj;

		// �ȴ�session�в����Ƿ����ļ�����,files,û�еĻ����½�һ��
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		} else {
			// �������ļ��ҳ������ŵ�session���棬��������й���·�����ϵ�
			allFiles = diskServ.gainUserFiles(user.getUserId());
			request.getSession().setAttribute("files", allFiles);

		}
		// ����Ϊnull���û�û���ļ�����
		if (allFiles == null) {
			out.print("message=û���κ��ļ���Ϣ");
			return;
		}

		// ��ʽ��path
		path = path.replace("_", "/");
		System.out.println("ת�����path:" + path);

		// �õ����˺���ļ��б�
		List<MyFile> finFiles = diskServ.FilesUnderPath(allFiles, path);

		// finFiles����sizeΪ0����ʾ��ǰĿ¼��û������

		List<Table> tableData = new ArrayList<Table>();
		for (MyFile file : finFiles) {
			
			//�Ѿ���ɾ���Ĳ���������
			if(file.getIsdelete()==1){
				continue;
			}
			
			int fileicon = 6;
			if ( file.getType() == 1)
				switch (file.getSourceFile().getFileType()) {
				// txt,//0
				// film,//1
				// music,//2
				// picture,//3
				// compress,//4ѹ����
				// other//5
				case txt:
					fileicon = 0;
					break;
				case film:
					fileicon = 1;
					break;
				case music:
					fileicon = 2;
					break;
				case picture:
					fileicon = 3;
					break;
				case compress:
					fileicon = 4;
					break;
				case other:
					fileicon = 5;
					break;

				}
			// ��ʽ��date
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String createTime;
			 createTime = format.format(file.getCreateTime());
			
			
			String size = "";
			if (file.getType() == 0) {
				size = "-";
			} else {
				float filesize = file.getSourceFile().getSize();
				if (filesize > 1024)
					size = (int) (filesize / 1024) + "K";
				if (filesize > 1024 * 1024)
					size = (int) (filesize / (1024 * 1024)) + "M";

			}

			Table tab = new Table(fileicon + "", file.getFileName(),
					file.getType() + "", createTime, file.getFileIdInDisk(), size);
			tableData.add(tab);

		}
		// json array
		JSONArray ja1 = JSONArray.fromObject(tableData);
		System.out.println(ja1.toString());

		out.write(ja1.toString());

		// дjson���ݵ������

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
