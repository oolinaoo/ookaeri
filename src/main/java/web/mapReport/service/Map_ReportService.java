package web.mapReport.service;

import java.sql.Timestamp;
import java.util.List;

import web.mapReport.dao.Map_ReportDAO;
import web.mapReport.dao.Impl.Map_ReportDAOImpl;
import web.mapReport.entity.Map_ReportVO;

public class Map_ReportService {
	
	private Map_ReportDAO dao =null;

	public Map_ReportService() {
		dao= new Map_ReportDAOImpl();
	}
	public Map_ReportVO addMap_ReportDAO(Integer MAP_REPT_NO, String MAP_REPT_CONTENT, Integer MAP_MSG_NO, String MEM_ACCT,
			Integer MAP_REPT_STATE, String ADMIN_ACCT)
	{
		Map_ReportVO map_rept = new Map_ReportVO();
		map_rept.setADMIN_ACCT(ADMIN_ACCT);
		map_rept.setMAP_MSG_NO(MAP_MSG_NO);
		map_rept.setMAP_REPT_CONTENT(MAP_REPT_CONTENT);
		map_rept.setMAP_REPT_NO(MAP_REPT_NO);
		map_rept.setMAP_REPT_STATE(MAP_REPT_STATE);
		map_rept.setMEM_ACCT(MEM_ACCT);
		
		dao.add(map_rept);
		return map_rept;
	}
	
	
	public List<Map_ReportVO> getAll() {
		return dao.getAll();
	}
	

}
