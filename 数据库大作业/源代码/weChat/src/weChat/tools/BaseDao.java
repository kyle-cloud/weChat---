package weChat.tools;

/**
 * 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.search.IntegerComparisonTerm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.command.dml.Update;
import org.omg.CORBA.UserException;

import com.mysql.jdbc.CallableStatement;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import weChat.action.user.User;




public class BaseDao {
		
	public static Integer INT = 0;
	
	public static Integer VARCHAR = 1;
	
	public static Integer BIGINT = 2;
		
	/**
	 * 获得Connection对象
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws Exception { 
		return JDBCInfo.getInstance().getConnection();
	} 
	
	/**
	 * 预编译执行SQL
	 * @param sql
	 * @throws Exception
	 */
	public static void preparedExecute(String sql,PreparedMap<Integer,Object> params) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = BaseDao.getConnection();
			pstmt = conn.prepareStatement(sql); 
			if ( params != null ) {
				Integer intValue ;
				Long longValue;
				String strValue;
				List<Integer> keys = params.keys();
				int key;
				Object value;
				int seq;
				for ( int i = 0 ; i < keys.size() ; i++ ) {
					key = keys.get(i);
					value = params.getValue(i);
					seq = i + 1;
					//System.out.println(i+","+ key +","+value);
					if ( key == BaseDao.INT ) {
						intValue = value==null?0:(Integer)value;	
						pstmt.setInt(seq, intValue);
					} else if ( key == BaseDao.VARCHAR ) {
						strValue = value==null?"":String.valueOf(value);
						pstmt.setString(seq, strValue);
					} else if ( key == BaseDao.BIGINT ) {
						longValue = value==null?0:(Long)value;
						pstmt.setLong(seq, longValue);
					} 
				}
			}
			
			pstmt.executeUpdate(); 
			if ( !conn.getAutoCommit() )
				conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			if ( null != conn ) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 预编译执行SQL，返回查询结果
	 * @param sql
	 * @throws Exception
	 */
	public static ResultSet preparedQuery(String sql,PreparedMap<Integer,Object> params) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = BaseDao.getConnection();
			pstmt = conn.prepareStatement(sql); 
			if ( params != null ) {
				Integer intValue ;
				Long longValue;
				String strValue;
				List<Integer> keys = params.keys();
				int key;
				Object value;
				int seq;
				for ( int i = 0 ; i < keys.size() ; i++ ) {
					key = keys.get(i);
					value = params.getValue(i);
					seq = i + 1;
					//System.out.println(i+","+ key +","+value);
					if ( key == BaseDao.INT ) {
						intValue = value==null?0:(Integer)value;	
						pstmt.setInt(seq, intValue);
					} else if ( key == BaseDao.VARCHAR ) {
						strValue = value==null?"":String.valueOf(value);
						pstmt.setString(seq, strValue);
					} else if ( key == BaseDao.BIGINT ) {
						longValue = value==null?0:(Long)value;
						pstmt.setLong(seq, longValue);
					} 
				}
			}
			return pstmt.executeQuery(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * 执行不带返回值的SQL，即执行非查询的SQL
	 * @param sql
	 * @throws Exception
	 */
	public static void executeNotAutoCommit(Connection conn,String sql) throws Exception{
		conn = BaseDao.getConnection();
		conn.setAutoCommit(false);
		conn.createStatement().execute(sql);
	}
	
	/**
	 * 执行不带返回值的SQL，即执行非查询的SQL
	 * @param sql
	 * @throws Exception
	 */
	public static void execute(Connection conn,String sql) throws Exception{
		conn = BaseDao.getConnection();
		conn.createStatement().execute(sql);
		if ( !conn.getAutoCommit() ){
			conn.commit();
		}
	}
	
	/**
	 * 执行不带返回值的SQL，即执行非查询的SQL
	 * @param sql
	 * @throws Exception
	 */
	public static void execute(String sql) throws Exception{
		Connection conn = null;
		try {
			conn = BaseDao.getConnection();
			conn.createStatement().execute(sql);
			if ( !conn.getAutoCommit() )
				conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( null != conn ) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	

	public static ResultSet query(String sql) throws Exception{
		Connection conn = BaseDao.getConnection();
		return conn.createStatement().executeQuery(sql);
	}
	
	public static User QueryUser(String eamil, String pwd) throws Exception{
		User user = null;
		user = new User();
		ResultSet result = query("select * from user where email = '"+eamil+"' and password = '"+pwd+"'");
//		if(result.next()) {
//			System.out.println(result.getString("name"));
//		}
//		String Name = result.getString(1);
//		System.out.println("select * from home_user where name = '"+name+"' and pwd = '"+pwd+"'");
		
		if(result.next()) {
			user.setId(result.getInt("id"));
			user.setEmail(result.getString("email"));
			user.setAddress(result.getString("address"));
			user.setName(result.getString("nickname"));
			user.setSex(result.getInt("sex"));
			user.setAge(result.getInt("age"));
			user.setQrcode(result.getString("qrcode"));
			user.setPhotourl(result.getString("photourl"));
		}
		release(result);
		System.out.println(user.getEmail());
		return user;
	}
	
	public static User queryUser(String eamil) throws Exception{
		User user = null;
		user = new User();
		ResultSet result = query("select * from user where email = '"+eamil+"' for Update");
//		if(result.next()) {
//			System.out.println(result.getString("name"));
//		}
//		String Name = result.getString(1);
//		System.out.println("select * from home_user where name = '"+name+"' and pwd = '"+pwd+"'");
		
		if(result.next()) {
			user.setId(result.getInt("id"));
			user.setEmail(result.getString("email"));
			user.setAddress(result.getString("address"));
			user.setName(result.getString("nickname"));
			user.setSex(result.getInt("sex"));
			user.setAge(result.getInt("age"));
			user.setQrcode(result.getString("qrcode"));
			user.setPhotourl(result.getString("photourl"));
		}
		release(result);
		System.out.println(user.getEmail());
		return user;
	}
	
	public static void InsertUser(String email) throws Exception{
		User user = null;
		Connection conn = getConnection();
		int temp = 0;
		String sql = "insert into user(email, password, nickname, address, sex, age, qrcode) values ('"+email+"', '"+email+"', '"+email+"', '"+email+"', "+temp+", "+temp+", '"+email+"')";  
		conn.createStatement().execute(sql);
		
		conn.close();
		conn = null;
	}
	public static void deltUser(int id) throws Exception{
		User user = null;
		Connection conn = getConnection();
		String sql = "delete from user where id = "+id+"";
		conn.createStatement().execute(sql);
		conn.close();
		conn = null;
	}
	public static void UpdateUser(int id, String email) throws Exception{
		User user = null;
		Connection conn = getConnection();
		String sql = "update user set email = '"+email+"' where id = "+id+"";
		conn.createStatement().execute(sql);
		conn.close();
		conn = null;
	}
	/**
	 * 关闭rs和connection
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static void release(ResultSet rs) throws Exception{
		if ( rs != null ) {
			Connection conn = rs.getStatement().getConnection();
			rs.close();
			conn.close();
			rs = null;
		}
	}
	
	public List<User> select_user() throws Exception {
		List<User> list = new ArrayList<User>();
		Connection connection = getConnection();
		CallableStatement cStatement = (CallableStatement) connection.prepareCall("{call select_user()}");
		ResultSet resultSet = cStatement.executeQuery();
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
		release(resultSet);
		return list;
	}
	
	public static void main(String[] args) throws Exception{
//		String sql = "select * from home_user";
//		try {
//			ResultSet rs = BaseDao.query(sql);
//			System.out.println(rs.next());
//			BaseDao.release(rs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		sql = "update home_user set pwd = ? where id = ?";
//		PreparedMap<Integer,Object> params = new PreparedMap<Integer,Object>();
//		params.put(BaseDao.VARCHAR, "100");
//		params.put(BaseDao.INT, 1);
//		BaseDao.preparedExecute(sql, params);'
		
		System.out.println(QueryUser("1", "1").getName());
		
	}
}
