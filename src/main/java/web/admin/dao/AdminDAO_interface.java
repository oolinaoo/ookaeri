package web.admin.dao;

import java.util.List;

import web.admin.entity.AdminVO;

public interface AdminDAO_interface {
	
	public void insert(AdminVO adminVO);
    public void update(AdminVO adminVO);
    public void delete(String adminAcct);
    public AdminVO findByPrimaryKey(String adminAcct);
    public List<AdminVO> getAll();
    
    

}
