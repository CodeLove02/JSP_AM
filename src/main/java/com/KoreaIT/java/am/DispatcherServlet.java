package com.KoreaIT.java.am;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.controller.ArticleController;
import com.KoreaIT.java.am.exception.SQLErrorException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String requestUri = request.getRequestURI();
		String[] requestUriBits = requestUri.split("/");
		
		if(requestUriBits.length < 5) {
			response.getWriter().append("올바른 요청이 아닙니다.");
			return;
		}

		Connection conn = null;

		try {
			Class.forName(Config.getDBDriverClassName());
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());

			HttpSession session = request.getSession();
			
			boolean isLogined = false;
			int loginedMemberId = -1;
			String loginedMemberName = (String) session.getAttribute("loginedMemberName");
			
			if(session.getAttribute("loginedMemberLoginId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				isLogined = true;
			}
			
			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("loginedMemberName", loginedMemberName);
			
			String controllerName = requestUriBits[3];
			String actionMethodName = requestUriBits[4];
			
			if(controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(request, response, conn);
				
				if(actionMethodName.equals("list")) {
					articleController.showList();
				}
			}
			
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} catch (SQLErrorException e) {
			e.getOrigin().printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}