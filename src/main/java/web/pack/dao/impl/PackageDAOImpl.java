package web.pack.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import web.pack.dao.PackageDAO;
import web.pack.entity.PackageVO;
import util.Util;

public class PackageDAOImpl implements PackageDAO {
	private static final String INSERT_STMT = "INSERT INTO PACKAGE(addr_no, pack_arrived, pack_logistics, pack_type_no, pack_state) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE PACKAGE SET  pack_arrived = ?, pack_received = ?, pack_logistics = ?, pack_type_no = ?, pack_state = ? WHERE pack_no = ? AND addr_no = ?";
	private static final String DELETE_STMT = "DELETE FROM PACKAGE WHERE pack_no = ?";
	private static final String FIND_BY_PK = "SELECT * FROM PACKAGE WHERE pack_no = ?";
	private static final String GET_ALL = "SELECT * FROM PACKAGE";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	} 

	@Override
	public void add(PackageVO packagevo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, packagevo.getAddrNo());
			pstmt.setDate(2, packagevo.getPackArrived());
//			pstmt.setDate(3, packagevo.getPackReceived());
			pstmt.setString(3, packagevo.getPackLogistics());
			pstmt.setInt(4, packagevo.getPackTypeNo());
			pstmt.setInt(5, packagevo.getPackState());

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
	public void update(PackageVO packagevo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			
			pstmt.setDate(1, packagevo.getPackArrived());
			pstmt.setDate(2, packagevo.getPackReceived());
			pstmt.setString(3, packagevo.getPackLogistics());
			pstmt.setInt(4, packagevo.getPackTypeNo());
			pstmt.setInt(5, packagevo.getPackState());
			pstmt.setInt(6, packagevo.getPackNo());
			pstmt.setInt(7, packagevo.getAddrNo());

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
	public void delete(int packNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, packNo);
			
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
	public PackageVO findByPK(int packNo) {
		PackageVO pack = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, packNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pack = new PackageVO();
				pack.setPackNo(rs.getInt("PACK_NO"));
				pack.setAddrNo(rs.getInt("ADDR_NO"));
				pack.setPackArrived(rs.getDate("PACK_ARRIVED"));
				pack.setPackReceived(rs.getDate("PACK_RECEIVED"));
				pack.setPackLogistics(rs.getString("PACK_LOGISTICS"));
				pack.setPackTypeNo(rs.getInt("PACK_TYPE_NO"));
				pack.setPackState(rs.getInt("PACK_STATE"));
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

		return pack;
	}

	@Override
	public List<PackageVO> getAll() {
		List<PackageVO> packList = new ArrayList<>();
		PackageVO pack = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pack = new PackageVO();
				pack.setPackNo(rs.getInt("PACK_NO"));
				pack.setAddrNo(rs.getInt("ADDR_NO"));
				pack.setPackArrived(rs.getDate("PACK_ARRIVED"));
				pack.setPackReceived(rs.getDate("PACK_RECEIVED"));
				pack.setPackLogistics(rs.getString("PACK_LOGISTICS"));
				pack.setPackTypeNo(rs.getInt("PACK_TYPE_NO"));
				pack.setPackState(rs.getInt("PACK_STATE"));
				packList.add(pack);
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
		return packList;
	}
}
