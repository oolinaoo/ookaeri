package web.addr.entity;

public class AddressVO implements java.io.Serializable{
	private Integer addrNo;
	private String addrBuild;
	private Integer addrFloor;
	private Integer addrRoom;
	
	public AddressVO() {
		
	}
	
	public AddressVO(Integer addrNo, String addrBuild, Integer addrFloor, Integer addrRoom) {
		this.addrNo = addrNo;
		this.addrBuild = addrBuild;
		this.addrFloor = addrFloor;
		this.addrRoom = addrRoom;
	}

	public Integer getAddrNo() {
		return addrNo;
	}

	public void setAddrNo(Integer addrNo) {
		this.addrNo = addrNo;
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





