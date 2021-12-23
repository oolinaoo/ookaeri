package web.mem.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemNewsVO {
	private Integer newsNo;
	private String newsTypeNo;
	private Integer newsStateNo;
	private String newsTitle;
	private String newsContent;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date newsTime;
	private String adminAcct;
	
	
	public MemNewsVO() {
		
	}
	
	
	public MemNewsVO(Integer newsNo, String newsTypeNo, Integer newsStateNo, String newsTitle, String newsContent,
			Date newsTime, String adminAcct) {
		super();
		this.newsNo = newsNo;
		this.newsTypeNo = newsTypeNo;
		this.newsStateNo = newsStateNo;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsTime = newsTime;
		this.adminAcct = adminAcct;
	}




	public Integer getNewsNo() {
		return newsNo;
	}
	public void setNewsNo(Integer newsNo) {
		this.newsNo = newsNo;
	}
	public String getNewsTypeNo() {
		return newsTypeNo;
	}
	public void setNewsTypeNo(String newsTypeNo) {
		this.newsTypeNo = newsTypeNo;
	}
	public Integer getNewsStateNo() {
		return newsStateNo;
	}
	public void setNewsStateNo(Integer newsStateNo) {
		this.newsStateNo = newsStateNo;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public Date getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(Date newsTime) {
		this.newsTime = newsTime;
	}
	public String getAdminAcct() {
		return adminAcct;
	}
	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}
	
	
}
