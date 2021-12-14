package web.pack.service;

import java.sql.Date;
import java.util.List;

import web.pack.dao.PackageDAO;
import web.pack.dao.impl.PackageDAOImpl;
import web.pack.entity.PackageVO;



public class PackageService {
	private PackageDAO dao;
	
	public PackageService() {
		dao = new PackageDAOImpl();
	}
	public PackageVO addPack(Integer addrNo,Date packArrived,
			String packLogistics, Integer packTypeNo, Integer packState) {
		PackageVO packvo = new PackageVO();
		
		packvo.setAddrNo(addrNo);
		packvo.setPackArrived(packArrived);
//		packvo.setPackReceived(packReceived);
		packvo.setPackLogistics(packLogistics);
		packvo.setPackTypeNo(packTypeNo);
		packvo.setPackState(packState);
		dao.add(packvo);
		
		return packvo;
	}
	
	public PackageVO updatePack(Integer packNo, Integer addrNo, Date packArrived, Date packReceived,
			String packLogistics, Integer packTypeNo, Integer packState) {
		PackageVO packvo = new PackageVO();
		
		packvo.setPackNo(packNo);
		packvo.setAddrNo(addrNo);
		packvo.setPackArrived(packArrived);
		packvo.setPackReceived(packReceived);
		packvo.setPackLogistics(packLogistics);
		packvo.setPackTypeNo(packTypeNo);
		packvo.setPackState(packState);
		dao.update(packvo);
		
		return packvo;
	}
	
	public void deletePack(Integer packNo) {
		dao.delete(packNo);
	}

	public PackageVO getOnePack(Integer packNo) {
		return dao.findByPK(packNo);
	}

	public List<PackageVO> getAll() {
		return dao.getAll();
	}

}
