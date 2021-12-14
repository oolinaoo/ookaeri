package web.mapFavorite.dao;

import java.util.List;

import web.mapFavorite.entity.Map_My_FavoriteVO;

public interface Map_My_FavoriteDAO {
	// 此介面定義對資料庫的相關存取抽象方法
		void add(Map_My_FavoriteVO map_my_favorite);
		void update(Map_My_FavoriteVO map_my_favorite);
		void delete(String MAP_STORE_NO,String MEM_ACCT);
		Map_My_FavoriteVO findByPK(String MAP_STORE_NO);
		List<Map_My_FavoriteVO> getAll();

}
