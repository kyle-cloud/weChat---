/**
 * 
 */
package weChat.action;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 
 * @version 1.0 - 2017-12-17
 */
public class Action {

	private String name;//全局唯一的名称，用于Controller注册
	private String interfaceName;//接口名称
	private String className;//实现类的名称
	private Map<String, String> param2method;//传参与方法名的映射关系
	private String role;//角色，用于权限校验
	
	/**
	 * 构造方法
	 */
	public Action(){
		param2method = new HashMap<String,String>();
	}
	
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
		return interfaceName;
	}
	/**
	 * @param interfaceName the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the param2method
	 */
	public Map<String, String> getParam2method() {
		return param2method;
	}
	/**
	 * @param param2method the param2method to set
	 */
	public void setParam2method(Map<String, String> param2method) {
		this.param2method = param2method;
	}
	
	
}
