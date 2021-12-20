package web.fac.entity;

public class FacVO {
	private Integer facNo;
	private String facName;
	private Integer facMax;
	private Integer facState;
	private byte[] facPhoto;
	private String facAddr;
	private String facFloor;
	private Integer facAddrNo;
	
	private String facOpenDate;
	private String facOpenTime;
	private Integer startTime;
	private Integer endTime;
	
	private String allOpenDate;
	private String allOpenTime;
	
	public FacVO() {
		
	}

	public FacVO(Integer facNo, String facName, Integer facMax, Integer facState, byte[] facPhoto, String facAddr,
			String facFloor, Integer facAddrNo, String facOpenDate, String facOpenTime, Integer startTime,
			Integer endTime, String allOpenDate, String allOpenTime) {
		super();
		this.facNo = facNo;
		this.facName = facName;
		this.facMax = facMax;
		this.facState = facState;
		this.facPhoto = facPhoto;
		this.facAddr = facAddr;
		this.facFloor = facFloor;
		this.facAddrNo = facAddrNo;
		this.facOpenDate = facOpenDate;
		this.facOpenTime = facOpenTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.allOpenDate = allOpenDate;
		this.allOpenTime = allOpenTime;
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

	public String getFacAddr() {
		return facAddr;
	}

	public void setFacAddr(String facAddr) {
		this.facAddr = facAddr;
	}

	public String getFacFloor() {
		return facFloor;
	}

	public void setFacFloor(String facFloor) {
		this.facFloor = facFloor;
	}

	public Integer getFacAddrNo() {
		return facAddrNo;
	}

	public void setFacAddrNo(Integer facAddrNo) {
		this.facAddrNo = facAddrNo;
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

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getAllOpenDate() {
		return allOpenDate;
	}

	public void setAllOpenDate(String allOpenDate) {
		this.allOpenDate = allOpenDate;
	}

	public String getAllOpenTime() {
		return allOpenTime;
	}

	public void setAllOpenTime(String allOpenTime) {
		this.allOpenTime = allOpenTime;
	}
	
}
