package web.forumMessage.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ForumMessageBo {
	
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
	private String forType;
	private Integer forMsgNo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp forMsgPosttime;
	private String forMsgContent;
	private Integer forMsgState;
	private String memAcctMsg;
	private Integer forReptNo;
	private Integer forMsgNoRept;
	private String memAcctRept;
	private String adminAcct;
	private Integer forReptState;
	private String forReptContent;
	
	public ForumMessageBo() {
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

	public String getForType() {
		return forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
	}

	public Integer getForMsgNo() {
		return forMsgNo;
	}

	public void setForMsgNo(Integer forMsgNo) {
		this.forMsgNo = forMsgNo;
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

	public String getMemAcctMsg() {
		return memAcctMsg;
	}

	public void setMemAcctMsg(String memAcctMsg) {
		this.memAcctMsg = memAcctMsg;
	}

	public Integer getForReptNo() {
		return forReptNo;
	}

	public void setForReptNo(Integer forReptNo) {
		this.forReptNo = forReptNo;
	}

	public Integer getForMsgNoRept() {
		return forMsgNoRept;
	}

	public void setForMsgNoRept(Integer forMsgNoRept) {
		this.forMsgNoRept = forMsgNoRept;
	}

	public String getMemAcctRept() {
		return memAcctRept;
	}

	public void setMemAcctRept(String memAcctRept) {
		this.memAcctRept = memAcctRept;
	}

	public String getAdminAcct() {
		return adminAcct;
	}

	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
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
