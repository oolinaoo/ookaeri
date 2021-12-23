package web.v_map_message_map_report.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;

import web.v_map_message_map_report.dao.mapMessageMapReportDAO;
import web.v_map_message_map_report.entity.mapMessageMapReportVO;





public  class mapMessageMapReportDAOImpl implements mapMessageMapReportDAO
{
	
	
	private static final String GET_ALL = "SELECT * FROM V_MAP_MESSAGE_MAP_REPORT";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public List<mapMessageMapReportVO> getAll() {
		List<mapMessageMapReportVO> empList = new ArrayList<>();
		mapMessageMapReportVO emp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp = new mapMessageMapReportVO();
				emp.setMapMassageNo(rs.getInt("MAP_MSG_NO"));
				emp.setMapStoreNo(rs.getString("MAP_STORE_NO"));
				emp.setMemAcct(rs.getString("MEM_ACCT"));
				emp.setMapMessageContent(rs.getString("MAP_MSG_CONTENT"));
				emp.setMapMessageTime(rs.getTimestamp("MAP_MSG_TIME"));
				emp.setMapMessageState(rs.getInt("MAP_MSG_STATE"));
				emp.setMapReptState(rs.getInt("MAP_REPT_STATE"));
				emp.setMapReptContent(rs.getString("MAP_REPT_CONTENT"));
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
	

