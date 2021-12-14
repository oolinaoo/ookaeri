package web.payment.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import web.payment.dao.Management_PaymentDAO;
import web.payment.entity.ManagementPaymentVO;
import util.Util;

public class Management_PaymentDAOImpl implements Management_PaymentDAO {
	private static final String INSERT_STMT = "INSERT INTO MANAGEMENT_PAYMENT(mem_acct, addr_no, pay_deadline, pay_amount, pay_period, admin_acct, pay_state) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE MANAGEMENT_PAYMENT  SET mem_acct = ?, addr_no = ?, pay_deadline = ?, pay_amount = ?, pay_period = ?, pay_way = ?, admin_acct= ?, pay_state = ? WHERE pay_no = ?";
	private static final String DELETE_STMT = "DELETE FROM MANAGEMENT_PAYMENT WHERE pay_no = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MANAGEMENT_PAYMENT WHERE pay_no = ?";
	private static final String GET_ALL = "SELECT * FROM MANAGEMENT_PAYMENT";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(ManagementPaymentVO management_payment) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, management_payment.getMemAcct());
			pstmt.setInt(2, management_payment.getAddrNo());
			pstmt.setDate(3, management_payment.getPayDeadline());
			pstmt.setInt(4, management_payment.getPayAmount());
			pstmt.setInt(5, management_payment.getPayPeriod());
			pstmt.setString(6, management_payment.getAdminAcct());
			pstmt.setInt(7, management_payment.getPayState());

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
	public void update(ManagementPaymentVO management_payment) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, management_payment.getMemAcct());
			pstmt.setInt(2, management_payment.getAddrNo());
//			pstmt.setTimestamp(3, management_payment.getPay_date());
			pstmt.setDate(3, management_payment.getPayDeadline());
			pstmt.setInt(4, management_payment.getPayAmount());
//			pstmt.setTimestamp(6, management_payment.getPay_recent_call());
			pstmt.setInt(5, management_payment.getPayPeriod());
			pstmt.setString(6, management_payment.getPayWay());
			pstmt.setString(7, management_payment.getAdminAcct());
			pstmt.setInt(8, management_payment.getPayState());
			pstmt.setInt(9, management_payment.getPayNo());

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
	public void delete(int payNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, payNo);
			
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
	public ManagementPaymentVO findByPK(int payNo) {
		ManagementPaymentVO man = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, payNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				man = new ManagementPaymentVO();
				man.setPayNo(rs.getInt("PAY_NO"));
				man.setMemAcct(rs.getString("MEM_ACCT"));
				man.setAddrNo(rs.getInt("ADDR_NO"));
				man.setPayDate(rs.getTimestamp("PAY_DATE"));
				man.setPayDeadline(rs.getDate("PAY_DEADLINE"));
				man.setPayAmount(rs.getInt("PAY_AMOUNT"));
				man.setPayRecentCall(rs.getTimestamp("PAY_RECENT_CALL"));
				man.setPayPeriod(rs.getInt("PAY_PERIOD"));
				man.setPayWay(rs.getString("PAY_WAY"));
				man.setAdminAcct(rs.getString("ADMIN_ACCT"));
				man.setPayState(rs.getInt("PAY_STATE"));
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

		return man;
	}

	@Override
	public List<ManagementPaymentVO> getAll() {
		List<ManagementPaymentVO> payList = new ArrayList<>();
		ManagementPaymentVO man = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				man = new ManagementPaymentVO();
				man.setPayNo(rs.getInt("PAY_NO"));
				man.setMemAcct(rs.getString("MEM_ACCT"));
				man.setAddrNo(rs.getInt("ADDR_NO"));
				man.setPayDate(rs.getTimestamp("PAY_DATE"));
				man.setPayDeadline(rs.getDate("PAY_DEADLINE"));
				man.setPayAmount(rs.getInt("PAY_AMOUNT"));
				man.setPayRecentCall(rs.getTimestamp("PAY_RECENT_CALL"));
				man.setPayPeriod(rs.getInt("PAY_PERIOD"));
				man.setPayWay(rs.getString("PAY_WAY"));
				man.setAdminAcct(rs.getString("ADMIN_ACCT"));
				man.setPayState(rs.getInt("PAY_STATE"));
				payList.add(man);
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
		return payList;
	}

}
