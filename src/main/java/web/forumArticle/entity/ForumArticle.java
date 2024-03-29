package web.forumArticle.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import web.forumMessage.entity.ForumMessage;
import web.forumReport.entity.ForumReport;
import web.forumType.entity.ForumType;

public class ForumArticle implements Serializable{
	
	private Integer forArtNo;
	private Integer forTypeNo;
	private Integer artStateNo;
	private String memAcct;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp forArtPosttime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp forArtEdittime;
	private String forArtTitle;
	private String forArtContent;
	private Integer forArtView;
	private List<ForumMessage> message;
	private List<ForumType> type;
	private List<ForumReport> report;
	
	public ForumArticle() {
	}

	public ForumArticle(Integer forArtNo, Integer forTypeNo, Integer artStateNo, String memAcct,
			Timestamp forArtPosttime, Timestamp forArtEdittime, String forArtTitle, String forArtContent,
			Integer forArtView) {
		super();
		this.forArtNo = forArtNo;
		this.forTypeNo = forTypeNo;
		this.artStateNo = artStateNo;
		this.memAcct = memAcct;
		this.forArtPosttime = forArtPosttime;
		this.forArtEdittime = forArtEdittime;
		this.forArtTitle = forArtTitle;
		this.forArtContent = forArtContent;
		this.forArtView = forArtView;
	}

	public Integer getForArtNo() {
		return forArtNo;
	}

	public void setForArtNo(Integer forArtNo) {
		this.forArtNo = forArtNo;
	}

	public Integer getForTypeNo() {
		return forTypeNo;
	}

	public void setForTypeNo(Integer forTypeNo) {
		this.forTypeNo = forTypeNo;
	}

	public Integer getArtStateNo() {
		return artStateNo;
	}

	public void setArtStateNo(Integer artStateNo) {
		this.artStateNo = artStateNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public Timestamp getForArtPosttime() {
		return forArtPosttime;
	}

	public void setForArtPosttime(Timestamp forArtPosttime) {
		this.forArtPosttime = forArtPosttime;
	}

	public Timestamp getForArtEdittime() {
		return forArtEdittime;
	}

	public void setForArtEdittime(Timestamp forArtEdittime) {
		this.forArtEdittime = forArtEdittime;
	}

	public String getForArtTitle() {
		return forArtTitle;
	}

	public void setForArtTitle(String forArtTitle) {
		this.forArtTitle = forArtTitle;
	}

	public String getForArtContent() {
		return forArtContent;
	}

	public void setForArtContent(String forArtContent) {
		this.forArtContent = forArtContent;
	}

	public Integer getForArtView() {
		return forArtView;
	}

	public void setForArtView(Integer forArtView) {
		this.forArtView = forArtView;
	}

	public List<ForumMessage> getMessage() {
		return message;
	}

	public void setMessage(List<ForumMessage> message) {
		this.message = message;
	}

	public List<ForumType> getType() {
		return type;
	}

	public void setType(List<ForumType> type) {
		this.type = type;
	}

	public List<ForumReport> getReport() {
		return report;
	}

	public void setReport(List<ForumReport> report) {
		this.report = report;
	}
	
}
