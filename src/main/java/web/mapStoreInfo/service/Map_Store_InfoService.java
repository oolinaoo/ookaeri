package web.mapStoreInfo.service;

import java.util.List;

import web.mapStoreInfo.dao.Map_Store_InfoDAO;
import web.mapStoreInfo.dao.impl.Map_Store_InfoDAOImpl;
import web.mapStoreInfo.entity.Map_Store_InfoVO;



public class Map_Store_InfoService {
	
	private Map_Store_InfoDAO dao =null;
	
	public Map_Store_InfoService() {
		
		dao =new Map_Store_InfoDAOImpl();
	}
	public List<Map_Store_InfoVO> getAll() {
		return dao.getAll();
	}

}
