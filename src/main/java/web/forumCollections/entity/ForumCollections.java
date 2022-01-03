package web.forumCollections.entity;

public class ForumCollections {
	
	private Integer forCollNo;
	private Integer forArtNo;
	private String memAcct;
	
	public ForumCollections() {
	}

	public ForumCollections(Integer forCollNo, Integer forArtNo, String memAcct) {
		super();
		this.forCollNo = forCollNo;
		this.forArtNo = forArtNo;
		this.memAcct = memAcct;
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

}
