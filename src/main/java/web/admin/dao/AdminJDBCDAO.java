package web.admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.admin.entity.AdminVO;

public class AdminJDBCDAO implements AdminDAO_interface{
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.ADMIN(ADMIN_ACCT, ADMIN_PWD, ADMIN_NAME, ADMIN_POS, ADMIN_STATE, ADMIN_PHONE) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = 
			"UPDATE OKAERI.ADMIN set ADMIN_PWD=?, ADMIN_NAME=?, ADMIN_POS=?, ADMIN_STATE=?, ADMIN_PHONE=? where ADMIN_ACCT = ?";
	private static final String DELETE = 
			"DELETE FROM OKAERI.ADMIN where ADMIN_ACCT = ?";
	private static final String GET_ONE_STMT = 
			"SELECT ADMIN_ACCT, ADMIN_PWD, ADMIN_NAME, ADMIN_POS, ADMIN_STATE, ADMIN_PHONE FROM OKAERI.ADMIN where ADMIN_ACCT = ?";
	private static final String GET_ALL_STMT = 
			"SELECT ADMIN_ACCT, ADMIN_PWD, ADMIN_NAME, ADMIN_POS, ADMIN_STATE, ADMIN_PHONE FROM OKAERI.ADMIN order by ADMIN_STATE";
	
	@Override
	public void insert(AdminVO adminVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, adminVO.getAdminAcct());
			pstmt.setString(2, adminVO.getAdminPwd());
			pstmt.setString(3, adminVO.getAdminName());
			pstmt.setInt(4, adminVO.getAdminPos());
			pstmt.setInt(5, adminVO.getAdminState());
			pstmt.setString(6, adminVO.getAdminPhone());
			
			pstmt.executeUpdate();
			
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
	public void update(AdminVO adminVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, adminVO.getAdminPwd());
			pstmt.setString(2, adminVO.getAdminName());
			pstmt.setInt(3, adminVO.getAdminPos());
			pstmt.setInt(4, adminVO.getAdminState());
			pstmt.setString(5, adminVO.getAdminPhone());
			pstmt.setString(6, adminVO.getAdminAcct());

			pstmt.executeUpdate();
			
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
	public void delete(String admin_acct) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, admin_acct);
			pstmt.executeUpdate();
			
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
	public AdminVO findByPrimaryKey(String admin_acct) {
		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, admin_acct);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdminAcct(rs.getString("ADMIN_ACCT"));
				adminVO.setAdminPwd(rs.getString("ADMIN_PWD"));
				adminVO.setAdminName(rs.getString("ADMIN_NAME"));
				adminVO.setAdminPos(rs.getInt("ADMIN_POS"));
				adminVO.setAdminState(rs.getInt("ADMIN_STATE"));
				adminVO.setAdminPhone(rs.getString("ADMIN_PHONE"));
			}

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
		return adminVO;
		
	}
	@Override
	public List<AdminVO> getAll() {
		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO adminVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdminAcct(rs.getString("ADMIN_ACCT"));
				adminVO.setAdminPwd(rs.getString("ADMIN_PWD"));
				adminVO.setAdminName(rs.getString("ADMIN_NAME"));
				adminVO.setAdminPos(rs.getInt("ADMIN_POS"));
				adminVO.setAdminState(rs.getInt("ADMIN_STATE"));
				adminVO.setAdminPhone(rs.getString("ADMIN_PHONE"));
				list.add(adminVO);
			}

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
	
	public static void main(String[] args) {
		AdminJDBCDAO dao = new AdminJDBCDAO();
		
		//新增
//		AdminVO adminVO1 = new AdminVO();
//		adminVO1.setAdminAcct("Tina4321");
//		adminVO1.setAdminPwd("pwd12345");
//		adminVO1.setAdminName("王小明");
//		adminVO1.setAdminPos(0);
//		adminVO1.setAdminState(0);
//		adminVO1.setAdminPhone("0987654321");
//		dao.insert(adminVO1);
		
		//修改
//		AdminVO adminVO2 = new AdminVO();
//		adminVO2.setAdminAcct("Tina1234");
//		adminVO2.setAdminPwd("pwd54321");
//		adminVO2.setAdminName("王小明");
//		adminVO2.setAdminPos(0);
//		adminVO2.setAdminState(0);
//		adminVO2.setAdminPhone("0987654321");
//		dao.update(adminVO2);
		
		//刪除
//		dao.delete("Tina1234");
		
		//查詢
//		AdminVO adminVO3 = dao.findByPrimaryKey("Tina4321");
//		System.out.print( adminVO3.getAdminAcct() + ",");
//		System.out.print( adminVO3.getAdminPwd()+ ",");
//		System.out.print( adminVO3.getAdminName() + ",");
//		System.out.print( adminVO3.getAdminPos() + ",");
//		System.out.print( adminVO3.getAdminState() + ",");
//		System.out.println( adminVO3.getAdminPhone());
//		System.out.println("---------------------");
		
		//查詢多筆
		List<AdminVO> list = dao.getAll();
		for(AdminVO aAdmin : list) {
			System.out.print( aAdmin.getAdminAcct() + ",");
			System.out.print( aAdmin.getAdminPwd()+ ",");
			System.out.print( aAdmin.getAdminName() + ",");
			System.out.print( aAdmin.getAdminPos() + ",");
			System.out.print( aAdmin.getAdminState() + ",");
			System.out.print( aAdmin.getAdminPhone());
			System.out.println();
		}
		
	}


}
