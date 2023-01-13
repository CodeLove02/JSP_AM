<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>)request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
</head>
<body>
	<h1><%= (int)articleRow.get("id") %>번 게시물</h1>
	
	<%@ include file="../part/topBar.jsp" %>
	
	<div>번호 : <%= (int)articleRow.get("id") %></div>
	<div>날짜 : <%= (LocalDateTime)articleRow.get("regDate") %></div>
	<div>작성자 : <%= (String)articleRow.get("writerName") %></div>
	<div>제목 : <%= (String)articleRow.get("title") %></div>
	<div>내용 : <%= (String)articleRow.get("body") %></div>
	<div>
		<a href="list">목록</a>
		<a href="modify?id=<%= (int)articleRow.get("id") %>">수정</a>
		<a href="doDelete?id=<%= (int)articleRow.get("id") %>" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;">삭제</a>
	</div>
</body>
</html>