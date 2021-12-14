package web.forumMessage.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ForumMessage {

	private Integer forMsgNo;
	private Integer forArtNo;
	private String memAcctMsg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp forMsgPosttime;
	private Timestamp forMsgEdittime;
	private String forMsgContent;
	private Integer forMsgState;
	
	public ForumMessage() {
	}

	public ForumMessage(Integer forMsgNo, Integer forArtNo, String memAcctMsg, Timestamp forMsgPosttime,
			Timestamp forMsgEdittime, String forMsgContent, Integer forMsgState) {
		super();
		this.forMsgNo = forMsgNo;
		this.forArtNo = forArtNo;
		this.memAcctMsg = memAcctMsg;
		this.forMsgPosttime = forMsgPosttime;
		this.forMsgEdittime = forMsgEdittime;
		this.forMsgContent = forMsgContent;
		this.forMsgState = forMsgState;
	}

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

	public String getMemAcctMsg() {
		return memAcctMsg;
	}

	public void setMemAcctMsg(String memAcctMsg) {
		this.memAcctMsg = memAcctMsg;
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
