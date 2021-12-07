package web.mem.entity;
import java.sql.Date;
import java.sql.Timestamp;

public class MemberVO implements java.io.Serializable {
	private String memAcct;
	private String memPwd;
	private String memName;
	private String memId;
	private String memSex;
	private String memEmail;
	private Date memBirthday;
	private Integer addrNo;
	private byte[] memPhoto;
	private String memPhone;
	private Timestamp acctCreatetime;
	private Integer memState;
	
	public MemberVO() {
		
	}

	public MemberVO(String memAcct, String memPwd, String memName, String memId, String memSex, String memEmail,
			Date memBirthday, Integer addrNo, byte[] memPhoto, String memPhone, Timestamp acctCreatetime,
			Integer memState) {
		
		this.memAcct = memAcct;
		this.memPwd = memPwd;
		this.memName = memName;
		this.memId = memId;
		this.memSex = memSex;
		this.memEmail = memEmail;
		this.memBirthday = memBirthday;
		this.addrNo = addrNo;
		this.memPhoto = memPhoto;
		this.memPhone = memPhone;
		this.acctCreatetime = acctCreatetime;
		this.memState = memState;
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

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getMemSex() {
		return memSex;
	}

	public void setMemSex(String memSex) {
		this.memSex = memSex;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}

	public Date getMemBirthday() {
		return memBirthday;
	}

	public void setMemBirthday(Date memBirthday) {
		this.memBirthday = memBirthday;
	}

	public Integer getAddrNo() {
		return addrNo;
	}

	public void setAddrNo(Integer addrNo) {
		this.addrNo = addrNo;
	}

	public byte[] getMemPhoto() {
		return memPhoto;
	}

	public void setMemPhoto(byte[] memPhoto) {
		this.memPhoto = memPhoto;
	}

	public String getMemPhone() {
		return memPhone;
	}

	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}

	public Timestamp getAcctCreatetime() {
		return acctCreatetime;
	}

	public void setAcctCreatetime(Timestamp acctCreatetime) {
		this.acctCreatetime = acctCreatetime;
	}

	public Integer getMemState() {
		return memState;
	}

	public void setMemState(Integer memState) {
		this.memState = memState;
	}

	
	

}
