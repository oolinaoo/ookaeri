package web.mem.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import web.fam.entity.FamilyMemberVO;
import web.mem.dao.*;
import web.mem.entity.MemberVO;

public class MemberService {
	
	private MemberDAO_interface dao;
	
	public MemberService() {
		dao = new MemberJDBCDAO();
	}
	
	public MemberVO addMem(String memAcct, String memPwd, String memName, String memId, String memSex, String memEmail,
			Date memBirthday, Integer addrNo, byte[] memPhoto, String memPhone, Timestamp acctCreatetime){
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMemAcct(memAcct);
		memberVO.setMemPwd(memPwd);
		memberVO.setMemName(memName);
		memberVO.setMemId(memId);
		memberVO.setMemSex(memSex);
		memberVO.setMemEmail(memEmail);
		memberVO.setMemBirthday(memBirthday);
		memberVO.setAddrNo(addrNo);
		memberVO.setMemPhoto(memPhoto);
		memberVO.setMemPhone(memPhone);
		memberVO.setAcctCreatetime(acctCreatetime);
	    //memberVO.setMemState(memState);
		dao.insert(memberVO);
		
		return memberVO;
	}
	
	
	public MemberVO addMemWithFamMems(String memAcct, String memPwd, String memName, 
			String memId, String memSex, String memEmail,
			Date memBirthday, Integer addrNo, byte[] memPhoto, 
			String memPhone, Timestamp acctCreatetime, List<String> famMemAryList) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemAcct(memAcct);
		memberVO.setMemPwd(memPwd);
		memberVO.setMemName(memName);
		memberVO.setMemId(memId);
		memberVO.setMemSex(memSex);
		memberVO.setMemEmail(memEmail);
		memberVO.setMemBirthday(memBirthday);
		memberVO.setAddrNo(addrNo);
		memberVO.setMemPhoto(memPhoto);
		memberVO.setMemPhone(memPhone);
		memberVO.setAcctCreatetime(acctCreatetime);
		
		
		List<FamilyMemberVO> famMemVOList = new ArrayList<FamilyMemberVO>();
		for(String aFamName: famMemAryList) {
			FamilyMemberVO famMemberVO = new FamilyMemberVO();
			famMemberVO.setMemAcct(memAcct);
			famMemberVO.setFamMemName(aFamName);
			famMemVOList.add(famMemberVO);
		}
		
		dao.insertMemWithFams(memberVO, famMemVOList);
		
		return memberVO;
		
	}
	
	public MemberVO updateMem(String memPwd, String memName, String memId, String memSex, String memEmail,
			Date memBirthday, Integer addrNo, byte[] memPhoto, String memPhone, Integer memState, String memAcct) {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemPwd(memPwd);
		memberVO.setMemName(memName);
		memberVO.setMemId(memId);
		memberVO.setMemSex(memSex);
		memberVO.setMemEmail(memEmail);
		memberVO.setMemBirthday(memBirthday);
		memberVO.setAddrNo(addrNo);
		memberVO.setMemPhoto(memPhoto);
		memberVO.setMemPhone(memPhone);
		memberVO.setMemState(memState);
		memberVO.setMemAcct(memAcct);
		
		dao.update(memberVO);
		
		return memberVO;
	}
	
	
	public Integer updatePwd(String memAcct, String memPwd) {
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMemAcct(memAcct);
		memberVO.setMemPwd(memPwd);
		
		Integer affectedRows = dao.updatePwd(memberVO);
		
		return affectedRows;
	}
	
	
	public MemberVO updateMemWithFamMems(String memPwd, String memName, String memId, String memSex, String memEmail,
			Date memBirthday, Integer addrNo, byte[] memPhoto, String memPhone, Integer memState, String memAcct, List<String> famMemAryList) {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemPwd(memPwd);
		memberVO.setMemName(memName);
		memberVO.setMemId(memId);
		memberVO.setMemSex(memSex);
		memberVO.setMemEmail(memEmail);
		memberVO.setMemBirthday(memBirthday);
		memberVO.setAddrNo(addrNo);
		memberVO.setMemPhoto(memPhoto);
		memberVO.setMemPhone(memPhone);
		memberVO.setMemState(memState);
		memberVO.setMemAcct(memAcct);
		
		dao.updateMemWithFamMems(memberVO, famMemAryList);
		
		return memberVO;
	}
	
	
	
	
	public void deleteMem(String memAcct) {
		dao.delete(memAcct);
	}
	
	public MemberVO getOneMem(String memAcct) {
		return dao.findByPrimaryKey(memAcct);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}


	
	

}
