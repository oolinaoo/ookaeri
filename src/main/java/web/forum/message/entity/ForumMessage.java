package web.forum.message.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORUM_MESSAGE")
public class ForumMessage implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOR_MSG_NO")
	private Integer forMsgNo;
	
	@Column(name = "FOR_ART_NO")
	private Integer forArtNo;
	
	@Column(name = "MEM_ACCT")
	private String memAcct;
	
	@Column(name = "FOR_MSG_POSTTIME")
	private Timestamp forMsgPosttime;
	
	@Column(name = "FOR_MSG_EDITTIME")
	private Timestamp forMsgEdittime;
	
	@Column(name = "FOR_MSG_CONTENT")
	private String forMsgContent;
	
	@Column(name = "FOR_MSG_STATE")
	private Integer forMsgState;
	

	public Integer getForMsgNo() {
		return forMsgNo;
	}

	public void setForMsgNo(Integer forMsgNo) {
		this.forMsgNo = forMsgNo;
	}

	public Integer getForArtNo() {
		return forArtNo;
	}

	public void setForArtNo(Integer forArtNo) {
		this.forArtNo = forArtNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public Timestamp getForMsgPosttime() {
		return forMsgPosttime;
	}

	public void setForMsgPosttime(Timestamp forMsgPosttime) {
		this.forMsgPosttime = forMsgPosttime;
	}

	public Timestamp getForMsgEdittime() {
		return forMsgEdittime;
	}

	public void setForMsgEdittime(Timestamp forMsgEdittime) {
		this.forMsgEdittime = forMsgEdittime;
	}

	public String getForMsgContent() {
		return forMsgContent;
	}

	public void setForMsgContent(String forMsgContent) {
		this.forMsgContent = forMsgContent;
	}

	public Integer getForMsgState() {
		return forMsgState;
	}

	public void setForMsgState(Integer forMsgState) {
		this.forMsgState = forMsgState;
	}

}
