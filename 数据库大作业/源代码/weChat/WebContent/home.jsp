<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="weChat.tools.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="weChat.action.user.*" %>
<%List<User> userlist = new Useractions().select_friends(request, response); %>
<%List<News> newslist = new Useractions().select_news(request, response); %>

<!DOCTYPE html>
<html lang="en" >

<head>
<meta charset="UTF-8">
<title>Direct Messaging</title>

<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="CSS/css/reset.min.css">

<link rel="stylesheet" href="CSS/css/style.css">


</head>

<body>
<h1><a href="login.html" class="btn btn-default">logout</a></h1>
<h1><a href="addFriends.jsp" class="btn btn-default">addfri</a></h1>
  <div class="wrapper">
    <div class="container">
        <div class="left">
            <div class="top">
                <input type="text" placeholder="Search" />
                <a href="javascript:;" class="search"></a>
            </div>
            <ul class="people">
            	<%for(int i=0; i<userlist.size(); i++){
            		User user = userlist.get(i);%>
            		<li class="person" data-chat="person<%=i+1%>">
                    <img src="CSS/img/thomas.jpg" alt="" />
                    <span class="name"><%=user.getName() %></span>
                    <span class="time"><%=user.getAge() %>岁</span>
                    <span class="preview"><%=user.getAddress() %></span>
                </li>
            	<% }%>	
<!--         	
                <li class="person" data-chat="person1">
                    <img src="CSS/img/thomas.jpg" alt="" />
                    <span class="name">Thomas Bangalter</span>
                    <span class="time">2:09 PM</span>
                    <span class="preview">I was wondering...</span>
                </li>
-->
            </ul>
        </div>
        <div class="right">
            <div class="top"><span>To: <span class="name" id="friend_name">mial</span></span></div>
            
          	<%for(int i=0; i<userlist.size(); i++){
          		User user = userlist.get(i);%>
          		<div class="chat" data-chat="person<%=i+1%>">
          		
          		<%for(int j=0; j<newslist.size(); j++){ 
          			News news = newslist.get(j);
          			if(news.getContactid() == user.getId()){%>
          				<div class="conversation-start">
                    		<span><%=news.getSendtime() %></span>
               			</div>
          				 <div class="bubble me">
          				 	<%=news.getContent() %>
          				 </div>
          			<%} else if (news.getUserid() == user.getId()){%>
          				<div class="conversation-start">
                    		<span><%=news.getSendtime() %></span>
               			</div>
          				<div class="bubble you">
          				 	<%=news.getContent() %>
          				 </div>
          			<%}%>
          		<%}%>
          		<div class="write">
                	<a href="javascript:;" class="write-link attach"></a>
                	<form id="user_action" action="./act" method="post">
                		<input type="hidden" name="a" value="u02" />
                		<input type="hidden" name="friend" value="<%=user.getId()%>">
                		<input type="text" name="message"/>
                	</form>
                	<a href="javascript:;" class="write-link smiley"></a>
                	<a href="javascript:;" class="write-link send"></a>
           	 	</div>
          		</div>
          		
          	<%} %>
<!--
            <div class="chat" data-chat="person1">
                <div class="conversation-start">
                    <span>Today, 6:48 AM</span>
                </div>
                <div class="bubble you">
                    Hello,
                </div>
                <div class="bubble you">
                    it's me.
                </div>
                <div class="bubble you">
                    I was wondering...
                </div>
            </div>
            -->
        </div>
    </div>
</div>

<script  src="CSS/js/index.js">
</script>


</body>

</html>
