package web.pack.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import management_payment.entity.Management_PaymentVO;
import web.pack.entity.PackageVO;
import web.pack.service.PackageService;
import util.Util;


@WebServlet("/PackServlet.do")
public class PackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//取得資料庫member的帳號和地址編號
	private static final String GET_MEM = "SELECT ADDR_NO  FROM MEMBER";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if ("insertInfo".equals(action)) {
			try {
    			Class.forName(Util.DRIVER);
    		} catch (ClassNotFoundException ce) {
    			ce.printStackTrace();
    		}
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		
    		try {
        		List<Integer> addrNO = new ArrayList<>();
				con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
				pstmt = con.prepareStatement(GET_MEM);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					addrNO.add(rs.getInt("ADDR_NO"));
				}
				String jsonStr = "";
				// 遇到日期格式資料，在創建gson物件同時也指定日期格式 (Client - Server需一致)
				Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
				jsonStr = gson.toJson(addrNO);
				JsonArray jsonarray = new JsonParser().parse(jsonStr).getAsJsonArray();
				System.out.println("List to JSON: " + jsonStr);
				res.getWriter().print(jsonarray.toString());
				
				
				
    		}catch (SQLException e) {
				e.printStackTrace();
			}finally {
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
		
		if ("update".equals(action)) { 
			System.out.println("update action="+action);
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer packNo = new Integer(req.getParameter("packNo").trim());
				Integer addrNo = new Integer(req.getParameter("addrNo").trim());
				
				String str = req.getParameter("packArrived");
				java.util.Date date = new SimpleDateFormat("yyyy.MM.dd").parse(str);
				java.sql.Date packArrived = new java.sql.Date(date.getTime());
				
				
				String str1 = req.getParameter("packReceived");
				java.sql.Date packReceived = null;
				if(str1 == null || str1.equals("undefined") || str1.equals("")) {
					packReceived = null;
				}else {
					java.util.Date date1 = new SimpleDateFormat("yyyy.MM.dd").parse(str1);
					packReceived = new java.sql.Date(date1.getTime());
				}
				String packLogistics = req.getParameter("packLogistics").trim();
				if (packLogistics == null || packLogistics.trim().length() == 0) {
					errorMsgs.add("物流商請勿空白");
				}
				
				Integer packTypeNo = new Integer(req.getParameter("packTypeNo").trim());
				
				Integer packState = new Integer(req.getParameter("packState").trim());
				
//				java.sql.Date packArrived = new Date(System.currentTimeMillis());
//				try {
//					packArrived = java.sql.Date.valueOf(req.getParameter("packArrived").trim());
//				} catch (IllegalArgumentException e) { 
//					e.printStackTrace();
//					packArrived=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				
				
				
//				java.sql.Date packReceived = new Date(System.currentTimeMillis());;
//				try {
//					packReceived = java.sql.Date.valueOf(req.getParameter("packReceived").trim());
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//					packReceived=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				

//				try {
//					pack_type_no = new Integer(req.getParameter("pack_type_no").trim());
//				} catch (NumberFormatException e) {
//					pack_type_no = 1;
//					errorMsgs.add("包裹種類編號請填數字0或1");
//				}
//				
				
//				try {
//					pack_state = new Integer(req.getParameter("pack_state").trim());
//				} catch (NumberFormatException e) {
//					pack_state = 1;
//					errorMsgs.add("包裹種類編號請填數字0或1");
//				}
				PackageVO packVO = new PackageVO();
				packVO.setPackNo(packNo);
				packVO.setAddrNo(addrNo);
				packVO.setPackArrived(packArrived);
				packVO.setPackReceived(packReceived);
				packVO.setPackLogistics(packLogistics);
				packVO.setPackTypeNo(packTypeNo);
				packVO.setPackState(packState);
				


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("packVO", packVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/pack/pack.html");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				PackageService packSvc = new PackageService();
				packVO = packSvc.updatePack(packNo, addrNo, packArrived, packReceived, packLogistics,packTypeNo, packState);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("packVO", packVO); // 資料庫update成功後,正確的的packVO物件,存入req
				System.out.println("packVO1=" + packVO);	
				String url = "http://localhost:8081/Project/admin1/back-end/pack/pack.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/pack/pack.html");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//				Integer packNo = new Integer(req.getParameter("packNo").trim());
				
				Integer addrNo =Integer.parseInt(req.getParameter("addrNo"));
				String str = req.getParameter("packArrived");
				System.out.println(str);
				java.sql.Date packArrived = java.sql.Date.valueOf(str);
				String packLogistics = req.getParameter("packLogistics");
				Integer packTypeNo = Integer.parseInt(req.getParameter("packTypeNo"));
				Integer packState = Integer.parseInt(req.getParameter("packState"));
			
//				Integer addrNo = new Integer(req.getParameter("addrNo").trim());
//				
//				java.sql.Date packArrived = new Date(System.currentTimeMillis());;
//				try {
//					packArrived = java.sql.Date.valueOf(req.getParameter("packArrived").trim());
//				} catch (IllegalArgumentException e) {
//					packArrived=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				
//				java.sql.Date packReceived = new Date(System.currentTimeMillis());;
//				try {
//					packReceived = java.sql.Date.valueOf(req.getParameter("packReceived").trim());
//				} catch (IllegalArgumentException e) {
//					packReceived=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				
//				String packLogistics = req.getParameter("packLogistics").trim();
//				if (packLogistics == null || packLogistics.trim().length() == 0) {
//					errorMsgs.add("物流商請勿空白");
//				}
//				
//				Integer packTypeNo = new Integer(req.getParameter("packTypeNo").trim());
//				
//				Integer packState = new Integer(req.getParameter("packState").trim());
				

				PackageVO packVO = new PackageVO();
				packVO.setAddrNo(addrNo);
				packVO.setPackArrived(packArrived);
//				packVO.setPackReceived(packReceived);
				packVO.setPackLogistics(packLogistics);
				packVO.setPackTypeNo(packTypeNo);
				packVO.setPackState(packState);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("packVO", packVO); // 含有輸入格式錯誤的packVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/pack/pack.html");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				PackageService packSvc = new PackageService();
				packVO = packSvc.addPack(addrNo, packArrived, packLogistics, packTypeNo, packState);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "http://localhost:8081/Project/admin1/back-end/pack/pack.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (ArithmeticException e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/pack/pack.html");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer packNo = new Integer(req.getParameter("packNo"));
				
				/***************************2.開始刪除資料***************************************/
				PackageService packSvc = new PackageService();
				packSvc.deletePack(packNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "http://localhost:8081/Project/admin1/back-end/pack/pack.html";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/pack/pack.html");
				failureView.forward(req, res);
			}
		}
		if ("listPack".equals(action)) {
			PackageService packSvc = new PackageService();
			List<PackageVO> packList = packSvc.getAll();
			
			String jsonStr = "";
			// 遇到日期格式資料，在創建gson物件同時也指定日期格式 (Client - Server需一致)
			Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
			jsonStr = gson.toJson(packList);
			JsonArray jsonarray = new JsonParser().parse(jsonStr).getAsJsonArray();
			System.out.println("List to JSON: " + jsonStr);
			res.getWriter().print(jsonarray.toString());
		}
		
//		====================================================================================
//		if ("getOne_For_Display".equals(action)) { // 來自select_view.jsp的請求
//			
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String str = req.getParameter("packNo");
//				System.out.println(str);
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入包裹編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/pack/select_view.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				Integer packNo = null;
//				try {
//					packNo = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("包裹編號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/pack/select_view.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				
//				PackageService packSvc = new PackageService();
//				PackageVO packVO = packSvc.getOnePack(packNo);
//				if (packVO == null) {
//				
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					;
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/pack/select_view.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				
//				req.setAttribute("packVO", packVO); // 資料庫取出的PackageVO物件,存入req
//				String url = "/pack/listOnePack.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/pack/select_view.jsp");
//				failureView.forward(req, res);
//			}
//		}
//		
//		
//		if ("getOne_For_Update".equals(action)) { // 來自listAllPack.jsp的請求
//			
//			
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				Integer packNo = new Integer(req.getParameter("packNo"));
//				
//				/***************************2.開始查詢資料****************************************/
//				PackageService packSvc = new PackageService();
//				PackageVO packVO = packSvc.getOnePack(packNo);
//				System.out.println("packVO1=" + packVO);				
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("packVO", packVO);         // 資料庫取出的packVO物件,存入req
//				String url = "/pack/update_pack.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_pack.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/pack/listALLPack.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}
   

}
