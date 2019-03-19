/**
 * 
 */
package weChat.tools;

public class CommonTools {

	/**
	 * 空指针检查
	 * @param s
	 * @return
	 */
	public final static boolean isNull(Object s){
		if ( null == s ) {
			return true;
		} else {
			if ( s instanceof String ) {
				if ( "".equals(s) ) {
					return true;
				}
			}
		}
		return false;
	}
	
}
