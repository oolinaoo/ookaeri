package web.mapMessage.dao;


import java.util.List;

import web.mapMessage.entity.Map_MessageVO;

public interface Map_MessageDAO {

	void add(Map_MessageVO map_message);
	void update(Map_MessageVO map_message);
	void delete(int MAP_MSG_NO);
	Map_MessageVO findByPK(int MAP_MSG_NO);
	List<Map_MessageVO> getAll();
}