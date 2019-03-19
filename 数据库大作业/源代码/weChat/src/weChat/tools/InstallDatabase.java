package weChat.tools;

/**
 * 
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import weChat.tools.Toolkit;

/**
 * 过滤器 启动时调用isTablesInstalled检查系统是否初始化，
 * 如果返回false，有几种原因，一种是第一次执行，第二种是存在删除表的情况，
 * 第三种是JDBC配置信息出错，此时，要设置application中的TABLES_HAD_BEEN_INSTALL参数为false
 * 无论哪一种，都要进入install.jsp页面，重新检查JDBC配置，
 * 并安装缺少的表，以及初始化数据，用户可以指定驱动程序包（先上传）。
 * 
 * 安装完成后，主意调用JDBCInfo的save方法，将配置信息保存下来，并自动设置为useC3P0=true
 * @author jack
 */
public class InstallDatabase {
	
	//此参数在Application中
	public static final String TABLES_HAD_BEEN_INSTALL = "tables_had_been_installed"; 
	public static final String USER_TABLE_NAME = "user";
	public static final String DEFAULT_DATABASE_NAME = "myhomesystem";
		
	/**
	 * 是否是MySQL数据库
	 * @return
	 */
	public static boolean isMySQLDatabase(){
		Connection conn = null;
		try {
			conn = BaseDao.getConnection();
			DatabaseMetaData md = conn.getMetaData();			
			String databaseName = md.getDatabaseProductName();
			if ( databaseName != null ) {
				if ( databaseName.toLowerCase().equals("mysql") ) {
					return true;
				}
			}
		} catch (Exception e) {
		} finally {
			if ( conn != null ) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return false;
	}

	/**
	 * 根据驱动判断是否是H2数据库
	 * @param httpRequest
	 * @return
	 */
	public static boolean isH2DataBase(){
		return "org.h2.Driver".equals(JDBCInfo.getInstance().getDriverClass());
	}
	
	/**
	 * 在本系统中，分析配置文件中的JDBC的URL与系统按照一定规则自动取得的URL是否相等，如果相等表示该数据库与配置中的一致，不用创建
	 * @param httpRequest
	 * @return
	 * @throws Exception 
	 */
	public static boolean isDataBaseExist(HttpServletRequest httpRequest) throws Exception{
		if ( isH2DataBase() ) {
			String url = null;
			if ( httpRequest.getRequestURI().indexOf(".jsp?") > -1 || 
					httpRequest.getRequestURI().indexOf(".jsp") > -1) {
				url = getH2JDBCURLToJSP(httpRequest);
			} else {
				url = getH2JDBCURL(httpRequest);
			}
			if ( !url.equals(JDBCInfo.getInstance().getJdbcUrl()) ) {
				//如果JDBC的URL变了，以新的为准，更新配置文件
				JDBCInfo.getInstance().setJdbcUrl(url);
				JDBCInfo.getInstance().saveJDBCConfig();
				JDBCInfo.getInstance().saveC3P0Config();
			} 
		}
		return isTablesInstalled();
	}
	
	/**
	 * 表是否已经生成
	 * @return
	 */
	public static boolean isTablesInstalled(){
		Connection conn = null;
		try {
			Map<String,Boolean> tables = new HashMap<String,Boolean>();
			tables.put(USER_TABLE_NAME, false);
			conn = BaseDao.getConnection();
			DatabaseMetaData md = conn.getMetaData();			
			ResultSet rs = md.getTables(
					conn.getCatalog(), null, null, new String[]{"TABLE"});//TABLE一定要大写，否则取不出数据
			//System.out.println(conn.getCatalog());
			while ( rs.next() ) {
				//System.out.println(rs.getString("TABLE_NAME"));
				if ( tables.get(rs.getString("TABLE_NAME").toLowerCase()) != null ) {
					tables.put(rs.getString("TABLE_NAME").toLowerCase(), true);
				}
			}
			return tables.get(USER_TABLE_NAME);
		} catch (Exception e) {
		} finally {
			if ( conn != null ) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return false;
	}

	/**
	 * 数据库是否存在
	 * @return
	 */
	public static boolean isDatabaseNameEmpty(){
		Connection conn = null;
		try {
			conn = BaseDao.getConnection();
			return "".equals(conn.getCatalog());
		} catch (Exception e) {
		} finally {
			if ( conn != null ) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return true;
	}
	
	/**
	 * 创建数据库
	 * @param name
	 * @return
	 */
	public static String createDatabase(String name){
		if ( name == null || "".equals(name) ) {
			name = InstallDatabase.DEFAULT_DATABASE_NAME;
		}
		String exception = null;
		try {
			System.out.println(JDBCInfo.getInstance().getJdbcUrl());
			Connection conn = BaseDao.getConnection();
			System.out.println(conn.getCatalog());
			BaseDao.execute(conn,"create database if not exists " + name);
			String newUrl = null;
			if ( JDBCInfo.getInstance().getJdbcUrl().endsWith("/") ) {
				newUrl = JDBCInfo.getInstance().getJdbcUrl() + name;
			} else {
				newUrl = JDBCInfo.getInstance().getJdbcUrl() + "/" + name;
			}
			JDBCInfo.getInstance().setJdbcUrl(newUrl);//修改JDBCInfo之后，BaseDao取出的Connection就是新的了
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		}
		return exception;
	}
	
	/**
	 * 安装用户表
	 * @return
	 */
	public static String installUserTable(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS user (")
			  .append("id int(7) not null primary key auto_increment,")
			  .append("email varchar(20) not null unique,")
			  .append("password varchar(20) not null,")
			  .append("nickname varchar(20) not null,")
			  .append("address varchar(20) not null,")
			  .append("sex int(7) not null,")
			  .append("age int(7) not null,")
			  .append("qrcode varchar(50) not null unique,")
			  .append("photourl varchar(50) not null default 'xxx',")
			  .append(" constraint C1 check(sex between 0 and 1)")
			  .append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ");		
			
			Connection conn = BaseDao.getConnection();
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		}
		return exception;
	}
	
	/**
	 * 安装专辑表
	 * @return
	 */
	public static String installAlbumTable(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS home_album (")
			  .append("id int(11) NOT NULL AUTO_INCREMENT,")
			  .append("name varchar(50) NOT NULL,")
			  .append("parent varchar(50) NOT NULL,")
			  .append("userid int(11) NOT NULL,")
			  .append("username varchar(50) NOT NULL,")
			  .append("imageweburl varchar(500) DEFAULT NULL,")
			  .append("private int(11) NOT NULL,")
			  .append("imagefileurl varchar(500) DEFAULT NULL,")
			  .append("PRIMARY KEY (id)")
			  .append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ");		
			Connection conn = BaseDao.getConnection();
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 安装资源表
	 * @return
	 */
	public static String installResourceTable(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS home_resource (")
			  .append("id int(11) NOT NULL AUTO_INCREMENT,")
			  .append("name varchar(200) NOT NULL,")
			  .append("remark varchar(500) NOT NULL,")
			  .append("fileurl varchar(500) NOT NULL,")
			  .append("deleted int(11) NOT NULL,")
			  .append("userid int(11) NOT NULL,")
			  .append("username varchar(50) NOT NULL,")
			  .append("albumid int(11) NOT NULL,")
			  .append("albumname varchar(50) NOT NULL,")
			  .append("albumparent varchar(50) DEFAULT NULL,")
			  .append("private int(11) NOT NULL,")
			  .append("weburl varchar(500) DEFAULT NULL,")
			  .append("createtime bigint(20) NOT NULL,")
			  .append("PRIMARY KEY (id)")
			  .append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ");		
			Connection conn = BaseDao.getConnection();
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 初始化数据
	 * @return
	 */
	public static String initData(){
		String exception = null;
		try {
			Connection conn = BaseDao.getConnection();
			
			StringBuilder sb = new StringBuilder();
			//只删除全部管理员就好
			//sb.append("delete from home_user where admin = 1");
			//BaseDao.execute(conn,sb.toString());
			
			sb = new StringBuilder();
			sb.append("insert into home_user(name,pwd,locked,admin) values('admin','1',0,1)");
			//sb.append("insert into home_user(name,pwd,locked,admin) values('1','1',0,0)");
			//sb.append("insert into home_user(name,pwd,locked,admin) values('2','1',0,0)");
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage()).replaceAll("\"", "'");
		}
		return exception;
	}
	
	/**
	 * 保存用户输入的数据库连接信息到配置文件中
	 * @return
	 */
	public static String saveConfig(){
		String exception = null;
		try {
			JDBCInfo.getInstance().saveJDBCConfig();
			JDBCInfo.getInstance().saveC3P0Config();
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 是否是H2数据库
	 * @return
	 */
	public static boolean isH2Database(){
		Connection conn = null;
		try {
			conn = BaseDao.getConnection();
			DatabaseMetaData md = conn.getMetaData();			
			String databaseName = md.getDatabaseProductName();
			if ( databaseName != null ) {
				if ( databaseName.toLowerCase().contains("h2") ) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if ( conn != null ) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 安装用户表
	 * @return
	 */
	public static String installUserTableInH2(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS home_user (")
			  .append("id int(11) NOT NULL AUTO_INCREMENT,")
			  .append("name varchar(50) NOT NULL,")
			  .append("pwd varchar(50) NOT NULL,")
			  .append("locked int(11) NOT NULL,")
			  .append("admin int(11) NOT NULL,")
			  .append("PRIMARY KEY (id)")
			  .append(")");	
			System.out.println(sb.toString());
			//Connection conn = BaseDao.getConnection();
			//BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 安装专辑表
	 * @return
	 */
	public static String installAlbumTableInH2(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS home_album (")
			  .append("id int(11) NOT NULL AUTO_INCREMENT,")
			  .append("name varchar(50) NOT NULL,")
			  .append("parent varchar(50) NOT NULL,")
			  .append("userid int(11) NOT NULL,")
			  .append("username varchar(50) NOT NULL,")
			  .append("imageweburl varchar(500) DEFAULT NULL,")
			  .append("private int(11) NOT NULL,")
			  .append("imagefileurl varchar(500) DEFAULT NULL,")
			  .append("PRIMARY KEY (id)")
			  .append(") ");		
			System.out.println(sb.toString());
			Connection conn = BaseDao.getConnection();
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 安装资源表
	 * @return
	 */
	public static String installResourceTableInH2(){
		String exception = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS home_resource (")
			  .append("id int(11) NOT NULL AUTO_INCREMENT,")
			  .append("name varchar(200) NOT NULL,")
			  .append("remark varchar(500) ,")
			  .append("fileurl varchar(500) ,")
			  .append("deleted int(11) NOT NULL,")
			  .append("userid int(11) ,")
			  .append("username varchar(50) ,")
			  .append("albumid int(11) ,")
			  .append("albumname varchar(50) NOT NULL,")
			  .append("albumparent varchar(50) DEFAULT NULL,")
			  .append("private int(11) NOT NULL,")
			  .append("weburl varchar(500) DEFAULT NULL,")
			  .append("createtime bigint(20),")
			  .append("PRIMARY KEY (id)")
			  .append(") ");		
			System.out.println(sb.toString());
			Connection conn = BaseDao.getConnection();
			BaseDao.execute(conn,sb.toString());
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage());
		} 
		return exception;
	}
	
	/**
	 * 初始化数据
	 * @return
	 */
	public static String initDataInH2(){
		String exception = null;
		try {
			Connection conn = BaseDao.getConnection();
			
			StringBuilder sb = new StringBuilder();
			//只删除全部管理员就好
			sb.append("delete from home_user where admin = 1");
			BaseDao.execute(conn,sb.toString());
			
			sb = new StringBuilder();
			sb.append("insert into home_user(name,pwd,locked,admin) values('admin','1',0,1)");
			BaseDao.execute(conn,sb.toString());
			
		} catch (Exception e) {
			exception = Toolkit.htmlTransfer(e.getMessage()).replaceAll("\"", "'");
		} 
		return exception;
	}
	
	/**
	 * 获取App根路径
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getAppRootPath(HttpServletRequest request) {
		String path = new File(request.getRealPath(request.getRequestURI())).getAbsolutePath();
		return new File(path).getParent();
		
	}
	
	/**
	 * 得到系统默认的H2数据库的绝对路径
	 * @param request
	 * @return
	 */
	public static String getH2DatabaseAbsolutePath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(getAppRootPath(request))
		  .append("/WEB-INF/h2/homedisk");
		return sb.toString();
	}
	
	/**
	 * 得到系统默认的H2数据库的URL
	 * @param request
	 * @return
	 */
	public static String getH2JDBCURL(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:h2:")
		  .append(getH2DatabaseAbsolutePath(request));
		return sb.toString();
	}
	
	/**
	 * 获取App根路径
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getAppRootPathForJSP(HttpServletRequest request) {
		String path = new File(request.getRealPath(request.getRequestURI())).getParent();
		return new File(path).getParent();
		
	}
	
	/**
	 * 得到系统默认的H2数据库的绝对路径
	 * @param request
	 * @return
	 */
	public static String getH2DatabaseAbsolutePathForJSP(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(getAppRootPathForJSP(request))
		  .append("/WEB-INF/h2/homedisk");
		return sb.toString();
	}
	
	/**
	 * 得到系统默认的H2数据库的URL
	 * @param request
	 * @return
	 */
	public static String getH2JDBCURLToJSP(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:h2:")
		  .append(getH2DatabaseAbsolutePathForJSP(request));
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		//InstallDatabase.isMySQLDatabase();
		System.out.println("!!!");
		InstallDatabase.installUserTable();
		InstallDatabase.initData();
		System.out.println("!!!!!!");
	}
	
}
