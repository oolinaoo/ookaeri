package web.facilities.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class FacilitiesBean {
	
//	@OneToMany
//	之後要加上 Association
//	
	
	@Id
	@Column (name = "FAC_NO")
	private Integer facNo;
	
	@Column (name = "FAC_NAME")
	private String facName;
	
	@Column (name = "FAC_OPEN_DATE")
	private String facOpenDate;
	
	@Column (name = "FAC_OPEN_TIME")
	private String facOpenTime;
	
	@Column (name = "FAC_MAX")
	private Integer facMax;
	
	@Column (name = "FAC_STATE")
	private Integer facState;
	
	@Column (name = "FAC_PHOTO")
	private byte[] facPhoto;

	@Override
	public String toString() {
		return "FacilitiesBean [facNo=" + facNo + ", facName=" + facName + ", facOpenDate=" + facOpenDate
				+ ", facOpenTime=" + facOpenTime + ", facMax=" + facMax + ", facState=" + facState + "]";
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
