package web.mapReport.dao.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.Util;
import web.mapReport.dao.Map_ReportDAO;
import web.mapReport.entity.Map_ReportVO;

// 此類別實作DAO interface，並將資料庫操作細節封裝起來
public class Map_ReportDAOImpl implements Map_ReportDAO {
	private static final String INSERT_STMT = "INSERT INTO MAP_REPORT(MAP_REPT_NO, MAP_REPT_CONTENT, MAP_MSG_NO, MEM_ACCT, MAP_REPT_STATE,ADMIN_ACCT) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE MAP_REPORT SET  MAP_REPT_CONTENT = ?, MAP_MSG_NO = ?, MEM_ACCT = ?, MAP_REPT_STATE = ?, ADMIN_ACCT = ? WHERE MAP_REPT_NO = ?";
	private static final String DELETE_STMT = "DELETE FROM MAP_REPORT WHERE MAP_REPT_NO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MAP_REPORT WHERE MAP_REPT_NO = ?";
	private static final String GET_ALL = "SELECT * FROM MAP_REPORT";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(Map_ReportVO map_reoort) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, map_reoort.getMAP_REPT_NO());
			pstmt.setString(2, map_reoort.getMAP_REPT_CONTENT());
			pstmt.setInt(3, map_reoort.getMAP_MSG_NO());
			pstmt.setString(4, map_reoort. getMEM_ACCT());
			pstmt.setInt(5, map_reoort.getMAP_REPT_STATE());
			pstmt.setString(6, map_reoort.getADMIN_ACCT());
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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
	public void update(Map_ReportVO map_reoort) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);


			
			pstmt.setString(1, map_reoort.getMAP_REPT_CONTENT());
			pstmt.setInt(2, map_reoort.getMAP_MSG_NO());
			pstmt.setString(3, map_reoort. getMEM_ACCT());
			pstmt.setInt(4, map_reoort.getMAP_REPT_STATE());
			pstmt.setString(5, map_reoort.getADMIN_ACCT());
			pstmt.setInt(6, map_reoort.getMAP_REPT_NO());
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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
	public void delete(int MAP_REPT_NO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, MAP_REPT_NO);
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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
	public Map_ReportVO findByPK(int MAP_REPT_NO) {
		Map_ReportVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, MAP_REPT_NO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_ReportVO();
				emp.setMAP_REPT_NO(rs.getInt("MAP_REPT_NO"));
				emp.setMAP_REPT_CONTENT(rs.getString("MAP_REPT_CONTENT"));
				emp.setMAP_MSG_NO(rs.getInt("MAP_MSG_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				emp.setMAP_REPT_STATE(rs.getInt("MAP_REPT_STATE"));
				emp.setADMIN_ACCT(rs.getString("ADMIN_ACCT"));
				
			}

		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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

		return emp;
	}

	@Override
	public List<Map_ReportVO> getAll() {
		List<Map_ReportVO> empList = new ArrayList<>();
		Map_ReportVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_ReportVO();
				emp.setMAP_REPT_NO(rs.getInt("MAP_REPT_NO"));
				emp.setMAP_REPT_CONTENT(rs.getString("MAP_REPT_CONTENT"));
				emp.setMAP_MSG_NO(rs.getInt("MAP_MSG_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				emp.setMAP_REPT_STATE(rs.getInt("MAP_REPT_STATE"));
				emp.setADMIN_ACCT(rs.getString("ADMIN_ACCT"));
				empList.add(emp);
			}

		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
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

		return empList;
	}
}