<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.KoreaIT.java.am.dto.Article" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Article> articles = (List<Article>)request.getAttribute("articles");
int cPage = (int)request.getAttribute("page");
int totalPage = (int)request.getAttribute("totalPage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>

	<h1>게시물 리스트</h1>
	
	<%@ include file="../part/topBar.jsp" %>
	
	<div>
		<a href="write">글쓰기</a>
	</div>
	
	<div>
		<a href="../home/main">메인으로 돌아가기</a>
	</div>
	
	<table border="2" bordercolor="green">
		<colgroup>
			<col width="50"/>
			<col width="200"/>
		</colgroup>
		<tr>
			<th>번호</th>
			<th>날짜</th>
			<th>제목</th>
			<th>작성자</th>
		</tr>
	
	<% for(Article article : articles){ %>
		<tr>
			<td><%= (int)article.id %></td>
			<td><%= (LocalDateTime)article.regDate %></td>
			<td><a href="detail?id=<%= (int)article.id %>"><%= (String)article.title %></a></td>
			<td><%= (String)article.writerName %></td>
		</tr>
	<% } %>
	</table>
	
	<style type="text/css">
		.page > a.red {
			color:red;
		}
	</style>
	
	<div class="page">
		<%
		if(cPage > 1){
		%>	
			<a href="list?page=1">◀◀</a>
		<%
		}
		%>
	
		<%
		int pageSize = 5;
		int from = cPage - pageSize;
		if(from < 1) {
			from = 1;
		}
		int end = cPage + pageSize;
		if(end > totalPage) {
			end = totalPage;
		}
		for(int i = from; i <= end; i++){ 
		%>
			<a class="<%= cPage == i ? "red" : "" %>" href="list?page=<%= i %>"><%= i %></a>
		<%
		} 
		%>
		<%
		if(cPage < totalPage){
		%>	
			<a href="list?page=<%= totalPage %>">▶▶</a>
		<%
		}
		%>
	</div>
	
</body>
</html>















