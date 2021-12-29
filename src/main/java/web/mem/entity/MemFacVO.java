package web.mem.entity;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemFacVO{
	
	private Integer facNo;
	private String facName;
	private Integer histNo;
	private String memAcct;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date histDate;
	private String histTime;

	public MemFacVO() {
		
	}

	public MemFacVO(Integer facNo, String facName, Integer histNo, String memAcct, Date histDate, String histTime) {
		super();
		this.facNo = facNo;
		this.facName = facName;
		this.histNo = histNo;
		this.memAcct = memAcct;
		this.histDate = histDate;
		this.histTime = histTime;
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

	public Integer getHistNo() {
		return histNo;
	}

	public void setHistNo(Integer histNo) {
		this.histNo = histNo;
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

	
	

}
