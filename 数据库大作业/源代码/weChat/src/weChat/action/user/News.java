package weChat.action.user;

public class News  implements Comparable<News> {
	private int id;
	private int userid;
	private int contactid;
	private String content;
	private String sendtime;
	
	public News() {
		this.userid = 0;
		this.contactid = 0;
		this.content = "";
		this.sendtime = "";
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setUserid(int id) {
		this.userid = id;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setContactid(int id) {
		this.contactid = id;
	}
	
	public int getContactid() {
		return contactid;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setSendtime(String time) {
		this.sendtime = time;
	}
	
	public String getSendtime() {
		return sendtime;
	}

	@Override
	public int compareTo(News o) {
		// TODO Auto-generated method stub
		int i = sendtime.compareTo(o.sendtime);
		if(i < 0) {
			return -1;
		} else if(i == 0) {
			return 0;
		}
		return 1;
	}
}
