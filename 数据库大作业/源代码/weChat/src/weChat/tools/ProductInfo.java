/**
 * 
 */
package weChat.tools;

/**
 * @author jack
 *
 */
public class ProductInfo {

	/**
	 * 获取产品名称
	 * @return
	 */
	public static final String getProductName(){
		return "HomeDisk";
	}
	
	/**
	 * 获取网站名称和链接
	 * @return
	 */
	public static final String getWebSite(){
		return "<a href=\"http://www.tinyhomeutil.com\">tinyhomeutil.com</a>";
	}
	
	/**
	 * 获取提交问题的网址
	 * Giving us advise
	 * @return
	 */
	public static final String getAdviseUrl(String name){
		return "<a href=\"http://www.tinyhomeutil.com/advise.jsp\">"+name+"</a>";
	}
	
	/**
	 * 获取提交问题的网址
	 * @return
	 */
	public static final String getLasteVersionInfoUrl(String name){
		return "<a href=\"http://www.tinyhomeutil.com/version.jsp\">"+name+"</a>";
	}
	
}
