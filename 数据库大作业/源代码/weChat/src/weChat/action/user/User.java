package weChat.action.user;

import javax.websocket.server.PathParam;

public class User {
	
	private int id;
	private String email;
	private String password;
	private String nickname;
	private String address;
	private int sex;
	private int age;
	private String qrcode;
	private String photourl;

	public User(){
		this.email = null;
		this.password = "";
		this.nickname = "";
		this.address = "";
		this.sex = 0;
		this.age = 0;
		this.qrcode = "";
		this.photourl = "";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return password;
	}

	public void setPwd(String pwd) {
		this.password = pwd;
	}

	public String getName() {
		return nickname;
	}

	public void setName(String name) {
		this.nickname = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photiurl) {
		this.photourl = photiurl;
	}
}
