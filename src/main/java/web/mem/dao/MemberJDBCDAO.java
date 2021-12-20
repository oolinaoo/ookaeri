package web.mem.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.fam.dao.FamilyMemberJDBCDAO;
import web.fam.entity.FamilyMemberVO;
import web.fam.service.FamilyMemberService;
import web.mem.entity.MemberVO;

public class MemberJDBCDAO implements MemberDAO_interface{
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.MEMBER(MEM_ACCT, MEM_PWD, MEM_NAME, MEM_ID, MEM_SEX, MEM_EMAIL, MEM_BIRTHDAY, ADDR_NO, MEM_PHOTO, MEM_PHONE, ACCT_CREATETIME) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	//不更新帳號創建時間
	private static final String UPDATE = 
			"UPDATE OKAERI.MEMBER set MEM_PWD=?, MEM_NAME=?, MEM_ID=?, MEM_SEX=?, MEM_EMAIL=?, MEM_BIRTHDAY=?, ADDR_NO=?, MEM_PHOTO=?, MEM_PHONE=?, MEM_STATE=? where MEM_ACCT = ?";
	private static final String DELETE = 
			"DELETE FROM OKAERI.MEMBER where MEM_ACCT = ?";
	private static final String GET_ONE_STMT = 
			"SELECT MEM_ACCT, MEM_PWD, MEM_NAME, MEM_ID, MEM_SEX, MEM_EMAIL, MEM_BIRTHDAY, ADDR_NO, MEM_PHOTO, MEM_PHONE, ACCT_CREATETIME, MEM_STATE FROM OKAERI.MEMBER where MEM_ACCT = ?";
	private static final String GET_ALL_STMT = 
			"SELECT MEM_ACCT, MEM_PWD, MEM_NAME, MEM_ID, MEM_SEX, MEM_EMAIL, MEM_BIRTHDAY, ADDR_NO, MEM_PHOTO, MEM_PHONE, ACCT_CREATETIME, MEM_STATE FROM OKAERI.MEMBER order by ADDR_NO";
	
	@Override
	public void insert(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memberVO.getMemAcct());
			pstmt.setString(2, memberVO.getMemPwd());
			pstmt.setString(3, memberVO.getMemName());
			pstmt.setString(4, memberVO.getMemId());
			pstmt.setString(5, memberVO.getMemSex());
			pstmt.setString(6, memberVO.getMemEmail());
			pstmt.setDate(7, memberVO.getMemBirthday());
			pstmt.setInt(8, memberVO.getAddrNo());
			pstmt.setBytes(9, memberVO.getMemPhoto());
			pstmt.setString(10, memberVO.getMemPhone());
			pstmt.setTimestamp(11, memberVO.getAcctCreatetime());
			//pstmt.setInt(12, memberVO.getMemState()); //資料庫有預設值，所以不用寫
			pstmt.executeUpdate();
			//System.out.println("新增成功");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	
	@Override
	public void insertMemWithFams(MemberVO memberVO, List<FamilyMemberVO> famMemVOList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, memberVO.getMemAcct());
			pstmt.setString(2, memberVO.getMemPwd());
			pstmt.setString(3, memberVO.getMemName());
			pstmt.setString(4, memberVO.getMemId());
			pstmt.setString(5, memberVO.getMemSex());
			pstmt.setString(6, memberVO.getMemEmail());
			pstmt.setDate(7, memberVO.getMemBirthday());
			pstmt.setInt(8, memberVO.getAddrNo());
			pstmt.setBytes(9, memberVO.getMemPhoto());
			pstmt.setString(10, memberVO.getMemPhone());
			pstmt.setTimestamp(11, memberVO.getAcctCreatetime());
			pstmt.executeUpdate();
			
//			FamilyMemberJDBCDAO dao = new FamilyMemberJDBCDAO();
//			for(FamilyMemberVO aFam : famMemVOList) {
//				//aFam.setMemAcct(memberVO.getMemAcct());
//				dao.insertFamsWithMemAcct(aFam, con);
//			}
			
			FamilyMemberService famMemSvc = new FamilyMemberService();
			famMemSvc.addFamsWithMemAcct(famMemVOList, con);
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("新增住戶帳號" + memberVO.getMemAcct() + "時,共有同住家人" + famMemVOList.size()
			+ "人同時被新增");
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			if(con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Member");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
	@Override
	public void update(MemberVO memberVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memberVO.getMemPwd());
			pstmt.setString(2, memberVO.getMemName());
			pstmt.setString(3, memberVO.getMemId());
			pstmt.setString(4, memberVO.getMemSex());
			pstmt.setString(5, memberVO.getMemEmail());
			pstmt.setDate(6, memberVO.getMemBirthday());
			pstmt.setInt(7, memberVO.getAddrNo());
			pstmt.setBytes(8, memberVO.getMemPhoto());
			pstmt.setString(9, memberVO.getMemPhone());
			pstmt.setInt(10, memberVO.getMemState());
			pstmt.setString(11, memberVO.getMemAcct());
			pstmt.executeUpdate();
			//System.out.println("更新成功");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	
	
	
	@Override
	public Integer updatePwd(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer affectedRows = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement("UPDATE OKAERI.MEMBER set MEM_PWD=? where MEM_ACCT = ?");
			
			pstmt.setString(1, memberVO.getMemPwd());
			pstmt.setString(2, memberVO.getMemAcct());
			affectedRows = pstmt.executeUpdate();
			//System.out.println("更新成功");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return affectedRows;
	}


	@Override
	public void updateMemWithFamMems(MemberVO memberVO, List<String> famMemAryList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, memberVO.getMemPwd());
			pstmt.setString(2, memberVO.getMemName());
			pstmt.setString(3, memberVO.getMemId());
			pstmt.setString(4, memberVO.getMemSex());
			pstmt.setString(5, memberVO.getMemEmail());
			pstmt.setDate(6, memberVO.getMemBirthday());
			pstmt.setInt(7, memberVO.getAddrNo());
			pstmt.setBytes(8, memberVO.getMemPhoto());
			pstmt.setString(9, memberVO.getMemPhone());
			pstmt.setInt(10, memberVO.getMemState());
			pstmt.setString(11, memberVO.getMemAcct());
			pstmt.executeUpdate();
			
			FamilyMemberService famMemSvc = new FamilyMemberService();
			famMemSvc.updateFamsWithMemAcct(memberVO.getMemAcct(), famMemAryList, con);
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("更新住戶帳號" + memberVO.getMemAcct() + "時,共有同住家人" + famMemAryList.size()
			+ "人同時被新增");
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			if(con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Member");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public Integer delete(String mem_acct) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer affectedRows = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mem_acct);
			
			affectedRows = pstmt.executeUpdate();
			//System.out.println("刪除成功");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return affectedRows;
	}

	
	

//	@Override
//	public void deleteMemWithFamMems(String memAcct, Connection con) {
//		PreparedStatement pstmt = null;
//		
//		try {
//			
//			pstmt = con.prepareStatement("DELETE FROM OKAERI.MEMBER where MEM_ACCT = ?");
//			pstmt.setString(1, memAcct);
//			pstmt.executeUpdate();
//				
//			
//		}catch (SQLException se) {
//			if( con!=null ) {
//				try {
//					System.err.print("Transaction is being ");
//					System.err.println("rolled back-由-Mem");
//					con.rollback();
//				}catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. "
//							+ excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		}finally {
//			if(pstmt != null) {
//				try {
//					pstmt.close();
//				}catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}


	@Override
	public MemberVO findByPrimaryKey(String mem_acct) {
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_acct);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemAcct(rs.getString("MEM_ACCT"));
				memberVO.setMemPwd(rs.getString("MEM_PWD"));
				memberVO.setMemName(rs.getString("MEM_NAME"));
				memberVO.setMemId(rs.getString("MEM_ID"));
				memberVO.setMemSex(rs.getString("MEM_SEX"));
				memberVO.setMemEmail(rs.getString("MEM_EMAIL"));
				memberVO.setMemBirthday(rs.getDate("MEM_BIRTHDAY"));
				memberVO.setAddrNo(rs.getInt("ADDR_NO"));
				memberVO.setMemPhoto(rs.getBytes("MEM_PHOTO"));
				memberVO.setMemPhone(rs.getString("MEM_PHONE"));
				memberVO.setAcctCreatetime(rs.getTimestamp("ACCT_CREATETIME"));
				memberVO.setMemState(rs.getInt("MEM_STATE"));
			}
			//System.out.println("查詢成功");

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memberVO;		
	}

	
	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemAcct(rs.getString("MEM_ACCT"));
				memberVO.setMemPwd(rs.getString("MEM_PWD"));
				memberVO.setMemName(rs.getString("MEM_NAME"));
				memberVO.setMemId(rs.getString("MEM_ID"));
				memberVO.setMemSex(rs.getString("MEM_SEX"));
				memberVO.setMemEmail(rs.getString("MEM_EMAIL"));
				memberVO.setMemBirthday(rs.getDate("MEM_BIRTHDAY"));
				memberVO.setAddrNo(rs.getInt("ADDR_NO"));
				memberVO.setMemPhoto(rs.getBytes("MEM_PHOTO"));
				memberVO.setMemPhone(rs.getString("MEM_PHONE"));
				memberVO.setAcctCreatetime(rs.getTimestamp("ACCT_CREATETIME"));
				memberVO.setMemState(rs.getInt("MEM_STATE"));
				list.add(memberVO);
			}
			//System.out.println("查詢成功");

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;	
		
	}
	
	
	public static byte[] getPictureByteArray(String path) throws IOException{
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
//	public static void main(String[] args) {
////		MemberJDBCDAO dao = new MemberJDBCDAO();
//		
////		//新增
////		MemberVO memberVO1 = new MemberVO();
////		memberVO1.setMemAcct("emily2");
////		memberVO1.setMemPwd("ppaasswword");
////		memberVO1.setMemName("王大明");
////		memberVO1.setMemId("B000000000");
////		memberVO1.setMemSex("男");
////		memberVO1.setMemEmail("emily1111@gmail.com");
////		memberVO1.setMemBirthday(java.sql.Date.valueOf("1997-11-19"));
////		memberVO1.setAddrNo(3);
////		try {
////			byte[] pic = getPictureByteArray("items/Gru.png");
////			memberVO1.setMemPhoto(pic);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		memberVO1.setMemPhone("0998765082");
////		memberVO1.setAcctCreatetime(Timestamp.valueOf(LocalDateTime.now()));
////		//memberVO1.setMemState(0);  //這個不需要寫，資料庫會有預設值
////		dao.insert(memberVO1);
//		
////		//修改
////		MemberVO memberVO2 = new MemberVO();
////		memberVO2.setMemAcct("gina8665");
////		memberVO2.setMemPwd("passworddd66666");
////		memberVO2.setMemName("居家瑜");
////		memberVO2.setMemId("B123456789");
////		memberVO2.setMemSex("女");
////		memberVO2.setMemEmail("gina123@gmail.com");
////		memberVO2.setMemBirthday(java.sql.Date.valueOf("1997-10-28"));
////		memberVO2.setAddrNo(1);
////		try {
////			byte[] pic = getPictureByteArray("items/FC_Barcelona.png");
////			memberVO2.setMemPhoto(pic);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		memberVO2.setMemPhone("0912345678");
////		memberVO2.setMemState(0);
////		dao.update(memberVO2);
//		
////		//刪除
////		dao.delete("emily1234");
//		
////		//查詢單筆
////		MemberVO memberVO3 = dao.findByPrimaryKey("abc");;
////		System.out.println(memberVO3 == null);
////		System.out.print(memberVO3.getMemAcct() + ",");
////		System.out.print(memberVO3.getMemPwd() + ",");
////		System.out.print(memberVO3.getMemName() + ",");
////		System.out.print(memberVO3.getMemId() + ",");
////		System.out.print(memberVO3.getMemSex() + ",");
////		System.out.print(memberVO3.getMemEmail() + ",");
////		System.out.print(memberVO3.getMemBirthday() + ",");
////		System.out.print(memberVO3.getAddrNo() + ",");
////		System.out.print(memberVO3.getMemPhoto() + ",");
////		System.out.print(memberVO3.getMemPhone() + ",");
////		System.out.print(memberVO3.getAcctCreatetime() + ",");
////		System.out.println(memberVO3.getMemState());
////		System.out.println("---------------------");
//		
////		//查詢全部
////		List<MemberVO> list = dao.getAll();
////		for(MemberVO aMem : list) {
////			System.out.print(aMem.getMemAcct() + ",");
////			System.out.print(aMem.getMemPwd() + ",");
////			System.out.print(aMem.getMemName() + ",");
////			System.out.print(aMem.getMemId() + ",");
////			System.out.print(aMem.getMemSex() + ",");
////			System.out.print(aMem.getMemEmail() + ",");
////			System.out.print(aMem.getMemBirthday() + ",");
////			System.out.print(aMem.getAddrNo() + ",");
////			System.out.print(aMem.getMemPhoto() + ",");
////			System.out.print(aMem.getMemPhone() + ",");
////			System.out.print(aMem.getAcctCreatetime() + ",");
////			System.out.println(aMem.getMemState());
////			
////		}
//		
//		
//	}
	
	
	

	


}
