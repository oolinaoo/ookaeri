package web.admin.service;

import java.util.List;

import web.admin.dao.*;
import web.admin.entity.AdminVO;

public class AdminService {
	
	private AdminDAO_interface dao;
	
	public AdminService() {
		dao = new AdminJDBCDAO();
	}
	
	
	public AdminVO addAdmin(String adminAcct, String adminPwd, String adminName, Integer adminPos, 
			Integer adminState, String adminPhone) {
		
		AdminVO adminVO = new AdminVO();
		
		adminVO.setAdminAcct(adminAcct);
		adminVO.setAdminPwd(adminPwd);
		adminVO.setAdminName(adminName);
		adminVO.setAdminPos(adminPos);
		adminVO.setAdminState(adminState);
		adminVO.setAdminPhone(adminPhone);
		dao.insert(adminVO);
		
		return adminVO;
	}
	
	public AdminVO updateAdmin(String adminPwd, String adminName, Integer adminPos, 
			Integer adminState, String adminPhone, String adminAcct) {
		AdminVO adminVO = new AdminVO();
		
		adminVO.setAdminPwd(adminPwd);
		adminVO.setAdminName(adminName);
		adminVO.setAdminPos(adminPos);
		adminVO.setAdminState(adminState);
		adminVO.setAdminPhone(adminPhone);
		adminVO.setAdminAcct(adminAcct);
		
		dao.update(adminVO);
		
		return adminVO;
	}
	
	public Integer deleteAdmin(String adminAcct) {
		Integer affectedRows =  dao.delete(adminAcct);
		return affectedRows;
	}
	
	public AdminVO getOneAdmin(String adminAcct) {
		return dao.findByPrimaryKey(adminAcct);
	}
	
	public List<AdminVO> getAll(){
		return dao.getAll();
	}
	

}
