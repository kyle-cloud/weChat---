/**
 * 
 */
package weChat.action;

import weChat.tools.CommonTools;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.omg.CORBA.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author 
 * @version 1.0 - 2017-12-17
 */
public class ControllerXmlParser {

	private Document doc = null;
	public static final String PLATFORM = "platform";
	public static final String CACHE = "cache";
	public static final String CACHE_ACTION = "action";
	public static final String CACHE_ACTION_NAME = "name";
	public static final String CACHE_ACTION_ROLE = "role";
	public static final String CACHE_ACTION_ENABLE = "enable";
	public static final String CACHE_ACTION_INTEGERFACE = "interface";
	public static final String CACHE_ACTION_CLASS = "class";
	public static final String CACHE_ACTION_METHOD = "method";
	public static final String CACHE_ACTION_METHOD_NAME = "name";
	public static final String CACHE_ACTION_METHOD_PARAMETER = "parameter";
	public static final String CACHE_ACTION_PAGE = "page";
	public static final String CACHE_ACTION_PAGE_ENTRANCE = "entrance";
	public static final String CACHE_ACTION_PAGE_URL = "url";
	public static final String CACHE_PUBLIC_PAGE = "public_page";
	public static final String CACHE_PUBLIC_PAGE_URL = "url";
	public static final String CACHE_PUBLIC_PAGE_NAME = "public_page";
	public static final String CACHE_PUBLIC_PAGE_ENABLE = "enable";
	
	public static final String CACHE_ROLE_ADMIN = "admin";
	public static final String CACHE_ROLE_USER = "user";
	public static final String CACHE_ROLE_PUBLIC = "public";
	
	/**
	 * Action列表
	 */
	public List<Action> actions;
	
	/**
	 * Action哈希表
	 */
	public Map<String, Action> param2action;
	
	/**
	 * 角色与页面的映射关系
	 */
	public Map<String, List<String>> role2pages;
	
	/**
	 * 构造方法
	 * @throws Exception
	 */
	public ControllerXmlParser(){
		try {
			doc = this.loadXmlFile("actions.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		actions = new ArrayList<Action>();
		param2action = new HashMap<String, Action>();
		role2pages = new HashMap<String, List<String>>();
		try {
			this.init();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件到InputStream中
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream(String resource) throws Exception{
		InputStream is = ControllerXmlParser.class.getClassLoader().getResourceAsStream(resource);
		if ( is != null ) return is;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String stripped = resource.startsWith("/") ? resource.substring(1): resource;
		if (classLoader != null) {
			is = classLoader.getResourceAsStream(stripped);
		}
		if (is == null) {
			is = Environment.class.getResourceAsStream(resource);
		}
		if (is == null) {
			is = Environment.class.getClassLoader().getResourceAsStream(stripped);
		}
		if (is == null) {
			throw new Exception(resource + " not found");
		}
		return is;
	}
	
	/**
	 * 加载配置文件
	 * @param filePath String
	 * @return document Document
	 * @throws Exception 
	 * */
	public final Document loadXmlFile(String resource) throws Exception{
		InputStream is = getInputStream(resource);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		doc = db.parse(is);
		return doc;
	}
	
	/**
	 * 从指定名称的父节点取得指定名称的子节点
	 * @param doc
	 * @param parentNodeName
	 * @param childNodeName
	 * @return List<Node>
	 */
	public final List<Node> getChildNodes(Document doc,String parentNodeName,String childNodeName){
		if ( null != doc ) {
			List<Node> osm = this.getChildNodes(doc, parentNodeName);
			if ( null != osm && !osm.isEmpty() ) {
				List<Node> osmNodes = new ArrayList<Node>();
				List<Node> l = null;
				for ( Node n : osm ) {
					l = this.getNodes(n, childNodeName);
					if ( l != null && !l.isEmpty() ) {
						osmNodes.addAll(l);
					}
				}
				return osmNodes;
			}
		}
		return null;
	}
	
	/**
	 * 获得指定Node的子Node集合
	 * @param node
	 * @param childNodeName
	 * @return 
	 * */
	public final List<Node> getChildNodes(Node node,String childNodeName) {
		if ( null == node )
			return null;
		if ( null == childNodeName || "".equals(childNodeName) )
			return null;
		NodeList nl = node.getChildNodes();
		List<Node> r = new ArrayList<Node>();
		List<Node> cr = null;
		Node thisNode = null;
		for ( int i = 0 ; i < nl.getLength() ; i++  ) {
			thisNode = nl.item(i);
			//System.out.println(nodeName.toLowerCase() + " : " + thisNode.getNodeName().toLowerCase());
			if ( childNodeName.toLowerCase().equals(thisNode.getNodeName().toLowerCase()) ) {
					r.add(nl.item(i));
			} else {
				if ( nl.item(i).hasChildNodes() ) {
					cr = this.getChildNodes(thisNode, childNodeName);
					if ( cr != null && !cr.isEmpty() ) {
						r.addAll(cr);
					}
				}
			}
		}
		return r;
	}
	
	/**
	 * 获得指定Node的子Node集合
	 * @param node 
	 * @param nodeName 
	 * @return 
	 * */
	public final List<Node> getNodes(Node node,String nodeName) {
		if ( null == node )
			return null;
		if ( null == nodeName || "".equals(nodeName) )
			return null;
		NodeList nl = node.getChildNodes();
		List<Node> r = new ArrayList<Node>();
		for ( int i = 0 ; i < nl.getLength() ; i++  ) {
			//System.out.println(nodeName.toLowerCase() + " : " + nl.item(i).getNodeName().toLowerCase());
			if ( nodeName.toLowerCase().equals(nl.item(i).getNodeName().toLowerCase()) ) {
					r.add(nl.item(i));
			}
		}
		return r;
	}	
	
	/**
	 * Get attribute value of node.
	 * @param node 
	 * @param attrName 
	 * @return String
	 * */
	public final String getNodeAttribute(Node node,String attrName) {
		if ( null == node )
			return null;
		if ( node != null ) {
			NamedNodeMap attributesMap = node.getAttributes();
			if ( attributesMap != null ) {
				Node objectProperty = attributesMap.getNamedItem(attrName);
				if ( objectProperty != null ) {
					return objectProperty.getTextContent();
				}
			}
		}
		return null;
	}	
	
	/**
	 * 初始化缓存
	 * @throws ClassNotFoundException
	 */
	public void init() throws ClassNotFoundException{
		if ( doc != null ) {
			//读取配置清单并取出各XML文件的路径
			List<Node> cacheNodes = this.getChildNodes(doc, CACHE);
			if ( cacheNodes != null && !cacheNodes.isEmpty() ) {
				Node cacheNode = cacheNodes.get(0);//只取第一个节点
				List<Node> actionNodes = this.getChildNodes(cacheNode, CACHE_ACTION);
				if ( actionNodes != null && !actionNodes.isEmpty() ) {
					for ( Node actionNode : actionNodes ) {
						this.parseAction(actionNode);
					}
				}
				this.initPublicPage(cacheNode);
			}
		}
	}

	
	
	/**
	 * 解析并初始化调用Action的缓存和权限管理专用页面缓存
	 * @param actionNode
	 * @throws ClassNotFoundException
	 */
	protected void parseAction(Node actionNode) throws ClassNotFoundException{
		if ( actionNode == null ) {
			return;
		}
		String enableNodeAttrbute = this.getNodeAttribute(actionNode, CACHE_ACTION_ENABLE);
		boolean enable = enableNodeAttrbute==null?false:"true".equals(enableNodeAttrbute)?true:false;
		if ( enable ) {
			Action action = new Action();
			action.setName(this.getNodeAttribute(actionNode, CACHE_ACTION_NAME));
			action.setRole(this.getNodeAttribute(actionNode, CACHE_ACTION_ROLE));
			if ( !CommonTools.isNull(action.getName()) ) {
				action.setInterfaceName(this.getNodeTextContent(actionNode, CACHE_ACTION_INTEGERFACE));
				action.setClassName(this.getNodeTextContent(actionNode, CACHE_ACTION_CLASS));
				if ( !CommonTools.isNull(action.getInterfaceName()) && !CommonTools.isNull(action.getClassName()) ) {
					List<Node> methodNodes = this.getChildNodes(actionNode, CACHE_ACTION_METHOD);
					if ( methodNodes != null && !methodNodes.isEmpty() ) {
						String methodName = null;
						String methodParameter = null;
						for ( Node method : methodNodes ) {
							methodName = this.getNodeAttribute(method, CACHE_ACTION_METHOD_NAME);
							methodParameter = this.getNodeAttribute(method, CACHE_ACTION_METHOD_PARAMETER);
							if ( CommonTools.isNull(methodName) || 
									CommonTools.isNull(methodParameter) ) {
								continue;
							}
							action.getParam2method().put(methodParameter, methodName);
							param2action.put(methodParameter, action);
						}
						actions.add(action);
						
					}
					List<Node> pageNodes = this.getChildNodes(actionNode, CACHE_ACTION_PAGE);
					if ( methodNodes != null && !methodNodes.isEmpty() ) {
						List<String> l = role2pages.get(action.getRole());
						if ( l == null ) {
							role2pages.put(action.getRole(), new ArrayList<String>());
							l = role2pages.get(action.getRole());
						}
						String url = null;
						for ( Node n : pageNodes ) {
							url = this.getNodeAttribute(n, CACHE_PUBLIC_PAGE_URL);
							l.add(url);
						}
					}
					
				}
			}
		}
	}

	/**
	 * 初始化公用后台页面的缓存
	 * @param cacheNode
	 */
	protected void initPublicPage(Node cacheNode){
		List<Node> publicPageNodes = this.getChildNodes(cacheNode, CACHE_PUBLIC_PAGE);
		if ( publicPageNodes != null ) {
			List<String> l = role2pages.get(CACHE_ROLE_PUBLIC);
			if ( l == null ) {
				role2pages.put(CACHE_ROLE_PUBLIC, new ArrayList<String>());
				l = role2pages.get(CACHE_ROLE_PUBLIC);
			}
			String url = null;
			for ( Node n : publicPageNodes ) {
				url = this.getNodeAttribute(n, CACHE_PUBLIC_PAGE_URL);
				l.add(url);
			}
		}
	}

	/**
	 * 返回Node节点的Content
	 * @param parentNode
	 * @param childNodeName
	 * @return
	 */
	private String getNodeTextContent(Node parentNode, String childNodeName){
		if ( parentNode != null && childNodeName != null && !"".equals(childNodeName) ) {
			List<Node> childNodes = this.getChildNodes(parentNode, childNodeName);
			if ( childNodes == null || childNodes.isEmpty() ) {
				return "";
			}
			Node childNode = childNodes.get(0);//只取第一个
			String childNodeValue = childNode.getTextContent();
			return childNodeValue==null?"":childNodeValue;
		}
		return "";
	}


}
