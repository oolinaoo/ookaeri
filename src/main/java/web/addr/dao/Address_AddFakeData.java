package web.addr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Util;

public class Address_AddFakeData {
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.ADDRESS(ADDR_BUILD, ADDR_FLOOR, ADDR_ROOM) VALUES(?, ?, ?)";
	
	
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(INSERT_STMT);
			for(int i = 1; i <= 5; i++) {
				for(int j = 1; j <= 5; j++) {
					pstmt.setString(1, "A");
					pstmt.setInt(2, i);
					pstmt.setInt(3, j);
					pstmt.addBatch();
					
				}
			}

			pstmt.executeBatch();
			System.out.println("新增成功");

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
		
		
	}
	

}
