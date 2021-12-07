package web.admin.entity;

public class AdminVO implements java.io.Serializable{
	private String adminAcct;
	private String adminPwd;
	private String adminName;
	private Integer adminPos;
	private Integer adminState;
	private String adminPhone;
	
	public AdminVO() {
		
	}

	public AdminVO(String adminAcct, String adminPwd, String adminName, Integer adminPos, 
			Integer adminState, String adminPhone) {
		super();
		this.adminAcct = adminAcct;
		this.adminPwd = adminPwd;
		this.adminName = adminName;
		this.adminPos = adminPos;
		this.adminState = adminState;
		this.adminPhone = adminPhone;
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

	public Integer getAdminPos() {
		return adminPos;
	}

	public void setAdminPos(Integer adminPos) {
		this.adminPos = adminPos;
	}

	public Integer getAdminState() {
		return adminState;
	}

	public void setAdminState(Integer adminState) {
		this.adminState = adminState;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	
	
	
	
	
}
