package com.douzone.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.guestbook.dao.GuestbookDao;
import com.douzone.guestbook.vo.GuestbookVo;
import com.douzone.web.WebUtils;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//요청 분리(식별)
		String action = request.getParameter("a");
		if("index".equals(action)) {
			
		} else if("deleteform".equals(action)) {
			WebUtils.forward(request, response, "/WEB-INF/views/deleteform.jsp");
		} else if("add".equals(action)){
			GuestbookVo vo = new GuestbookVo();
			vo.setName(request.getParameter("name"));
			vo.setPassword(request.getParameter("password"));
			vo.setMessage(request.getParameter("message"));
			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);
			WebUtils.redirect(request, response, request.getContextPath()+"/gb");
		} else if("delete".equals(action)) {
			request.setCharacterEncoding("utf-8");
			GuestbookDao dao = new GuestbookDao();
			long no = (long)Integer.parseInt(request.getParameter("no"));
			if(dao.getPassword(no).equals(request.getParameter("password"))){
				dao.delete(no);
				WebUtils.redirect(request, response, request.getContextPath()+"/gb");
			}
			
		}else {
			/* default action */
			/* index */
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();
			
			// 데이터를 request 범위에 저장
			request.setAttribute("list", list);
			
			// forwarding
			WebUtils.forward(request, response, "/WEB-INF/views/index.jsp");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
