package web.forum.report.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORUM_REPORT")
public class ForumReport implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOR_REPT_NO")
	private Integer forReptNo;
	
	@Column(name = "FOR_ART_NO")
	private Integer forArtNo;
	
	@Column(name = "FOR_MSG_NO")
	private Integer forMsgNo;
	
	@Column(name = "MEM_ACCT")
	private String memAcct;
	
	@Column(name = "ADMIN_ACCT")
	private String adminAcct;
	
	@Column(name = "FOR_REPT_TIME")
	private Timestamp forReptTime;
	
	@Column(name = "FOR_REPT_CHECKTIME")
	private Timestamp forReptChecktime;
	
	@Column(name = "FOR_REPT_STATE")
	private Integer forReptState;
	
	@Column(name = "FOR_REPT_CONTENT")
	private String forReptContent;

	public Integer getForReptNo() {
		return forReptNo;
	}

	public void setForReptNo(Integer forReptNo) {
		this.forReptNo = forReptNo;
	}

	public Integer getForArtNo() {
		return forArtNo;
	}

	public void setForArtNo(Integer forArtNo) {
		this.forArtNo = forArtNo;
	}

	public Integer getForMsgNo() {
		return forMsgNo;
	}

	public void setForMsgNo(Integer forMsgNo) {
		this.forMsgNo = forMsgNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public String getAdminAcct() {
		return adminAcct;
	}

	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}

	public Timestamp getForReptTime() {
		return forReptTime;
	}

	public void setForReptTime(Timestamp forReptTime) {
		this.forReptTime = forReptTime;
	}

	public Timestamp getForReptChecktime() {
		return forReptChecktime;
	}

	public void setForReptChecktime(Timestamp forReptChecktime) {
		this.forReptChecktime = forReptChecktime;
	}

	public Integer getForReptState() {
		return forReptState;
	}

	public void setForReptState(Integer forReptState) {
		this.forReptState = forReptState;
	}

	public String getForReptContent() {
		return forReptContent;
	}

	public void setForReptContent(String forReptContent) {
		this.forReptContent = forReptContent;
	}

}
