package web.login.entity;

import java.io.Serializable;

public class Login implements Serializable{
	
	private String memAcct;
	private String memPwd;
	private String memName;
	private String adminAcct;
	private String adminPwd;
	private String adminName;

	public Login() {
	}

	public Login(String memAcct, String memPwd, String memName, String adminAcct, String adminPwd,
			String adminName) {
		super();
		this.memAcct = memAcct;
		this.memPwd = memPwd;
		this.memName = memName;
		this.adminAcct = adminAcct;
		this.adminPwd = adminPwd;
		this.adminName = adminName;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getAdminAcct() {
		return adminAcct;
	}

	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}
