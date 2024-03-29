package web.mem.dao;
import java.sql.Connection;
import java.util.*;

import web.fam.entity.FamilyMemberVO;
import web.mem.entity.MemberVO;

public interface MemberDAO_interface {
	public void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public Integer updatePwd(MemberVO memberVO);
    public Integer delete(String memAcct);
    public MemberVO findByPrimaryKey(String memAcct);
    public List<MemberVO> getAll();
    public void insertMemWithFams(MemberVO memberVO, List<FamilyMemberVO> list);
    public void updateMemWithFamMems(MemberVO memberVO, List<String> list);
    //public void deleteMemWithFamMems(String memAcct, Connection con);
}
