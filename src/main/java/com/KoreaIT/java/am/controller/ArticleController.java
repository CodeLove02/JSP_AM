package com.KoreaIT.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.KoreaIT.java.am.dto.Article;
import com.KoreaIT.java.am.service.ArticleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticleController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ArticleService articleService;
	
	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.articleService = new ArticleService(conn);
	}

	public void showList() throws ServletException, IOException {
			
		int page = 1;
		
		if (request.getParameter("page") != null && request.getParameter("page").length() != 0){
		  page = Integer.parseInt(request.getParameter("page"));
		}

		int totalPage = articleService.getListTotalPage();
		
		List<Article> articles = articleService.getArticleRows(page);

		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("articles", articles);

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

}