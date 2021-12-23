package web.mem.entity;

import java.io.Serializable;

public class MemPackVO implements Serializable{
	
	private String memAcct;
	private Integer packNo;
	private Integer packTypeNo;
	private Integer packState;
	private Integer addrNo;

	
	
	public MemPackVO() {
		
	}


	
	
	public MemPackVO(String memAcct, Integer packNo, Integer packTypeNo, Integer packState, Integer addrNo) {
		super();
		this.memAcct = memAcct;
		this.packNo = packNo;
		this.packTypeNo = packTypeNo;
		this.packState = packState;
		this.addrNo = addrNo;
	}




	public String getMemAcct() {
		return memAcct;
	}



	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}



	public Integer getPackNo() {
		return packNo;
	}



	public void setPackNo(Integer packNo) {
		this.packNo = packNo;
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



	public Integer getAddrNo() {
		return addrNo;
	}



	public void setAddrNo(Integer addrNo) {
		this.addrNo = addrNo;
	}
	
	
	
	
	
	

}
