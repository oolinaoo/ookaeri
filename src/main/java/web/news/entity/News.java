package web.news.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NEWS")
public class News implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NEWS_NO")
	private Integer newsNo;
	
	@Column(name = "ADMIN_ACCT")
	private String adminAcct;
	
	@Column(name = "NEWS_TYPE_NO")
	private Integer newsTypeNo;
	
	@Column(name = "NEWS_STATE_NO")
	private Integer newsStateNo;
	
	@Column(name = "NEWS_TITLE")
	private String newsTitle;
	
	@Column(name = "NEWS_CONTENT")
	private String newsContent;
	
	@Column(name = "NEWS_TIME")
	private Date newsTime;

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

	public Integer getNewsTypeNo() {
		return newsTypeNo;
	}

	public void setNewsTypeNo(Integer newsTypeNo) {
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
}
