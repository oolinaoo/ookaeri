package web.forumReport.entity;

public class ForumReport {

	private Integer forReptNo;
	private Integer forArtNo;
	private Integer forMsgNo;
	private String memAcct;
	private String adminAcct;
	private Integer forReptState;
	private String forReptContent;
	
	public ForumReport() {
	}

	public ForumReport(Integer forReptNo, Integer forArtNo, Integer forMsgNo, String memAcct, String adminAcct,
			Integer forReptState, String forReptContent) {
		super();
		this.forReptNo = forReptNo;
		this.forArtNo = forArtNo;
		this.forMsgNo = forMsgNo;
		this.memAcct = memAcct;
		this.adminAcct = adminAcct;
		this.forReptState = forReptState;
		this.forReptContent = forReptContent;
	}

	public Integer getForReptNo() {
		return forReptNo;
	}

	public void setForReptNo(Integer forReptNo) {
		this.forReptNo = forReptNo;
	}

	public Integer getForArtNo() {
		return forArtNo;
	}

	public void setForArtNo(Integer forArtNo) {
		this.forArtNo = forArtNo;
	}

	public Integer getForMsgNo() {
		return forMsgNo;
	}

	public void setForMsgNo(Integer forMsgNo) {
		this.forMsgNo = forMsgNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
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
