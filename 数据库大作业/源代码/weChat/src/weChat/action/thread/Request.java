package weChat.action.thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.*;

import weChat.tools.BaseDao;

class thread1 extends Thread {
	Integer id;
	public thread1(Integer id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	public void run() {
		//insert 10000条
		try {
			File f = new File("insert_time.txt");
			if(!f.exists()) f.createNewFile();
			FileWriter fWriter = new FileWriter(f, true);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			
			Connection conn = BaseDao.getConnection();
			
			long startTime =  System.currentTimeMillis();
			for(Integer i=id; i<id + 10000; i++) {
				int temp = 0;
				String email = String.valueOf(i);
				String sql = "insert into user(email, password, nickname, address, sex, age, qrcode) values ('"+email+"', '"+email+"', '"+email+"', '"+email+"', "+temp+", "+temp+", '"+email+"')";  
				conn.createStatement().execute(sql);
			}
			long endTime =  System.currentTimeMillis();
			long usedTime = (endTime-startTime);
			Integer temp = id + 10000;
			bWriter.write("第" + id + "到" + temp + "的数据插入时间：" + usedTime + "ms" + "\r\n");
			bWriter.close();
			conn.close();
			conn = null;
			System.out.println(id + "  ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class thread2 extends Thread {
	int id = 0;
	public  void run() {
		//select 死循环
		id++;
		try {
			FileWriter fWriter = new FileWriter("select_time.txt", true);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			
			Connection conn = BaseDao.getConnection();
			
			while(id++ < 200) {
				long startTime =  System.currentTimeMillis();
				String eamil = "1";
				String pwd = "1";
				String sql = "select * from user where password = '"+eamil+"'";
				ResultSet result = conn.createStatement().executeQuery(sql);
				release(result);
				long endTime =  System.currentTimeMillis();
				long usedTime = (endTime-startTime);
				
				bWriter.write("第" + id + "次数据查询时间：" + usedTime + "ms" + "\r\n");
				sleep(1000);
			}
			bWriter.close();
			conn.close();
			conn = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void release(ResultSet result) {
		// TODO Auto-generated method stub
		
	}
}

public class Request {
	public static void main(String[] args) {
		for(int i=0; i<100; i++) {
			new thread1(i*10000).start();
		}
		new thread2().start();
	}
}