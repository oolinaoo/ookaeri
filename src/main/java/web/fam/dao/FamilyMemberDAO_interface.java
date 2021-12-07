package web.fam.dao;

import java.sql.Connection;
import java.util.List;

import web.fam.entity.FamilyMemberVO;

public interface FamilyMemberDAO_interface {
	public void insert(FamilyMemberVO family_memberVO);
    public void update(FamilyMemberVO family_memberVO);
    public void delete(Integer famMemNo);
    public FamilyMemberVO findByPrimaryKey(Integer famMemNo);
    public List<FamilyMemberVO> getAll();
    public void insertFamsWithMemAcct(FamilyMemberVO famMemberVO, Connection con);
    
}
