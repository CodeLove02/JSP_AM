package com.KoreaIT.java.am;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.exception.SQLErrorException;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

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
			
			int page = 1;
			if (request.getParameter("page") != null && request.getParameter("page").length() != 0){
			  page = Integer.parseInt(request.getParameter("page"));
			}

			int itemsInAPage = 10;

			int limitFrom = (page - 1) * itemsInAPage;
			
			SecSql sql = SecSql.from("SELECT COUNT(id)");
			sql.append("FROM article");
			
			int totalCount = DBUtil.selectRowIntValue(conn, sql);
			int totalPage = (int)Math.ceil((double)totalCount / itemsInAPage);
			
			sql = SecSql.from("SELECT A.*, M.name AS writerName");
			sql.append("FROM article AS A");
			sql.append("INNER JOIN `member` AS M");
			sql.append("ON A.memberId = M.id");
			sql.append("ORDER BY A.id DESC");
			sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

			request.setAttribute("page", page);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("articleRows", articleRows);

			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

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