<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
boolean isLogined = (boolean) request.getAttribute("isLogined");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<h1>메인 페이지</h1>
	<div>
		<%
		if(isLogined) {
		%>
			<div>
				<span><%= loginedMemberId %>번 회원</span>
				<a href="../member/doLogout">로그아웃</a>
			</div>
		<%	
		}
		%>
		
		<%
		if(!isLogined) {
		%>
			<div>
				<a href="../member/login">로그인</a>
			</div>
			<div>
				<a href="../member/join">회원가입</a>
			</div>
		<%	
		}
		%>
		
		<div>
			<a href="../article/list">게시물 리스트</a>
		</div>
	</div>
</body>
</html>