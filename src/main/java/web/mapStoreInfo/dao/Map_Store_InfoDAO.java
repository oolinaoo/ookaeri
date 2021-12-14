package web.mapStoreInfo.dao;

import java.util.List;

import web.mapStoreInfo.entity.Map_Store_InfoVO;



public interface Map_Store_InfoDAO {
	// 此介面定義對資料庫的相關存取抽象方法
		void add(Map_Store_InfoVO map_store_infovo);
		void update(Map_Store_InfoVO map_store_infovo);
		void delete(String MAP_STORE_NO);
		Map_Store_InfoVO findByPK(String map_store_infovo);
		List<Map_Store_InfoVO> getAll();
		

}
