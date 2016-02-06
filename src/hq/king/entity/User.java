package hq.king.entity;

import java.io.Serializable;



import android.R.integer;

public class User implements Serializable {

	private int id;
	private String name;
	private String email;
	private String password;
	private int isOnline;
	private int img;
	private String ip;
	private int port;
	private int group;

	public int getId() {
		return id;
	}

	public void setId(int id2) {
		this.id = id2;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {//�������еĶ��󶼿���ǿ������ת�������ҽ���o���ͱ�����User����
			//ֻ��������ָ����Object�����ã�����������󸳸��������ã������ڽ���ǿ������ת��֮ǰҪ��������
			//�жϣ���ͨ��instanceof��������жϣ����ҽ���o��User���������ʱ����true
			User user = (User) o;
			if (user.getId() == id && user.getIp().equals(ip)
					&& user.getPort() == port) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", isOnline=" + isOnline
				+ ", img=" + img + ", ip=" + ip + ", port=" + port + ", group="
				+ group + "]";
	}
}

