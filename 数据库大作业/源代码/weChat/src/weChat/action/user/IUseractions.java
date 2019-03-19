package weChat.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUseractions {
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void update_user(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void delete_user(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void insert_user(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<User> select_friends(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<News> select_news(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void sendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void signup(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void queryPeople(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void sendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<User> selectRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void agreeRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
