package web.fam.entity;

public class FamilyMemberVO implements java.io.Serializable {
	private Integer famMemNo;
	private String memAcct;
	private String famMemName;
	
	public FamilyMemberVO() {
		
	}

	public FamilyMemberVO(Integer famMemNo, String memAcct, String famMemName) {
		super();
		this.famMemNo = famMemNo;
		this.memAcct = memAcct;
		this.famMemName = famMemName;
	}

	public Integer getFamMemNo() {
		return famMemNo;
	}

	public void setFamMemNo(Integer famMemNo) {
		this.famMemNo = famMemNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public String getFamMemName() {
		return famMemName;
	}

	public void setFamMemName(String famMemName) {
		this.famMemName = famMemName;
	}
	
	
	
	
}
