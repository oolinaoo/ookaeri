package web.pack.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class PackageVO {
	
	private Integer packNo;
	private Integer addrNo;
	private Date packArrived;
	private Date packReceived;
	private String packLogistics;
	private Integer packTypeNo;
	private Integer packState;
	
	public PackageVO() {
		
	}
	public PackageVO(Integer packNo, Integer addrNo, Date packArrived, Date packReceived,
			String packLogistics, Integer packTypeNo, Integer packState) {
		super();
		this.packNo = packNo;
		this.addrNo = addrNo;
		this.packArrived = packArrived;
		this.packReceived = packReceived;
		this.packLogistics = packLogistics;
		this.packTypeNo = packTypeNo;
		this.packState = packState;
	}
	
	public Integer getPackNo() {
		return packNo;
	}
	public void setPackNo(Integer packNo) {
		this.packNo = packNo;
	}
	public Integer getAddrNo() {
		return addrNo;
	}
	public void setAddrNo(Integer addrNo) {
		this.addrNo = addrNo;
	}
	public Date getPackArrived() {
		return packArrived;
	}
	public void setPackArrived(Date packArrived) {
		this.packArrived = packArrived;
	}
	public Date getPackReceived() {
		return packReceived;
	}
	public void setPackReceived(Date packReceived) {
		this.packReceived = packReceived;
	}
	public String getPackLogistics() {
		return packLogistics;
	}
	public void setPackLogistics(String packLogistics) {
		this.packLogistics = packLogistics;
	}
	public Integer getPackTypeNo() {
		return packTypeNo;
	}
	public void setPackTypeNo(Integer packTypeNo) {
		this.packTypeNo = packTypeNo;
	}
	public Integer getPackState() {
		return packState;
	}
	public void setPackState(Integer packState) {
		this.packState = packState;
	}
	
	

	
	
	
	
	
}
