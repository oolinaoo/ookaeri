package web.fam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.fam.entity.FamilyMemberVO;

public class FamilyMemberJDBCDAO implements FamilyMemberDAO_interface{
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.FAMILY_MEMBER(MEM_ACCT, FAM_MEM_NAME) VALUES(?, ?)";
	private static final String UPDATE = 
			"UPDATE OKAERI.FAMILY_MEMBER set MEM_ACCT=?, FAM_MEM_NAME=? where FAM_MEM_NO = ?";
	private static final String DELETE = 
			"DELETE FROM OKAERI.FAMILY_MEMBER where FAM_MEM_NO = ?";
	private static final String GET_ONE_STMT = 
			"SELECT FAM_MEM_NO, MEM_ACCT, FAM_MEM_NAME FROM OKAERI.FAMILY_MEMBER where FAM_MEM_NO = ?";
	private static final String GET_ALL_STMT = 
			"SELECT FAM_MEM_NO, MEM_ACCT, FAM_MEM_NAME FROM OKAERI.FAMILY_MEMBER order by FAM_MEM_NO";
	
	@Override
	public void insert(FamilyMemberVO family_memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, family_memberVO.getMemAcct());
			pstmt.setString(2, family_memberVO.getFamMemName());

			pstmt.executeUpdate();
			System.out.println("新增成功");
			
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
	public void insertFamsWithMemAcct(FamilyMemberVO famMemberVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, famMemberVO.getMemAcct());
			pstmt.setString(2, famMemberVO.getFamMemName());

			pstmt.executeUpdate();
			
			
		}catch (SQLException se) {
			if( con!=null ) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-FamMem");
					con.rollback();
				}catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void update(FamilyMemberVO family_memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, family_memberVO.getMemAcct());
			pstmt.setString(2, family_memberVO.getFamMemName());
			pstmt.setInt(3, family_memberVO.getFamMemNo());
			
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
	public void updateFamsWithMemAcct(String memAcct, List<String> famMemAryList, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			/*傳過來的famMemAryList可能為空陣列，
			 * 若為空陣列就直接將所有家人資料刪除，若資料庫中本來就沒有家人資料，執行這行指令也不會錯！
			 */
			/*傳過來的famMemAryList可能若不為空陣列，則先刪除資料庫中所有家人資料，
			 * 再將更新過的家人資料存入資料庫
			 */
			pstmt = con.prepareStatement("DELETE FROM OKAERI.FAMILY_MEMBER where MEM_ACCT = ?");
			pstmt.setString(1, memAcct);
			pstmt.executeUpdate();
			
			if(!famMemAryList.isEmpty()) {
				for(String aFamName : famMemAryList) {
					pstmt = con.prepareStatement(INSERT_STMT);
					pstmt.setString(1, memAcct);
					pstmt.setString(2, aFamName);
					pstmt.executeUpdate();
				}	
			}
			
		}catch (SQLException se) {
			if( con!=null ) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-FamMem");
					con.rollback();
				}catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(Integer fam_mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, fam_mem_no);
			
			pstmt.executeUpdate();
			System.out.println("刪除成功");
			
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
	public FamilyMemberVO findByPrimaryKey(Integer fam_mem_no) {
		FamilyMemberVO family_memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, fam_mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				family_memberVO = new FamilyMemberVO();
				family_memberVO.setFamMemNo(fam_mem_no);
				family_memberVO.setMemAcct(rs.getString("MEM_ACCT"));
				family_memberVO.setFamMemName(rs.getString("FAM_MEM_NAME"));
				
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
		return family_memberVO;		
	}

	@Override
	public List<FamilyMemberVO> getAll() {
		List<FamilyMemberVO> list = new ArrayList<FamilyMemberVO>();
		FamilyMemberVO family_memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				family_memberVO = new FamilyMemberVO();
				family_memberVO.setFamMemNo(rs.getInt("FAM_MEM_NO"));
				family_memberVO.setMemAcct(rs.getString("MEM_ACCT"));
				family_memberVO.setFamMemName(rs.getString("FAM_MEM_NAME"));
				list.add(family_memberVO);
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
	
	

	
	
//	public static void main(String[] args) {
//		FamilyMemberJDBCDAO dao = new FamilyMemberJDBCDAO();
//		
//		//新增
//		FamilyMemberVO family_memberVO1 = new FamilyMemberVO();
//		family_memberVO1.setMemAcct("gina8665");
//		family_memberVO1.setFamMemName("居小魚");
//		dao.insert(family_memberVO1);
//		
//		//修改
//		FamilyMemberVO family_memberVO2 = new FamilyMemberVO();
//		family_memberVO2.setMemAcct("gina8665");
//		family_memberVO2.setFamMemName("居居居");
//		family_memberVO2.setFamMemNo(1);
//		dao.update(family_memberVO2);
//		
//		//刪除
//		dao.delete(2);
//		
//		//查詢單筆
//		FamilyMemberVO family_memberVO3 = dao.findByPrimaryKey(1);
//		System.out.print(family_memberVO3.getFamMemNo() + ",");
//		System.out.print(family_memberVO3.getMemAcct() + ",");
//		System.out.println(family_memberVO3.getFamMemName());
//		System.out.println("---------------------");
//		
//		List<FamilyMemberVO> list = dao.getAll();
//		for(FamilyMemberVO aFamily_Member : list) {
//			System.out.print(aFamily_Member.getFamMemNo() + ",");
//			System.out.print(aFamily_Member.getMemAcct() + ",");
//			System.out.println(aFamily_Member.getFamMemName());	
//		}
//		
//	}
	
	
	
}
