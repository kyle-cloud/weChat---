package weChat.tools;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class Toolkit {

	/**
	 * md5
	 * @param String message
	 * @return String
	 */
	public final static String md5(String message){ 
		MessageDigest md; 
	try { 
		md = MessageDigest.getInstance("md5"); 
	} catch (java.security.NoSuchAlgorithmException e) { 
		throw new RuntimeException(e.getMessage()); 
	} 
		md.update(message.getBytes()); 
		return new BigInteger(md.digest()).toString(); 
	} 
	
	/**
	 * SHA
	 * @param String message
	 * @return String
	 */
	public static final String sha(String message){ 
		MessageDigest md; 
	try { 
		md = MessageDigest.getInstance("SHA"); 
	} catch (java.security.NoSuchAlgorithmException e) { 
		throw new RuntimeException(e.getMessage()); 
	} 
		md.update(message.getBytes()); 
		return new BigInteger(md.digest()).toString(); 
	} 
	
	/**
	 * 将特殊的HTML标记进行转义
	 * 包括：
	 * >,<,&,",空格,版权符,注册符
	 * @param str
	 * @return
	 */
	public static String htmlTransfer(String str){
		if ( str != null ) {
			String result =	str.replaceAll("&", "&amp;");
			result = str.replaceAll("<", "&lt;");
			result = str.replaceAll(">", "&gt;");
			result = str.replaceAll("\"", "&quot;");
			result = str.replaceAll(" ", "&nbsp;");
			result = str.replaceAll("©", "&copy;");
			result = str.replaceAll("®", "&reg;");
			result = str.replaceAll("'", "&apos;");
			result = str.replaceAll(" ", "&nbsp;");
			return result;
		}
		return "";
	}
	
	/**
	 * 将特殊的HTML标记进行转义
	 * 包括：
	 * >,<,&,",空格,版权符,注册符
	 * @param str
	 * @return
	 */
	public static String htmlTransfer2(String str){
		if ( str != null ) {
			String result =	str.replaceAll("&", "&amp;");
			result = str.replaceAll("<", "&lt;");
			result = str.replaceAll(">", "&gt;");
			result = str.replaceAll("\"", "&quot;");
			result = str.replaceAll(" ", "&nbsp;");
			result = str.replaceAll("©", "&copy;");
			result = str.replaceAll("®", "&reg;");
			result = str.replaceAll("'", "&apos;");
			return result;
		}
		return "";
	}
	
	/**
	 * 返回成功状态的链接
	 * @param link
	 * @param notice
	 * @return
	 */
	public static String successLink(String link,String notice) {
		StringBuilder resultLink = new StringBuilder();
		resultLink.append(link)
		  .append("?")
	      .append(Constant.PARAM_RETURN).append("=").append(Constant.PARAM_RETURN_SUCCESS)
	      .append("&")
	      .append(Constant.PARAM_RETURN_SUCCESS).append("=").append(notice);
		return resultLink.toString();
	}
	
	/**
	 * 返回失败状态的链接
	 * @param link
	 * @param notice
	 * @return
	 */
	public static String failLink(String link,String notice) {
		StringBuilder resultLink = new StringBuilder();
		resultLink.append(link)
		  .append("?")
	      .append(Constant.PARAM_RETURN).append("=").append(Constant.PARAM_RETURN_FAIL)
	      .append("&")
	      .append(Constant.PARAM_RETURN_FAIL).append("=").append(notice);
		return resultLink.toString();
	}
	
	/**
	 * 空指针检查
	 * @param input
	 * @param link
	 * @param notice
	 * @return
	 */
	public static String checkNull(String input,String link,String notice) {
		if ( input == null || "".equals(input) ) {
			return failLink(link, notice);
		}
		return null;
	}
	
	/**
	 * 空指针检查
	 * @param input
	 * @param link
	 * @param notice
	 * @return
	 * @throws Exception 
	 */
	public static boolean isNull(String input,String link,String notice,
			HttpServletResponse response) 
			throws Exception {
		String result = checkNull(input, link, notice);
		if ( result != null ) {
			response.sendRedirect(result);
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是常用的图片格式
	 * @param fileName
	 * @return
	 */
	public static boolean isImageFile(String fileName){
		if ( fileName != null && !"".equals(fileName) ) {
			if ( fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") 
					|| fileName.endsWith(".bmp") || fileName.endsWith(".gif") 
					|| fileName.endsWith(".png") ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		if ( fileName != null && !"".equals(fileName) ) {
			int point = fileName.lastIndexOf(".");
			if ( point > -1 ) {
				return fileName.substring(point+1, fileName.length());
			}
		}
		return null;
	}
		
	/**
	 * 获取App根路径
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getAppRootPath(HttpServletRequest request) {
		//System.out.println(new File(request.getRealPath(request.getRequestURI())).getParent());
		String path = new File(request.getRealPath(request.getRequestURI())).getParent();
		//String path = new File(request.getServletContext().getRealPath(request.getRequestURI())).getParent();
		//System.out.println(path);
		return new File(path).getParent();
		
	}
	
	/**
	 * 获取文件名
	 * @param fileType
	 * @return
	 */
	public static String getSimpleFileName(String fileType){
		long time = System.currentTimeMillis();
		StringBuilder simpleFileName = new StringBuilder();
		simpleFileName.append(time).append(".").append(fileType);
		return simpleFileName.toString();
	}
	
	/**
	 * 获取专辑图片所在目录的相对路径
	 * @param appPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getRelativeAlbumImageFolderPath(String userName,String parent) {
		StringBuilder relativePath = new StringBuilder();
		relativePath.append("/upload")
                    .append("/").append(userName)
                    .append("/").append(parent)
                    .append("/album/");
		return relativePath.toString();
	}
	
	/**
	 * 获取专辑图片的相对路径
	 * @param webRootPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getRelativeAlbumImagePath(String webRootPath,String userName,String parent,String simpleFileName) {
		StringBuilder path = new StringBuilder();
		path.append(webRootPath)
		    .append(getRelativeAlbumImageFolderPath(userName,parent))
		    .append("cut_")
		    .append(simpleFileName);
		return path.toString();
	}
	
	/**
	 * 获取WebApp的根路径
	 * @param request
	 * @return
	 */
	public static String getWebRootPath(HttpServletRequest request){
		return request.getRequestURL().toString().replace(request.getServletPath(), "");
	}
	
	/**
	 * 获取专辑图片的绝对路径todo
	 * @param appPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getAbsoluteAlbumImagePath(String appPath,String userName,String parent,String simpleFileName) {
		StringBuilder path = new StringBuilder();
		path.append(appPath)
		    .append(getRelativeAlbumImageFolderPath(userName,parent));
		File folder = new File(path.toString());
		folder.mkdirs();
		path.append(simpleFileName);
		return path.toString();
	}
	
	/**
	 * 得到数据库备份的文件夹的绝对路径todo
	 * @param request
	 * @return
	 */
	public static String getBackupFolderAbsolutePath(HttpServletRequest request){
		String appPath = Toolkit.getAppRootPath(request);
		StringBuilder path = new StringBuilder();
		path.append(appPath).append("\\WEB-INF\\backup\\");
		File folder = new File(path.toString());
		if ( !folder.exists() ) {
			folder.mkdirs();
		}
		//System.out.println("path"+path);
		return path.toString();
	}
	
	/**
	 * 从传过来的参数中读取当前页
	 * @param request
	 * @return
	 */
	public static int getCurrentPage(HttpServletRequest request) {
		String pageStr = request.getParameter(Constant.PARAM_PAGE);
		int currentPage;
		if ( pageStr == null || "".equals(pageStr) ) {
			currentPage = 1;
		} else {
			currentPage = Integer.valueOf(pageStr);
		}
		return currentPage;
	}
	
	/**
	 * 获取资源文件的绝对路径todo
	 * @param appPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getAbsoluteResourcePath(String appPath,String userName,String simpleFileName) {
		StringBuilder path = new StringBuilder();
		path.append(appPath)
		    .append(getRelativeResourceFolderPath(userName));
		File folder = new File(path.toString());
		folder.mkdirs();
		path.append(simpleFileName);
		return path.toString();
	}
	
	/**
	 * 获取资源文件的绝对路径
	 * @param request
	 * @param res
	 * @return
	 */
	public static String getAbsoluteResourcePath(HttpServletRequest request,Resource res) {
		StringBuilder path = new StringBuilder();
		path.append(Toolkit.getAppRootPath(request))
		    .append(res.getFileUrl());
		return path.toString();
	}
	
	/**
	 * 创建资源所在的目录
	 * @param request
	 * @param res
	 * @return
	 */
	public static void createResourceFolder(HttpServletRequest request,String userName) {
		StringBuilder path = new StringBuilder();
		path.append(Toolkit.getAppRootPath(request))
		    .append(getRelativeResourceFolderPath(userName));
		File folder = new File(path.toString());
		folder.mkdirs();
	}
	
	/**
	 * 获取资源文件的相对路径
	 * @param appPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getRelativeResourcePath(String webRootPath,String userName,String simpleFileName) {
		StringBuilder path = new StringBuilder();
		path.append(webRootPath)
		    .append(getRelativeResourceFolderPath(userName))
		    .append(simpleFileName);
		return path.toString();
	}
	
	/**
	 * 获取资源文件所在目录的相对路径
	 * @param appPath
	 * @param userName
	 * @param parent
	 * @param simpleFileName
	 * @return
	 */
	public static String getRelativeResourceFolderPath(String userName) {
		Calendar cal=Calendar.getInstance();//使用日历类
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		
		StringBuilder relativePath = new StringBuilder();
		relativePath.append("/upload")
                    .append("/").append(userName)
                    .append("/").append(year)
                    .append("/").append(month)
                    .append("/").append(day)
                    .append("/");
		return relativePath.toString();
	}
	
	/**
	 * 两个double类型是否相等
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isEqual(double x,double y) {
		if ( Math.abs(x-y) < 0.01d ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将资源列表转换为JSON字符串
	 * @param list
	 * @return
	 */
	public static String resourceList2Json(List<Resource> list){
		return Toolkit.resourceList2Json(list,0);
	}
	
	/**
	 * 将资源列表转换为JSON字符串
	 * @param list
	 * @return
	 */
	public static String resourceList2Json(List<Resource> list,int start){
		if ( list == null || "".equals(list) ) {
			return "[]";
		}
		if ( start >= list.size() ) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Resource r = null;
		for ( int i = start ; i < list.size() ; i++ ) {
			r = list.get(i);
			sb.append("{")
			  .append("\"").append("id").append("\" : \"").append(r.getId()).append("\"").append(",")
			  .append("\"").append("name").append("\" : \"").append(r.getName()).append("\"").append(",")
			  .append("\"").append("remark").append("\" : \"").append(r.getRemark()).append("\"").append(",")
			  .append("\"").append("albumid").append("\" : \"").append(r.getAlbumId()).append("\"").append(",")
			  .append("\"").append("albumname").append("\" : \"").append(r.getAlbumName()).append("\"").append(",")
			  .append("\"").append("albumparent").append("\" : \"").append(r.getAlbumParent()).append("\"").append(",")
			  .append("\"").append("weburl").append("\" : \"").append(r.getWebUrl()).append("\"").append(",")
			  .append("\"").append("createtime").append("\" : \"").append(r.getCreateTime()).append("\"")			  
			  .append("}");
			if ( i < list.size() -1 ) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 将资源列表转换为JSON字符串
	 * @param list
	 * @return
	 */
	public static String imageResourceList2Json(List<Resource> list,int start){
		if ( list == null || "".equals(list) ) {
			return "[]";
		}
		if ( start >= list.size() ) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Resource r = null;
		for ( int i = start ; i < list.size() ; i++ ) {
			r = list.get(i);
			sb.append("{")
			  .append("\"").append("id").append("\" : \"").append(r.getId()).append("\"").append(",")
			  .append("\"").append("name").append("\" : \"").append(r.getName()).append("\"").append(",")
			  .append("\"").append("remark").append("\" : \"").append(r.getRemark()).append("\"").append(",")
			  .append("\"").append("albumid").append("\" : \"").append(r.getAlbumId()).append("\"").append(",")
			  .append("\"").append("albumname").append("\" : \"").append(r.getAlbumName()).append("\"").append(",")
			  .append("\"").append("albumparent").append("\" : \"").append(r.getAlbumParent()).append("\"").append(",")
			  .append("\"").append("weburl").append("\" : \"").append(r.getWebUrl()).append("\"").append(",")
			  .append("\"").append("smallweburl").append("\" : \"").append(Toolkit.getSmallImageName(r.getWebUrl())).append("\"").append(",")
			  .append("\"").append("createtime").append("\" : \"").append(r.getCreateTime()).append("\"")			  
			  .append("}");
			if ( i < list.size() -1 ) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 将资源列表转换为JSON字符串
	 * @param list
	 * @return
	 */
	public static String resource2Json(Resource r){
		if ( r == null )
			return "{}";
		StringBuilder sb = new StringBuilder();
		sb.append("{")
		  .append("\"").append("id").append("\" : \"").append(r.getId()).append("\"").append(",")
		  .append("\"").append("name").append("\" : \"").append(r.getName()).append("\"").append(",")
		  .append("\"").append("remark").append("\" : \"").append(r.getRemark()).append("\"").append(",")
		  .append("\"").append("albumid").append("\" : \"").append(r.getAlbumId()).append("\"").append(",")
		  .append("\"").append("albumname").append("\" : \"").append(r.getAlbumName()).append("\"").append(",")
		  .append("\"").append("albumparent").append("\" : \"").append(r.getAlbumParent()).append("\"").append(",")
		  .append("\"").append("weburl").append("\" : \"").append(r.getWebUrl()).append("\"").append(",")
		  .append("\"").append("createtime").append("\" : \"").append(r.getCreateTime()).append("\"")			  
		  .append("}");
		return sb.toString();
	}
	
	/**
	 * 内容为空的过滤器
	 * @param input
	 * @param notice
	 * @return
	 */
	public static String nullFilter(String input,String notice){
		if ( input == null || "".equals(input) ) {
			return notice;
		} else {
			return input;
		}
	}
	
	/**
	 * 返回系统支持的图片格式
	 * @return
	 */
	public static Set<String> imageType(){
		Set<String> set = new HashSet<String>();
		set.add("bmp");
		set.add("jpg");
		set.add("wbmp");
		set.add("jpeg");
		set.add("png");
		set.add("gif");
		return set;
	}
	
	/**
	 * 获取图片文件对应的裁剪过的小文件名称
	 * @param fileName
	 * @return
	 */
	public static String getSmallImageName(String fileName){
		if ( fileName != null && !"".equals(fileName) ) {
			int point = fileName.lastIndexOf(".");
			if ( point > -1 ) {
				return fileName.substring(0,point) + "_m" + fileName.substring(point, fileName.length());
			}
		}
		return null;
	}
	
	/**
	 * 得到格式化后的时间
	 * @param time
	 * @return
	 */
	public static String getDateString(long time){
		String model = "yyyy-MM-dd"; //yyyy/MM/dd HH:mm:ss.SSS
		SimpleDateFormat formater = new SimpleDateFormat(model);
		return formater.format(new Date(time));
	}
		
	/**
	 * 移除通配符
	 * @param time
	 * @return
	 */
	public static String removeWildcard(String keyword){
		if ( keyword != null && !"".equals(keyword) ) {
			keyword = keyword.replaceAll("%", "");
			keyword = keyword.replaceAll("_", "");
			//keyword = keyword.replaceAll("[", "");
			//keyword = keyword.replaceAll("]", "");
			keyword = keyword.replaceAll("!", "");
			keyword = keyword.replaceAll("^", "");
		}
		return keyword;
	}
	
	/**
	 * 将ISO-8859-1转码为UTF-8
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode8859toUtf8(String input) throws UnsupportedEncodingException {
		if ( input == null )
			return null;
		return new String(input.getBytes("ISO-8859-1"),"utf-8");
	}
	
	/**
	 * 剪切字符串中指定字符后面的字符串，将前面的字符串返回
	 * @param input
	 * @param keyword
	 * @return
	 */
	public static String cutStringEnd(String input,String keyword){
		if ( input == null || keyword == null ) {
			return "";
		}
		if ( input.indexOf(keyword) < 0 ) {
			return input;
		}
		return input.substring(0, input.indexOf(keyword));
	}

	/**
	 * 将数据保存到文件中
	 * @param file
	 * @param content
	 * @throws Exception
	 */
	public static void writeInFile(File file, String content)
			throws Exception {
		if ( file == null || content == null )
			throw new NullPointerException("input value is null.");
		if ( !file.exists() ) {
			//System.out.println(file.getAbsolutePath());
			file.createNewFile();
		}
		//System.out.println(content);
		RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
		long fileLength = randomFile.length();
		randomFile.seek(fileLength);
		randomFile.writeBytes(content);
		randomFile.close();
		randomFile = null;
		System.out.println("save file " + file.getAbsolutePath());
	}
	
	/**
	 * 本系统支持的图片格式
	 * @param fileName
	 * @return
	 */
	public static boolean isImage(String fileName){
		if ( fileName == null || "".equals(fileName) ) {
			return false;
		}
		return fileName.endsWith(ImageUtil.IMAGE_TYPE_BMP) ||
			   fileName.endsWith(ImageUtil.IMAGE_TYPE_GIF) ||
			   fileName.endsWith(ImageUtil.IMAGE_TYPE_JPEG) ||
			   fileName.endsWith(ImageUtil.IMAGE_TYPE_JPG) ||
			   fileName.endsWith(ImageUtil.IMAGE_TYPE_PNG) ||
			   fileName.endsWith(ImageUtil.IMAGE_TYPE_PSD);
	}
	
	/**
	 * 本系统支持的视频格式
	 * @param fileName
	 * @return
	 */
	public static boolean isVideo(String fileName){
		if ( fileName == null || "".equals(fileName) ) {
			return false;
		}
		return fileName.endsWith(".ogg") ||
			   fileName.endsWith(".mp4") ||
			   fileName.endsWith("webm");
	}
	
	/**
	 * 本系统支持的音频格式
	 * @param fileName
	 * @return
	 */
	public static boolean isAudio(String fileName){
		if ( fileName == null || "".equals(fileName) ) {
			return false;
		}
		return fileName.endsWith(".ogg") ||
			   fileName.endsWith(".mpeg") ||
			   fileName.endsWith(".wav") ||
			   fileName.endsWith(".mp3");
	}
	
	/**
	 * 读取K,V形式存储的文件，将V读出来
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readFile(File file) throws Exception{
		InputStream is = new FileInputStream(file);
		//InputStreamReader isReader = new InputStreamReader(is,"utf-8");//一定要加上编码
		InputStreamReader isReader = new InputStreamReader(is);//一定要加上编码
		BufferedReader br = new BufferedReader(isReader);
		String line = "";
		String[] values = null;
		StringBuilder sb = new StringBuilder(); 
		while ((line = br.readLine()) != null) {
			if ( !"".equals(line.trim()) ) {
				values = line.trim().split("=");
				if ( values.length == 2 ) {
					sb.append(values[1]).append("\r\n");
				}
			}
		}
		br.close();
		isReader.close();
		is.close();
		return sb.toString();
	}
	
	public static List<String> readKey(File file) throws Exception{
		List<String> str = new ArrayList<String>();
		InputStream is = new FileInputStream(file);
		//InputStreamReader isReader = new InputStreamReader(is,"utf-8");//一定要加上编码
		InputStreamReader isReader = new InputStreamReader(is);//一定要加上编码
		BufferedReader br = new BufferedReader(isReader);
		String line = "";
		String[] values = null;
		StringBuilder sb = new StringBuilder(); 
		while ((line = br.readLine()) != null) {
			if ( !"".equals(line.trim()) ) {
				values = line.trim().split("=");
				if ( values.length == 2 ) {
					str.add(values[0]);
					//sb.append(values[1]).append("\r\n");
				}
			}
		}
		br.close();
		isReader.close();
		is.close();
		return str;
	}
	
	public static List<String> readValue(File file) throws Exception{
		List<String> str = new ArrayList<String>();
		InputStream is = new FileInputStream(file);
		InputStreamReader isReader = new InputStreamReader(is,"utf-8");//一定要加上编码
		//InputStreamReader isReader = new InputStreamReader(is);//一定要加上编码
		BufferedReader br = new BufferedReader(isReader);
		String line = "";
		String[] values = null;
		StringBuilder sb = new StringBuilder(); 
		while ((line = br.readLine()) != null) {
			if ( !"".equals(line.trim()) ) {
				str.add(line.trim());
			}
		}
		br.close();
		isReader.close();
		is.close();
		return str;
	}
	
	public static String key2value(List<String> keys,List<String> values) throws Exception{
		if ( keys.size() != values.size() ) {
			throw new Exception("Key and value are not match.");
		}
			
		StringBuilder sb = new StringBuilder(); 
		for( int i = 0 ; i < keys.size() ; i ++ ) {
			sb.append(keys.get(i)).append("=").append(values.get(i)).append("\r\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try {
			File keyFile = new File("D:/zh-value.txt");
			File valueFile = null;
			valueFile = new File("D:/en-value.txt");
			valueFile = new File("D:/fr-value.txt");
			//valueFile = new File("D:/ru-value.txt");
			//valueFile = new File("D:/es-value.txt");
			//valueFile = new File("D:/zh.txt");
			File targetFile = new File("D:/target.txt");
			Toolkit.writeInFile(targetFile, 
					Toolkit.key2value(Toolkit.readKey(keyFile),Toolkit.readValue(valueFile)));
			
			
//			File srcFile = new File("D:/en.txt");
//			File targetFile = new File("D:/en-value.txt");
//			Toolkit.writeInFile(targetFile, Toolkit.readFile(srcFile));
			
			
//			System.out.println("中文");// 1
//			System.out.println("中文".getBytes());// 2
//			System.out.println("中文".getBytes("GB2312"));// 3
//			System.out.println("中文".getBytes("ISO8859_1"));// 4
//			System.out.println(new String("中文".getBytes()));// 5
//			System.out.println(new String("中文".getBytes(), "GB2312"));// 6
//			System.out.println(new String("中文".getBytes(), "ISO8859_1"));// 7
//			System.out.println(new String("中文".getBytes("GB2312")));// 8
//			System.out.println(new String("中文".getBytes("GB2312"), "GB2312"));// 9
//			System.out
//					.println(new String("中文".getBytes("GB2312"), "ISO8859_1"));// 10
//			System.out.println(new String("中文".getBytes("ISO8859_1")));// 11
//			System.out
//					.println(new String("中文".getBytes("ISO8859_1"), "GB2312"));// 12
//			System.out.println(new String("中文".getBytes("ISO8859_1"),
//					"ISO8859_1"));// 13
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
