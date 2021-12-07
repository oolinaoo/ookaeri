package web.addr.service;

import java.util.List;

import web.addr.dao.AddressDAO_interface;
import web.addr.dao.AddressJDBCDAO;
import web.addr.entity.AddressVO;

public class AddressService {
	
	private AddressDAO_interface dao;
	
	public AddressService() {
		dao = new AddressJDBCDAO();
		
	}
	
	public String addAddr(String addrBuild, Integer addrFloor, Integer addrRoom) {
		AddressVO addressVO = new AddressVO();
		
		addressVO.setAddrBuild(addrBuild);
		addressVO.setAddrFloor(addrFloor);
		addressVO.setAddrRoom(addrRoom);
		String key = dao.insert(addressVO);
		
		return key;
	}
	
	public AddressVO updateAddr(String addrBuild, Integer addrFloor, Integer addrRoom, Integer addrNo) {
		
		AddressVO addressVO = new AddressVO();
		
		addressVO.setAddrBuild(addrBuild);
		addressVO.setAddrFloor(addrFloor);
		addressVO.setAddrRoom(addrRoom);
		addressVO.setAddrNo(addrNo);
		
		dao.update(addressVO);
		
		return addressVO;
		
	}
	
	public Integer deleteAddr(Integer addrNo) {
		Integer affectedRows = dao.delete(addrNo);
		return affectedRows;
	}
	
	
	public AddressVO getOneAddr(Integer addrNo) {
		return dao.findByPrimaryKey(addrNo);
		
	}
	
	public List<AddressVO> getAll(){
		return dao.getAll();
	}
	


}
