<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<%
	boolean isLogined = (boolean) request.getAttribute("isLogined");
	int loginedMemberId = (int) request.getAttribute("loginedMemberId");
	String loginedMemberName = (String) request.getAttribute("loginedMemberName");
	%>
	<%
	if(isLogined) {
	%>
		<div>
			<div><%= loginedMemberName %> 님 환영합니다~</div>
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
</div>