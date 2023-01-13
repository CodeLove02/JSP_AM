package com.KoreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.exception.SQLErrorException;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		Connection conn = null;

		try {
			Class.forName(Config.getDBDriverClassName());
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());

			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			
			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);
			
			Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);
			
			if(memberRow.isEmpty()) {
				response.getWriter().append(String.format("<script>alert('%s 는 존재하지 않는 아이디입니다'); location.replace('login');</script>", loginId));
				return;
			}
			
			if(memberRow.get("loginPw").equals(loginPw) == false) {
				response.getWriter().append(String.format("<script>alert('비밀번호가 일치하지 않습니다'); location.replace('login');</script>"));
				return;
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("loginedMemberLoginId", memberRow.get("loginId"));
			session.setAttribute("loginedMemberId", memberRow.get("id"));
			session.setAttribute("loginedMemberName", memberRow.get("name"));
			
			response.getWriter().append(String.format("<script>alert('%s 님 환영합니다!'); location.replace('../home/main');</script>", memberRow.get("name")));

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