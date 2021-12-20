package web.forumMessage.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import web.forumReport.entity.ForumReport;

public class ForumMessage {

	private Integer forMsgNo;
	private Integer forArtNo;
	private String memAcct;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp forMsgPosttime;
	private String forMsgContent;
	private Integer forMsgState;
	private List<ForumReport> report;
	
	public ForumMessage() {
	}

	public ForumMessage(Integer forMsgNo, Integer forArtNo, String memAcct, Timestamp forMsgPosttime,
			String forMsgContent, Integer forMsgState) {
		super();
		this.forMsgNo = forMsgNo;
		this.forArtNo = forArtNo;
		this.memAcct = memAcct;
		this.forMsgPosttime = forMsgPosttime;
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

	public List<ForumReport> getReport() {
		return report;
	}

	public void setReport(List<ForumReport> report) {
		this.report = report;
	}
	
}
