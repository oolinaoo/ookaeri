package web.mapReport.dao;

import java.util.List;

import web.mapReport.entity.Map_ReportVO;

public interface Map_ReportDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(Map_ReportVO map_reoort);
	void update(Map_ReportVO map_reoort);
	void delete(int MAP_REPT_NO);
	Map_ReportVO findByPK(int MAP_REPT_NO);
	List<Map_ReportVO> getAll();
}