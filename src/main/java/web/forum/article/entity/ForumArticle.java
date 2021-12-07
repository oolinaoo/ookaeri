package web.forum.article.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FORUM_ARTICLE")
public class ForumArticle implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FOR_ART_NO")
	private Integer forArtNo;
	
	@Column(name="FOR_TYPE_NO")
	private Integer forTypeNo;
	
	@Column(name="FOR_STATE_NO")
	private Integer artStateNo;
	
	@Column(name="MEM_ACCT")
	private String memAcct;
	
	@Column(name="FOR_ART_POSTTIME")
	private Timestamp forArtPosttime;
	
	@Column(name="FOR_ART_EDITTIME")
	private Timestamp forArtEdittime;
	
	@Column(name="FOR_ART_TITLE")
	private String forArtTitle;
	
	@Column(name="FOR_ART_CONTENT")
	private String forArtContent;
	
	@Column(name="FOR_ART_VIEW")
	private Integer forArtView;

	@Override
	public String toString() {
		return "ForumArticleVO [forArtNo=" + forArtNo + ", forTypeNo=" + forTypeNo + ", artStateNo=" + artStateNo
				+ ", memAcct=" + memAcct + ", forArtPosttime=" + forArtPosttime + ", forArtEdittime=" + forArtEdittime
				+ ", forArtTitle=" + forArtTitle + ", forArtContent=" + forArtContent + ", forArtView=" + forArtView
				+ "]";
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
	
}
