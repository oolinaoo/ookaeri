package web.mapMessage.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.mapMessage.dao.Map_MessageDAO;
import web.mapMessage.entity.Map_MessageVO;





public class Map_MessageDAOlmpl implements Map_MessageDAO {
	private static final String INSERT_STMT = "INSERT INTO MAP_MESSAGE(MAP_MSG_NO, MAP_STORE_NO, MEM_ACCT, MAP_MSG_CONTENT, MAP_MSG_TIME, MAP_MSG_STATE) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE MAP_MESSAGE SET MAP_STORE_NO = ?, MEM_ACCT = ?, MAP_MSG_CONTENT = ?, MAP_MSG_TIME = ?, MAP_MSG_STATE = ? WHERE MAP_MSG_NO = ?";
	private static final String DELETE_STMT = "DELETE FROM MAP_MESSAGE WHERE MAP_MSG_NO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MAP_MESSAGE WHERE MAP_MSG_NO = ?";
	private static final String GET_ALL = "SELECT * FROM MAP_MESSAGE";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(Map_MessageVO map_message) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, map_message.getMAP_MSG_NO());
			pstmt.setString(2, map_message.getMAP_STORE_NO());
			pstmt.setString(3, map_message.getMEM_ACCT());
			pstmt.setString(4, map_message. getMAP_MSG_CONTENT());
			pstmt.setTimestamp(5, map_message.getMAP_MSG_TIME());
			pstmt.setInt(6, map_message.getMAP_MSG_STATE());
			

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
	public void update(Map_MessageVO map_message) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

		
			pstmt.setString(1, map_message.getMAP_STORE_NO());
			pstmt.setString(2, map_message.getMEM_ACCT());
			pstmt.setString(3, map_message. getMAP_MSG_CONTENT());
			pstmt.setTimestamp(4, map_message.getMAP_MSG_TIME());
			pstmt.setInt(5, map_message.getMAP_MSG_STATE());
			pstmt.setInt(6, map_message.getMAP_MSG_NO());

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
	public void delete(int MAP_MSG_NO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, MAP_MSG_NO);
			
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
	public Map_MessageVO findByPK(int MAP_MSG_NO) {
		Map_MessageVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, MAP_MSG_NO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_MessageVO();
				emp.setMAP_MSG_NO(rs.getInt("MAP_MSG_NO"));
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				emp.setMAP_MSG_TIME(rs.getTimestamp("MAP_MSG_TIME"));
				emp.setMAP_MSG_STATE(rs.getInt("MAP_MSG_STATE"));
				
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
	public List<Map_MessageVO> getAll() {
		List<Map_MessageVO> empList = new ArrayList<>();
		Map_MessageVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_MessageVO();
				emp.setMAP_MSG_NO(rs.getInt("MAP_MSG_NO"));
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				emp.setMAP_MSG_CONTENT(rs.getString("MAP_MSG_CONTENT"));
				emp.setMAP_MSG_TIME(rs.getTimestamp("MAP_MSG_TIME"));
				emp.setMAP_MSG_STATE(rs.getInt("MAP_MSG_STATE"));
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
	