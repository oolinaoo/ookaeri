package web.forumCollections.entity;

public class ForumCollectionsBo {
	
	private Integer forCollNo;
	private Integer forArtNo;
	private String memAcct;
	private Integer forTypeNo;
	private String forType;
	private String forArtTitle;
	private String forArtContent;
	
	public ForumCollectionsBo() {
	}

	public ForumCollectionsBo(Integer forCollNo, Integer forArtNo, String memAcct, Integer forTypeNo, String forType,
			String forArtTitle, String forArtContent) {
		super();
		this.forCollNo = forCollNo;
		this.forArtNo = forArtNo;
		this.memAcct = memAcct;
		this.forTypeNo = forTypeNo;
		this.forType = forType;
		this.forArtTitle = forArtTitle;
		this.forArtContent = forArtContent;
	}

	public Integer getForCollNo() {
		return forCollNo;
	}

	public void setForCollNo(Integer forCollNo) {
		this.forCollNo = forCollNo;
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

	public Integer getForTypeNo() {
		return forTypeNo;
	}

	public void setForTypeNo(Integer forTypeNo) {
		this.forTypeNo = forTypeNo;
	}

	public String getForType() {
		return forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
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

}
