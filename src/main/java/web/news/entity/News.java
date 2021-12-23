package web.news.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import web.admin.entity.AdminVO;

public class News implements Serializable{
	
	private Integer newsNo;
	private String adminAcct;
	private String newsTypeNo;
	private Integer newsStateNo;
	private String newsTitle;
	private String newsContent;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date newsTime;
	private AdminVO admin;
	private List<AdminVO> adminList; 
	
	public News() {
	}

	public News(Integer newsNo, String adminAcct, String newsTypeNo, Integer newsStateNo, String newsTitle,
			String newsContent, Date newsTime) {
		super();
		this.newsNo = newsNo;
		this.adminAcct = adminAcct;
		this.newsTypeNo = newsTypeNo;
		this.newsStateNo = newsStateNo;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsTime = newsTime;
	}

	public Integer getNewsNo() {
		return newsNo;
	}

	public void setNewsNo(Integer newsNo) {
		this.newsNo = newsNo;
	}

	public String getAdminAcct() {
		return adminAcct;
	}

	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
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

	public AdminVO getAdmin() {
		return admin;
	}

	public void setAdmin(AdminVO admin) {
		this.admin = admin;
	}

	public List<AdminVO> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<AdminVO> adminList) {
		this.adminList = adminList;
	}
}
