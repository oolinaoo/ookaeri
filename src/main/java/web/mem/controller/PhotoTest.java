package web.mem.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import util.Util;


@WebServlet("/front-end/photo.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PhotoTest extends HttpServlet {
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doGet(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); 
		if("photo".equals(action)) {
			
//			System.out.println("ContentType="+req.getContentType()); // 測試用
			Part photo = req.getPart("photo");
			System.out.println("是否為空值： " + photo); //若無上傳圖片，不是空值
			String filename = getFileNameFromPart(photo);
			System.out.println("filename: " + filename); //無上傳圖片，filename為空值
			
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			try {
//				Class.forName(Util.DRIVER);
//				con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
//				pstmt = con.prepareStatement("INSERT INTO OKAERI.PHOTO(Num, Photo) VALUES(?, ?)");
//				InputStream is = photo.getInputStream();
//				pstmt.setInt(1,1);
//				pstmt.setBinaryStream(2, is);
//				pstmt.executeUpdate();
//				is.close();
//				System.out.println("新增成功");
//				
//			} catch (ClassNotFoundException ce) {
//				ce.printStackTrace();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
			
			
		}
		
		
	}
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用 
		//String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1); //Chrome上傳成功，但IE會上傳失敗
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName(); //Chrome和IE都可以上傳成功
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
