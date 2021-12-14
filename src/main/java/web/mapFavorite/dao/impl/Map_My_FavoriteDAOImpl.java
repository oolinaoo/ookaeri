package web.mapFavorite.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;
import web.mapFavorite.dao.Map_My_FavoriteDAO;
import web.mapFavorite.entity.Map_My_FavoriteVO;




public class Map_My_FavoriteDAOImpl implements Map_My_FavoriteDAO {
	
	private static final String INSERT_STMT = "INSERT INTO MAP_MY_FAVORITE(MAP_STORE_NO, MEM_ACCT) VALUES (?, ?)";
	private static final String UPDATE_STMT = "UPDATE MAP_MY_FAVORITE SET MAP_STORE_NO = ?, MEM_ACCT = ?";
	private static final String DELETE_STMT = "DELETE FROM MAP_MY_FAVORITE WHERE MAP_STORE_NO = ? AND MEM_ACCT = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MAP_MY_FAVORITE WHERE MAP_STORE_NO = ?";
	private static final String GET_ALL = "SELECT * FROM MAP_MY_FAVORITE";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(Map_My_FavoriteVO map_my_favorite) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, map_my_favorite.getMAP_STORE_NO());
			pstmt.setString(2, map_my_favorite.getMEM_ACCT());
			
			

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
	public void update(Map_My_FavoriteVO map_my_favorite) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, map_my_favorite.getMAP_STORE_NO());
			pstmt.setString(2, map_my_favorite.getMEM_ACCT());
			
			
			

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
	public void delete(String MAP_STORE_NO ,String MEM_ACCT) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, MAP_STORE_NO);
			
			pstmt.setString(2, MEM_ACCT);
			
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
	public Map_My_FavoriteVO findByPK(String MAP_STORE_NO) {
		Map_My_FavoriteVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, MAP_STORE_NO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_My_FavoriteVO();
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
				
				
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
	public List<Map_My_FavoriteVO> getAll() {
		List<Map_My_FavoriteVO> empList = new ArrayList<>();
		Map_My_FavoriteVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new Map_My_FavoriteVO();
				emp.setMAP_STORE_NO(rs.getString("MAP_STORE_NO"));
				emp.setMEM_ACCT(rs.getString("MEM_ACCT"));
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
