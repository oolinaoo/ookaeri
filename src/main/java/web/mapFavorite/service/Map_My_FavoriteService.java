package web.mapFavorite.service;

import java.util.List;

import web.mapFavorite.dao.Map_My_FavoriteDAO;
import web.mapFavorite.dao.impl.Map_My_FavoriteDAOImpl;
import web.mapFavorite.entity.Map_My_FavoriteVO;

public class Map_My_FavoriteService {
	
	private Map_My_FavoriteDAO dao = null;

	public Map_My_FavoriteService() {
		
		dao = new Map_My_FavoriteDAOImpl();
		
	}
	public Map_My_FavoriteVO addMap_My_FavoriteVO(String MAP_STORE_NO,String MEM_ACCT)
	{

		Map_My_FavoriteVO map_my_favoritevo = new Map_My_FavoriteVO();
		map_my_favoritevo.setMEM_ACCT(MEM_ACCT);
		map_my_favoritevo.setMAP_STORE_NO(MAP_STORE_NO);
		
		dao.add(map_my_favoritevo);
		
		return map_my_favoritevo;
		
	}
	public void deleteMap_My_FavoriteVO(String MAP_STORE_NO,String MEM_ACCT)
	{
		dao.delete(MAP_STORE_NO,MEM_ACCT);
	}
	public List<Map_My_FavoriteVO> getAll() {
		return dao.getAll();
	}
	

}
