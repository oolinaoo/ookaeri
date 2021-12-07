package web.addr.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import util.Util;
import web.addr.entity.AddressVO;

public class AddressJDBCDAO implements AddressDAO_interface{
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.ADDRESS(ADDR_BUILD, ADDR_FLOOR, ADDR_ROOM) VALUES(?, ?, ?)";
	private static final String UPDATE = 
			"UPDATE OKAERI.ADDRESS set ADDR_BUILD=?, ADDR_FLOOR=?, ADDR_ROOM=? where ADDR_NO = ?";
	private static final String DELETE = 
			"DELETE FROM OKAERI.ADDRESS where ADDR_NO = ?";
	private static final String GET_ONE_STMT = 
			"SELECT ADDR_NO, ADDR_BUILD, ADDR_FLOOR, ADDR_ROOM FROM OKAERI.ADDRESS where ADDR_NO = ?";
	private static final String GET_ALL_STMT = 
			"SELECT ADDR_NO, ADDR_BUILD, ADDR_FLOOR, ADDR_ROOM FROM OKAERI.ADDRESS order by ADDR_NO";
	
	@Override
	public String insert(AddressVO addressVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			String[] cols = {"ADDR_NO"};
			
			pstmt = con.prepareStatement(INSERT_STMT ,cols);
			//pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, addressVO.getAddrBuild());
			pstmt.setInt(2, addressVO.getAddrFloor());
			pstmt.setInt(3, addressVO.getAddrRoom());
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getString(1); // 只支援欄位索引值取得自增主鍵值
				//System.out.println("自增主鍵值 = " + key + "(剛新增成功的員工編號)");
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}

			rs.close();

			
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "
					+ ce.getMessage());
		} catch (SQLException se) {
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
		return key;
		
	}

	@Override
	public void update(AddressVO addressVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, addressVO.getAddrBuild());
			pstmt.setInt(2, addressVO.getAddrFloor());
			pstmt.setInt(3, addressVO.getAddrRoom());
			pstmt.setInt(4, addressVO.getAddrNo());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "
					+ ce.getMessage());
		} catch (SQLException se) {
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
	public Integer delete(Integer addr_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer affectedRows = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, addr_no);
			
			affectedRows = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "
					+ ce.getMessage());
		} catch (SQLException se) {
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
		return affectedRows;
		
	}

	@Override
	public AddressVO findByPrimaryKey(Integer addr_no) {
		AddressVO addressVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, addr_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				addressVO = new AddressVO();
				addressVO.setAddrNo(rs.getInt("ADDR_NO"));
				addressVO.setAddrBuild(rs.getString("ADDR_BUILD"));
				addressVO.setAddrFloor(rs.getInt("ADDR_FLOOR"));
				addressVO.setAddrRoom(rs.getInt("ADDR_ROOM"));
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "
					+ ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return addressVO;
	}

	@Override
	public List<AddressVO> getAll() {
		List<AddressVO> list = new ArrayList<AddressVO>();
		AddressVO addressVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				addressVO = new AddressVO();
				addressVO.setAddrNo(rs.getInt("ADDR_NO"));
				addressVO.setAddrBuild(rs.getString("ADDR_BUILD"));
				addressVO.setAddrFloor(rs.getInt("ADDR_FLOOR"));
				addressVO.setAddrRoom(rs.getInt("ADDR_ROOM"));
				list.add(addressVO);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "
					+ ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
//		AddressJDBCDAO dao = new AddressJDBCDAO();
//		
//		//新增
//		AddressVO addressVO1 = new AddressVO();
//		addressVO1.setAddrBuild("A");
//		addressVO1.setAddrFloor(1);
//		addressVO1.setAddrRoom(2);
//		dao.insert(addressVO1);
//		
//		//修改
//		AddressVO addressVO2 = new AddressVO();
//		addressVO2.setAddrNo(2);
//		addressVO2.setAddrBuild("B");
//		addressVO2.setAddrFloor(3);
//		addressVO2.setAddrRoom(2);
//		dao.update(addressVO2);
//		
//		//刪除
//		dao.delete(2);
//		
//		//查詢單筆
//		AddressVO addressVO3 = dao.findByPrimaryKey(1);
//		System.out.print(addressVO3.getAddrNo() + ",");
//		System.out.print(addressVO3.getAddrBuild() + ",");
//		System.out.print(addressVO3.getAddrFloor() + ",");
//		System.out.println(addressVO3.getAddrRoom());
//		System.out.println("---------------------");
//		
//		//查詢全部
//		List<AddressVO> list = dao.getAll();
//		for(AddressVO aAddr : list) {
//			System.out.print(aAddr.getAddrNo() + ",");
//			System.out.print(aAddr.getAddrBuild() + ",");
//			System.out.print(aAddr.getAddrFloor() + ",");
//			System.out.print(aAddr.getAddrRoom());
//			System.out.println();
//		}
//		Gson gson = new Gson();
//		String data = gson.toJson(list);
//		System.out.println(data);
//		System.out.println(list);
//		
//		
//	}
	
	

}
