package web.fam.service;

import java.sql.Connection;
import java.util.List;

import web.fam.dao.*;
import web.fam.entity.FamilyMemberVO;

public class FamilyMemberService {
	
	private FamilyMemberDAO_interface dao;
	
	public FamilyMemberService() {
		dao = new FamilyMemberJDBCDAO();
		
	}
	
	public FamilyMemberVO addFam(String memAcct, String famMemName) {
		FamilyMemberVO family_memberVO = new FamilyMemberVO();
		
		family_memberVO.setMemAcct(memAcct);
		family_memberVO.setFamMemName(famMemName);
		dao.insert(family_memberVO);
		
		return family_memberVO;
	}
	
	
	public void addFamsWithMemAcct(List<FamilyMemberVO> famMemVOList, Connection con) {
		for(FamilyMemberVO aFam: famMemVOList) {
			dao.insertFamsWithMemAcct(aFam, con);
		}
		
		
	}
	
	
	public FamilyMemberVO updateFam(String memAcct, String famMemName, Integer famMemNo) {
		FamilyMemberVO family_memberVO = new FamilyMemberVO();
		
		family_memberVO.setMemAcct(memAcct);
		family_memberVO.setFamMemName(famMemName);
		family_memberVO.setFamMemNo(famMemNo);
		
		dao.update(family_memberVO);
		
		return family_memberVO;
	}
	
	public void updateFamsWithMemAcct(String memAcct, List<String> famMemAryList, Connection con) {
		dao.updateFamsWithMemAcct(memAcct, famMemAryList, con);
		
	}
	
	public void deleteFam(Integer famMemNo) {
		dao.delete(famMemNo);
	}
	
//	public Integer deleteFamsWithMemAcct(String memAcct) {
//		Integer affectedRows = dao.deleteFamsWithMemAcct(memAcct);
//		return affectedRows;
//		
//	}
	
	
	public FamilyMemberVO getOneFam(Integer famMemNo) {
		return dao.findByPrimaryKey(famMemNo);
	}
	
	public List<FamilyMemberVO> getAll(){
		return dao.getAll();
	}
	
	
	

}
