package com.KoreaIT.java.am.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	public int id;
	public LocalDateTime regDate;
	public int memberId;
	public String title;
	public String body;
	public String writerName;

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.writerName = (String) articleMap.get("writerName");
	}

}