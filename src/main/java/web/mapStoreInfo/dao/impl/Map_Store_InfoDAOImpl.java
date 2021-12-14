package web.mapStoreInfo.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.mapStoreInfo.dao.Map_Store_InfoDAO;
import web.mapStoreInfo.entity.Map_Store_InfoVO;



// 此類別實作DAO interface，並將資料庫操作細節封裝起來
public class Map_Store_InfoDAOImpl implements Map_Store_InfoDAO {
	private static final String INSERT_STMT = "INSERT INTO MAP_STORE_INFO(MAP_STORE_NO, MAP_STORE_NAME, MAP_STORE_ADDR) VALUES (?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE MAP_STORE_INFO SET MAP_MSG_NO = ?, MAP_STORE_NAME = ?, MAP_STORE_ADDR = ?";
	private static final String DELETE_STMT = "DELETE FROM MAP_STORE_INFO WHERE MAP_STORE_NO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MAP_STORE_INFO WHERE MAP_STORE_NO = ?";
	private static final String GET_ALL = "SELECT * FROM MAP_STORE_INFO";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(Map_Store_InfoVO map_store_infovo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, map_store_infovo.getMAP_STORE_NO());
			pstmt.setString(2, map_store_infovo.getMAP_STORE_NAME());
			pstmt.setString(3, map_store_infovo.getMAP_STORE_ADDR());
			
			

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
	public void update(Map_Store_InfoVO map_store_infovo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, map_store_infovo.getMAP_STORE_NO());
			pstmt.setString(2, map_store_infovo.getMAP_STORE_NAME());
			pstmt.setString(3, map_store_infovo.getMAP_STORE_ADDR());
			
			
			

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
	public void delete(String MAP_STORE_NO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, MAP_STORE_NO);
			
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
	public Map_Store_InfoVO findByPK(String MAP_STORE_NO) {
		Map_Store_InfoVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, MAP_STORE_NO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_Store_InfoVO();
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMAP_STORE_NAME(rs.getString("MAP_STORE_NAME"));
				emp.setMAP_STORE_ADDR(rs.getString("MAP_STORE_ADDR"));
			
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
	public List<Map_Store_InfoVO> getAll() {
		List<Map_Store_InfoVO> empList = new ArrayList<>();
		Map_Store_InfoVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_Store_InfoVO();
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMAP_STORE_NAME(rs.getString("MAP_STORE_NAME"));
				emp.setMAP_STORE_ADDR(rs.getString("MAP_STORE_ADDR"));
				
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