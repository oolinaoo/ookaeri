package web.fachist.entity;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FachistVO {
	private Integer histNo;
	private Integer facNo;
	private String memAcct;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date histDate;
	private String histTime;
	private Integer histAmount;
	private String facName;
	private String addrBuild;
	private Integer addrFloor;
	private Integer addrRoom;
	
	public FachistVO() {
		super();
	}

	public FachistVO(Integer histNo, Integer facNo, String memAcct, Date histDate, String histTime, Integer histAmount,
			String facName, String addrBuild, Integer addrFloor, Integer addrRoom) {
		super();
		this.histNo = histNo;
		this.facNo = facNo;
		this.memAcct = memAcct;
		this.histDate = histDate;
		this.histTime = histTime;
		this.histAmount = histAmount;
		this.facName = facName;
		this.addrBuild = addrBuild;
		this.addrFloor = addrFloor;
		this.addrRoom = addrRoom;
	}

	@Override
	public String toString() {
		return "FachistVO [histNo=" + histNo + ", facNo=" + facNo + ", memAcct=" + memAcct + ", histDate=" + histDate
				+ ", histTime=" + histTime + ", histAmount=" + histAmount + ", facName=" + facName + ", addrBuild="
				+ addrBuild + ", addrFloor=" + addrFloor + ", addrRoom=" + addrRoom + "]";
	}

	public Integer getHistNo() {
		return histNo;
	}

	public void setHistNo(Integer histNo) {
		this.histNo = histNo;
	}

	public Integer getFacNo() {
		return facNo;
	}

	public void setFacNo(Integer facNo) {
		this.facNo = facNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public Date getHistDate() {
		return histDate;
	}

	public void setHistDate(Date histDate) {
		this.histDate = histDate;
	}

	public String getHistTime() {
		return histTime;
	}

	public void setHistTime(String histTime) {
		this.histTime = histTime;
	}

	public Integer getHistAmount() {
		return histAmount;
	}

	public void setHistAmount(Integer histAmount) {
		this.histAmount = histAmount;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public String getAddrBuild() {
		return addrBuild;
	}

	public void setAddrBuild(String addrBuild) {
		this.addrBuild = addrBuild;
	}

	public Integer getAddrFloor() {
		return addrFloor;
	}

	public void setAddrFloor(Integer addrFloor) {
		this.addrFloor = addrFloor;
	}

	public Integer getAddrRoom() {
		return addrRoom;
	}

	public void setAddrRoom(Integer addrRoom) {
		this.addrRoom = addrRoom;
	}
	
	
}
