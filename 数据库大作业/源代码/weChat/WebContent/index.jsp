<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="weChat.tools.*" %>
<%@ page language="java" import="weChat.action.user.*" %>
<%@page language="java" import="java.util.List" %>
<% List<User> list = new BaseDao().select_user();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" >  

</head>
<body>
	<h3>用户信息</h3>
	<table class="table table-striped">
		<tr>
			<td>ID</td>
			<td>NickName</td>
			<td>Address</td>
			<td>Sex</td>
			<td>Age</td>
		</tr>
		<%for (int i=0; i<list.size(); i++) {
			User user = list.get(i);%>
		<tr>
		<td><%=user.getId() %></td>
		<td><%=user.getName() %></td>
		<td><%=user.getAddress() %></td>
		<td><%=user.getSex() %></td>
		<td><%=user.getAge() %></td>
		</tr>
		<%} %>
	</table>
	<form class="form-inline" id="test_action" action="./act" method="post">
		<h3>更改昵称</h3>
		<input type="hidden" name="a" value="t01" /> 
		<input class="form-control" name="id" type="id" placeholder="请输入用户id"> 
		<input class="form-control" name="name" type="name" placeholder="请输入昵称">
		<input class="btn btn-default" type="submit" value="更改">
	</form>
	<form class="form-inline" id="test_action" action="./act" method="post">
		<h3>删除用户</h3>
		<input type="hidden" name="a" value="t02" /> 
		<input class="form-control" name="id" type="id" placeholder="请输入用户id">
		<input class="btn btn-default" type="submit" value="删除">
	</form>
	<h1><a href="login.html" class="btn btn-default">logout</a></h1>
				
</body>
</html>