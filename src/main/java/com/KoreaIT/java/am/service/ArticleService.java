package com.KoreaIT.java.am.service;

import java.sql.Connection;
import java.util.List;

import com.KoreaIT.java.am.dao.ArticleDao;
import com.KoreaIT.java.am.dto.Article;

public class ArticleService {

	private int itemsInAPage;
	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		this.itemsInAPage = 10;
		this.articleDao = new ArticleDao(conn);
	}

	public int getListTotalPage() {

		int totalCount = articleDao.getListTotalCount();
		int totalPage = (int)Math.ceil((double)totalCount / itemsInAPage);
		
		return totalPage;
	}

	public List<Article> getArticleRows(int page) {
		
		int limitFrom = (page - 1) * itemsInAPage;
		
		return articleDao.getArticles(limitFrom, itemsInAPage);
	}
	
}