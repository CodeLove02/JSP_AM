<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>)request.getAttribute("articleRows");
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
	
	<div>
		<a href="write">글쓰기</a>
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
	
	<% for(Map<String, Object> articleRow : articleRows){ %>
		<tr>
			<td><%= (int)articleRow.get("id") %></td>
			<td><%= (LocalDateTime)articleRow.get("regDate") %></td>
			<td><a href="detail?id=<%= (int)articleRow.get("id") %>"><%= (String)articleRow.get("title") %></a></td>
			<td><%= (String)articleRow.get("writerName") %></td>
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















