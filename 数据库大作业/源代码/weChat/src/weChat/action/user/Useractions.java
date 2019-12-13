package weChat.action.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v1.cachedstore.Vacuumable;
import com.mysql.jdbc.CallableStatement;
import com.sun.org.apache.bcel.internal.generic.NEW;

import jdk.nashorn.internal.objects.annotations.Where;
import weChat.tools.BaseDao;
import weChat.tools.InstallDatabase;

public class Useractions implements IUseractions{
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = BaseDao.getConnection();
		InstallDatabase.installUserTable();
		User user = BaseDao.QueryUser(request.getParameter("email"), request.getParameter("password"));
		String temp = "admin@bjtu.edu.cn";
		if(user.getEmail().trim().equals(temp.trim())) {
			request.getSession().setAttribute("id", user.getId());
			request.getSession().setAttribute("email", user.getEmail());
			request.getSession().setAttribute("select_id", -1);
			System.out.println("管理员登陆成功");
			response.sendRedirect("index.jsp");
		}
		else if(user.getEmail() != null) {
			request.getSession().setAttribute("id", user.getId());
			request.getSession().setAttribute("email", user.getEmail());
			request.getSession().setAttribute("select_id", -1);
			System.out.println("登陆成功");
			response.sendRedirect("home.jsp");
		} else {
			System.out.println("登录失败");
			response.sendRedirect("login.html");
		}
	}
	
	public void signup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = BaseDao.getConnection();
		String email = request.getParameter("email");
		String psw = request.getParameter("password");
		String deflt = "";
		int temp = 0;
		User user = BaseDao.QueryUser(email, psw);
		if(user.getEmail() != null) {
			System.out.println("用户已存在");
			response.sendRedirect("signup.html");
		}
		boolean result = conn.createStatement().execute("insert into user(email, password, nickname, address, sex, age, qrcode) values ('"+email+"', '"+psw+"', '"+deflt+"', '"+deflt+"', "+temp+", "+temp+", '"+deflt+"')");
		//if(result == true) {
			System.out.println("注册成功");
			response.sendRedirect("login.html");
		//} else {
		//	System.out.println("注册失败");
			response.sendRedirect("login.html");
		//}
		conn.close();
		conn = null;
	}
	
	public void update_user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		int id = (int) request.getSession().getAttribute("id");
		String name = request.getParameter("name");
		
		CallableStatement cStatement = (CallableStatement) connection.prepareCall("{call update_user("+id+", '"+name+"')}");
		cStatement.execute();
		response.sendRedirect("index.jsp");
	}
	
	public void delete_user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		int id = (int) request.getSession().getAttribute("id");
		
		CallableStatement cStatement = (CallableStatement) connection.prepareCall("{call delete_user("+id+")}");
		cStatement.execute();
		response.sendRedirect("index.jsp");
	}
	
	public void insert_user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();		
		CallableStatement cStatement = (CallableStatement) connection.prepareCall("{call insert_user()}");
		cStatement.execute();
		response.sendRedirect("index.jsp");
	}
	
	public List<User> select_friends(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		List<User> list = new ArrayList<User>();
		
		int id = (int) request.getSession().getAttribute("id");
		ResultSet resultSet = connection.createStatement().executeQuery("select * from wechat.user where id in (select friendid from wechat.contacts where userid = "+id+")");
		while(resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setEmail(resultSet.getString("email"));
			user.setAddress(resultSet.getString("address"));
			user.setName(resultSet.getString("nickname"));
			user.setSex(resultSet.getInt("sex"));
			user.setAge(resultSet.getInt("age"));
			user.setQrcode(resultSet.getString("qrcode"));
			user.setPhotourl(resultSet.getString("photourl"));
			list.add(user);
		}
		BaseDao.release(resultSet);
		return list;
	}
	
	public List<News> select_news(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		List<News> list = new ArrayList<News>();
		
		int id = (int) request.getSession().getAttribute("id");
		ResultSet resultSet = connection.createStatement().executeQuery("select * from commonnews where userid = "+id+" or contactid = "+id+"");
		while(resultSet.next()){
			News news = new News();
			news.setId(resultSet.getInt("id"));
			news.setUserid(resultSet.getInt("userid"));
			news.setContactid(resultSet.getInt("contactid"));
			news.setContent(resultSet.getString("content"));
			news.setSendtime(resultSet.getString("sendtime"));
			list.add(news);
		}
		BaseDao.release(resultSet);
		Collections.sort(list);
		return list;
	}
	
	public void sendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		int userid = (int) request.getSession().getAttribute("id");
		int friendid = Integer.valueOf(request.getParameter("friend"));
		String message = request.getParameter("message");
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm");//可以方便地修改日期格式
		String sendtime = dateFormat.format(now);
		connection.createStatement().execute("insert into commonnews(userid, contactid, content, sendtime) values("+userid+", "+friendid+", '"+message+"', '"+sendtime+"')");
		connection.close();
		response.sendRedirect("home.jsp");
		return;
	}
	
	public void queryPeople(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = BaseDao.getConnection();
		User user = BaseDao.queryUser(request.getParameter("email"));
		if(user.getEmail() != null) {
			request.getSession().setAttribute("select_id", user.getId());
			request.getSession().setAttribute("select_name", user.getId());
			request.getSession().setAttribute("select_address", user.getId());
			request.getSession().setAttribute("select_sex", user.getId());
			request.getSession().setAttribute("select_age", user.getId());
		}
		else {
			System.out.println("无此用户");
		}
		response.sendRedirect("addFriends.jsp");
	}
	
	public void sendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = BaseDao.getConnection();
		int userID = (int) request.getSession().getAttribute("id");
		int requestID = Integer.valueOf(request.getParameter("id"));
		boolean result = conn.createStatement().execute("insert into request(userid, requestid) values("+requestID+", "+userID+")");
		//if(result == true) {
			System.out.println("请求发送成功");
		//} else {
		//	System.out.println("请求发送失败");
		//}
		response.sendRedirect("addFriends.jsp");
	}
	
	public List<User> selectRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = BaseDao.getConnection();
		List<User> list = new ArrayList<User>();
		
		int temp = 0; 
		int id = (int) request.getSession().getAttribute("id");
		ResultSet resultSet = connection.createStatement().executeQuery("select * from user where id in (select requestid from request where userid = "+id+" and agree = "+temp+")");
		while(resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setEmail(resultSet.getString("email"));
			user.setAddress(resultSet.getString("address"));
			user.setName(resultSet.getString("nickname"));
			user.setSex(resultSet.getInt("sex"));
			user.setAge(resultSet.getInt("age"));
			user.setQrcode(resultSet.getString("qrcode"));
			user.setPhotourl(resultSet.getString("photourl"));
			list.add(user);
		}
		BaseDao.release(resultSet);
		return list;
	}
	
	public void agreeRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = BaseDao.getConnection();
		int temp1 = 0;
		int temp2 = 1;
		int userID = (int) request.getSession().getAttribute("id");
		int requestID = Integer.valueOf(request.getParameter("id"));
		boolean result1 = conn.createStatement().execute("update request set agree = "+temp2+" where userid = "+userID+" and requestid = "+requestID+" and agree = "+temp1+"");
		boolean result2 = conn.createStatement().execute("insert into contacts(userid, friendid) values("+userID+", "+requestID+")");
		//if(result1 == true && result2 == true) {
			System.out.println("已成为好友");
			response.sendRedirect("home.jsp");
		//} else {
		//	System.out.println("请求好友失败");
		//	response.sendRedirect("addFriends.jsp");
		//}
	}

}
