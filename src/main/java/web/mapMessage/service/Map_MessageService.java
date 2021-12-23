package web.mapMessage.service;

import java.sql.Timestamp;
import java.util.List;

import web.mapMessage.dao.Map_MessageDAO;
import web.mapMessage.dao.impl.Map_MessageDAOlmpl;
import web.mapMessage.entity.Map_MessageVO;



public class Map_MessageService {
	
	private Map_MessageDAO dao = null;
	
	public Map_MessageService()
	{
		dao = new Map_MessageDAOlmpl();
	}
	public Map_MessageVO addMap_MessageVO(Integer MAP_MSG_NO,String MAP_STORE_NO, 
			String MEM_ACCT,String MAP_MSG_CONTENT,Timestamp MAP_MSG_TIME,Integer MAP_MSG_STATE)
	{
		Map_MessageVO map_messagevo = new Map_MessageVO();
		map_messagevo.setMAP_MSG_CONTENT(MAP_MSG_CONTENT);
		map_messagevo.setMAP_MSG_NO(MAP_MSG_NO);
		map_messagevo.setMAP_MSG_STATE(MAP_MSG_STATE);
		map_messagevo.setMAP_MSG_TIME(MAP_MSG_TIME);
		map_messagevo.setMAP_STORE_NO(MAP_STORE_NO);
		map_messagevo.setMEM_ACCT(MEM_ACCT);
		
		dao.add(map_messagevo);
		
		return map_messagevo;
		
	}
	public Map_MessageVO updateMap_MessageVO(Integer MAP_MSG_NO,String MAP_STORE_NO, 
			String MEM_ACCT,String MAP_MSG_CONTENT,Timestamp MAP_MSG_TIME,Integer MAP_MSG_STATE)
	{
		Map_MessageVO map_messagevo = new Map_MessageVO();
		map_messagevo.setMAP_MSG_CONTENT(MAP_MSG_CONTENT);
		map_messagevo.setMAP_MSG_NO(MAP_MSG_NO);
		map_messagevo.setMAP_MSG_STATE(MAP_MSG_STATE);
		map_messagevo.setMAP_MSG_TIME(MAP_MSG_TIME);
		map_messagevo.setMAP_STORE_NO(MAP_STORE_NO);
		map_messagevo.setMEM_ACCT(MEM_ACCT);
		
		dao.update(map_messagevo);
		
		return map_messagevo;
		
	}
	
	public List<Map_MessageVO> getAll() {
		return dao.getAll();
	}

}
