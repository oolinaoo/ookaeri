package web.fac.entity;

public class FacVO {
	private Integer facNo;
	private String facName;
	private String facOpenDate;
	private String facOpenTime;
	private Integer facMax;
	private Integer facState;
	private byte[] facPhoto;
	
	public FacVO() {
		
	}

	public FacVO(Integer facNo, String facName, String facOpenDate, String facOpenTime, Integer facMax,
			Integer facState, byte[] facPhoto) {
		this.facNo = facNo;
		this.facName = facName;
		this.facOpenDate = facOpenDate;
		this.facOpenTime = facOpenTime;
		this.facMax = facMax;
		this.facState = facState;
		this.facPhoto = facPhoto;
	}

	public Integer getFacNo() {
		return facNo;
	}

	public void setFacNo(Integer facNo) {
		this.facNo = facNo;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public String getFacOpenDate() {
		return facOpenDate;
	}

	public void setFacOpenDate(String facOpenDate) {
		this.facOpenDate = facOpenDate;
	}

	public String getFacOpenTime() {
		return facOpenTime;
	}

	public void setFacOpenTime(String facOpenTime) {
		this.facOpenTime = facOpenTime;
	}

	public Integer getFacMax() {
		return facMax;
	}

	public void setFacMax(Integer facMax) {
		this.facMax = facMax;
	}

	public Integer getFacState() {
		return facState;
	}

	public void setFacState(Integer facState) {
		this.facState = facState;
	}

	public byte[] getFacPhoto() {
		return facPhoto;
	}

	public void setFacPhoto(byte[] facPhoto) {
		this.facPhoto = facPhoto;
	}
}
