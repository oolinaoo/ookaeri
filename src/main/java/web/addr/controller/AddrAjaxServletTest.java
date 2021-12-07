package web.addr.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;
import web.addr.entity.AddressVO;


@WebServlet("/addr/addrAjax.do")
public class AddrAjaxServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
     

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		AddressVO addressVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement("SELECT ADDR_NO, ADDR_BUILD, ADDR_FLOOR, ADDR_ROOM FROM OKAERI.ADDRESS order by ADDR_NO");
			rs = pstmt.executeQuery();
			
			String userId = req.getParameter("user_id");
			
			if(userId == null) {
				String json = 
					  "{\r\n"  
					+ "msg: \"denied\"\r\n"  
					+ "}" ;
				
				res.getWriter().write(json);
				return;
			}
			
			String listAll = "";
			
			while(rs.next()) {
				String list = 
						  "  {\r\n"
						+ "    \"addrNo\": \""+ rs.getInt("ADDR_NO") +"\",\r\n" 
						+ "    \"addrBuild\":\""+ rs.getString("ADDR_BUILD") +"\",\r\n" 
						+ "    \"addrFloor\":\""+ rs.getInt("ADDR_FLOOR") +"\",\r\n"
						+ "    \"addrRoom\":\""+ rs.getInt("ADDR_ROOM") +"\"\r\n" 
						+ "  }";
				
				listAll += list;
				
				if(!rs.isLast()) {
					listAll += ",\r\n";
				}
			}
			
			String json = 
					"[\r\n"  
					+ listAll
					+ "]";
			res.getWriter().write(json);
			
			
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
		
	}

}
