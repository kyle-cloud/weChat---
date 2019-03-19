<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="weChat.tools.*" %>
<%@ page language="java" import="weChat.action.user.*" %>
<%@page language="java" import="java.util.List" %>
<% int id = -1; id = (int) request.getSession().getAttribute("select_id");%>
<%List<User> list = new Useractions().selectRequest(request, response); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
</head>

<body>
	<br>
	<br>
	<table class="table table-striped">
			<tr>
				<td>ID</td>
				<td>NickName</td>
				<td>Address</td>
				<td>Sex</td>
				<td>Age</td>
				<td>request</td>
			</tr>
			<%for(int i=0; i<list.size(); i++){
				User user = list.get(i);%>
			<tr>
				<td><%=user.getId() %></td>
				<td><%=user.getName() %></td>
				<td><%=user.getAddress() %></td>
				<td><%=user.getSex() %></td>
				<td><%=user.getAge() %></td>
				<form class="form-inline" id="test_action" action="./act" method="post">
					<input type="hidden" name="a" value="u06" />
					<input type="hidden" name="id" type="id" value="<%=user.getId()%>">
					<td><input class="btn btn-default" type="submit" value="同意"></td>
				</form>
			</tr>
			<%} %>
		</table>

	<form class="form-inline" id="test_action" action="./act" method="post">
		<h3>查找用户</h3>
		<input type="hidden" name="a" value="u04" /> 
		<input class="form-control" name="email" type="email" placeholder="请输入用户邮箱">
		<input class="btn btn-default" type="submit" value="查询">
	</form>
	<%if(id != -1){ %>
		<table class="table table-striped">
			<tr>
				<td>ID</td>
				<td>NickName</td>
				<td>Address</td>
				<td>Sex</td>
				<td>Age</td>
				<td>request</td>
			</tr>
			<tr>
				<td><%=(int) request.getSession().getAttribute("select_id") %></td>
				<td><%=request.getSession().getAttribute("select_name") %></td>
				<td><%=request.getSession().getAttribute("select_address") %></td>
				<td><%=(int) request.getSession().getAttribute("select_sex") %></td>
				<td><%=(int) request.getSession().getAttribute("select_age") %></td>
				<form class="form-inline" id="test_action" action="./act" method="post">
					<input type="hidden" name="a" value="u05" />
					<input type="hidden" name="id" type="id" value="<%=(int) request.getSession().getAttribute("select_id")%>">
					<td><input class="btn btn-default" type="submit" value="发送"></td>
				</form>
			</tr>
		</table>
		<%request.getSession().setAttribute("select_id", -1); %>
	<%} %>
	<h1><a href="login.html" class="btn btn-default">logout</a></h1>
	<h1><a href="home.jsp" class="btn btn-default">home	</a></h1>
</body>
</html>