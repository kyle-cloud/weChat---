/**
 * 
 */
package weChat.action;

import weChat.tools.CommonTools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0 - 2017-12-17
 */
@WebServlet(name = "act", urlPatterns = { "/act" })
public class Controller extends HttpServlet {

	private static final long serialVersionUID = -91811475866134042L;
	public static ControllerXmlParser PARSER = new ControllerXmlParser();

	/**
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws UnsupportedEncodingException 
	 */
	public static void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 利用参数‘a’的值，从缓存中取得对应的实现类和方法名，
		// 再通过反射调用用户定义的Control方法。
		String param = (String)request.getAttribute("a");
		Action action = PARSER.param2action.get(param);
		if ( action != null) {
			Map<String, String> map = action.getParam2method();
			String methodName = map.get(param);
			if ( !CommonTools.isNull(methodName) ) {
				try {
					Class<?> clazz = Class.forName(action.getClassName());
					Method m = clazz.getMethod(methodName, new Class[] {HttpServletRequest.class, HttpServletResponse.class });
					//TODO 如果打算使用单例模式，可以在此处修改
					m.invoke(clazz.newInstance(), new Object[] {request, response});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws UnsupportedEncodingException 
	 */
	public void doAction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 利用参数‘a’的值，从缓存中取得对应的实现类和方法名，
		// 再通过反射调用用户定义的Control方法。
		String param = request.getParameter("a");
		Action action = PARSER.param2action.get(param);
		if ( action != null) {
			Map<String, String> map = action.getParam2method();
			String methodName = map.get(param);
			if ( !CommonTools.isNull(methodName) ) {
				try {
					Class<?> clazz = Class.forName(action.getClassName());
					Method m = clazz.getMethod(methodName, new Class[] {HttpServletRequest.class, HttpServletResponse.class });
					//TODO 如果打算使用单例模式，可以在此处修改
					m.invoke(clazz.newInstance(), new Object[] {request, response});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		this.doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		this.doAction(request, response);
		
	}
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//System.out.println("init");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		//System.out.println("destroy");
	}

}
