package web.payment.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import web.payment.entity.ManagementPaymentVO;
import web.payment.service.PayService;
import util.Util;
import util.Util;



@WebServlet("/PayServlet")
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//===============設置排程器=================
	private static final String GET_MEM = "SELECT MEM_ACCT,ADDR_NO  FROM MEMBER";
	Timer timer;
    int i=0; 

	public void init() throws ServletException {
	    TimerTask task = new TimerTask(){ 
		        public void run() {
	        	Calendar c = Calendar.getInstance();
				int day = c.get(Calendar.DAY_OF_MONTH);
	
		        if(day ==3) {	
	        		try {
	        			Class.forName(Util.DRIVER);
	        		} catch (ClassNotFoundException ce) {
	        			ce.printStackTrace();
	        		}
	        		Connection con = null;
	        		PreparedStatement pstmt = null;
	        		ResultSet rs = null;
		        	
		        	try {
		        		List<ManagementPaymentVO> payList = new ArrayList<>();
						con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
						pstmt = con.prepareStatement(GET_MEM);
						rs = pstmt.executeQuery();
						while(rs.next()) {
							//設定截止日
							Calendar pd = Calendar.getInstance();
							pd.set(Calendar.DATE,pd.getActualMaximum(Calendar.DATE));//設定每月月底為繳費截止日
							java.util.Date date1 = pd.getTime();
							java.sql.Date payDeadline = new java.sql.Date(date1.getTime());

							//設定期數
							Calendar pp = Calendar.getInstance();
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//							String ppp = sdf.format(pp.getTime());
							Integer payPeriod = (pp.get(Calendar.YEAR)*100) +pp.get(Calendar.MONTH)+1 ;
//							System.out.println(payPeriod);
							
							String memAcct = rs.getString("MEM_ACCT");
							Integer addrNo = rs.getInt("ADDR_NO");
							Integer payAmount = 3600;
							String adminAcct = "gary1";
							Integer payState = 1;
							
							ManagementPaymentVO payVO = new ManagementPaymentVO();
							payVO.setMemAcct(memAcct);
							payVO.setAddrNo(addrNo);
							payVO.setPayDeadline(payDeadline);
							payVO.setPayAmount(payAmount);
							payVO.setPayPeriod(payPeriod);
							payVO.setAdminAcct(adminAcct);
							payVO.setPayState(payState);
							payList.add(payVO);
							PayService paySvc = new PayService();
							payVO = paySvc.addPay(memAcct,  addrNo, payDeadline,
									payAmount, payPeriod, adminAcct, payState);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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
		        } 
	    };
	    
	    timer = new Timer(); 
	    Calendar cal = new GregorianCalendar(2021, Calendar.NOVEMBER, 4, 0, 0, 0);
	    //計算間隔到下個月1號天數
//	    Calendar a = Calendar.getInstance();
//		a.set(Calendar.DATE, 1);
//		a.roll(Calendar.DATE, -1);
//		long maxDate = a.get(Calendar.DATE);
		
	    
	    timer.schedule(task, cal.getTime(),24*60*60*1000);
//	    timer.scheduleAtFixedRate(task, cal.getTime(),24*60*60*1000);
	    System.out.println("已建立排程!");       
	}
	public void destroy() {
        timer.cancel();
        System.out.println("已移除排程!");
	}
		
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
		
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_view.jsp的請求
			

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("pay_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入管理費編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/pay/select_pay.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer payNo = null;
				try {
					payNo = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("管理費編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/pay/select_pay.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				
				PayService paySvc = new PayService();
				ManagementPaymentVO payVO = paySvc.getOnePay(payNo);
				if (payVO == null) {
				
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					;
					RequestDispatcher failureView = req
							.getRequestDispatcher("/pay/select_pay.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("payVO", payVO); // 資料庫取出的PackageVO物件,存入req
				String url = "/pay/listOnePay.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOnePay.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/pay/select_pay.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listALLPay.jsp的請求
			

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer payNo = new Integer(req.getParameter("payNo"));
				
				/***************************2.開始查詢資料****************************************/
				PayService paySvc = new PayService();
				ManagementPaymentVO payVO = paySvc.getOnePay(payNo);
							
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("payVO", payVO);         // 資料庫取出的packVO物件,存入req
				String url = "/pay/update_pay.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_pack.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/pay/listALLPay.jsp");
				failureView.forward(req, res);
			}
		}
//		
//		
		if ("update".equals(action)) { // 來自update_pay.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("action="+action);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer payNo = new Integer(req.getParameter("payNo").trim());
				String memAcct = req.getParameter("memAcct").trim();
				if (memAcct == null || memAcct.trim().length() == 0) {
					errorMsgs.add("住戶帳號請勿空白");
				}
				Integer addrNo = new Integer(req.getParameter("addrNo").trim());
//				Date nowtime = new Date(System.currentTimeMillis());
//				String nowtime1 = nowtime.toString();
//				Timestamp pay_date =Timestamp.valueOf(nowtime1);
				String str = req.getParameter("payDate");
				java.sql.Date payDate = null;
				if(str == null || str.equals("undefined") || str.equals("")) {
					payDate = null;
				}else {
					java.util.Date date1 = new SimpleDateFormat("yyyy.MM.dd").parse(str);
					payDate = new java.sql.Date(date1.getTime());
				}
				
				String str1 = req.getParameter("payDeadline");
				java.sql.Date payDeadline = null;
				if(str1 == null || str1.equals("undefined") || str1.equals("")) {
					payDate = null;
				}else {
					java.util.Date date1 = new SimpleDateFormat("yyyy.MM.dd").parse(str1);
					payDeadline = new java.sql.Date(date1.getTime());
				}
				
				
				
				Integer payAmount = new Integer(req.getParameter("payAmount").trim());
				try {
					payAmount = new Integer(req.getParameter("payAmount").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errorMsgs.add("管理費用請填入數字");
				}
				
//				nowtime = new Date(System.currentTimeMillis());
//				nowtime1 = nowtime.toString();
//				Timestamp pay_recent_call =Timestamp.valueOf(nowtime1);
				
				Integer payPeriod = new Integer(req.getParameter("payPeriod").trim());
				try {
					payPeriod = new Integer(req.getParameter("payPeriod").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errorMsgs.add("期數請填入數字");
				}
				
				String payWay = req.getParameter("payWay").trim();
				if (payWay == null || payWay.trim().length() == 0) {
					errorMsgs.add("繳費方式請勿空白");
				}
				String adminAcct = req.getParameter("adminAcct").trim();
				
				
				Integer payState = null;
				System.out.println(req.getParameter("payState"));
				if(req.getParameter("payState").equals("未繳費")) {
					 payState =1;
				}else if(req.getParameter("payState").equals("已繳費")) {
					payState =0;
				}
				
//				try {
//					payState = new Integer(req.getParameter("payState").trim());
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//					errorMsgs.add("繳費狀態請填入數字");
//				}
				
				
				ManagementPaymentVO payVO = new ManagementPaymentVO();
				payVO.setPayNo(payNo);
				payVO.setMemAcct(memAcct);
				payVO.setAddrNo(addrNo);
//				payVO.setPay_date(pay_date);
				payVO.setPayDeadline(payDeadline);
				payVO.setPayAmount(payAmount);
//				payVO.setPay_recent_call(pay_recent_call);
				payVO.setPayPeriod(payPeriod);
				payVO.setPayWay(payWay);
				payVO.setAdminAcct(adminAcct);
				payVO.setPayState(payState);
				


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("payVO", payVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/payment/payment.html");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				PayService paySvc = new PayService();
				payVO = paySvc.updatePay( payNo,  memAcct,  addrNo,  payDeadline,
						payAmount,  payPeriod,  payWay,  adminAcct, payState);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("payVO", payVO); // 資料庫update成功後,正確的的payVO物件,存入req	
				String url = "http://localhost:8081/Project/admin1/back-end/payment/payment.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/pay/update_pay.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addPay.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String memAcct = req.getParameter("memAcct").trim();
				if (memAcct == null || memAcct.trim().length() == 0) {
					errorMsgs.add("住戶帳號請勿空白");
				}

				Integer addrNo =Integer.parseInt(req.getParameter("addrNo").trim());
//				if (addrNo == null || addrNo.equals("") ) {
//					errorMsgs.add("地址編號請勿空白");
//				}
//				Date nowtime = new Date(System.currentTimeMillis());
//				String nowtime1 = nowtime.toString();
//				Timestamp pay_date =Timestamp.valueOf(nowtime1);
				
				java.sql.Date payDeadline = new Date(System.currentTimeMillis());
				try {
					payDeadline = java.sql.Date.valueOf(req.getParameter("payDeadline").trim());
				} catch (IllegalArgumentException e) { 
					e.printStackTrace();
					payDeadline=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Integer payAmount = new Integer(req.getParameter("payAmount").trim());
				try {
					payAmount = new Integer(req.getParameter("payAmount").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errorMsgs.add("管理費用請填入數字");
				}
				
//				nowtime = new Date(System.currentTimeMillis());
//				nowtime1 = nowtime.toString();
//				Timestamp pay_recent_call =Timestamp.valueOf(nowtime1);
				
				Integer payPeriod = new Integer(req.getParameter("payPeriod").trim());
				try {
					payPeriod = new Integer(req.getParameter("payPeriod").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errorMsgs.add("期數請填入數字");
				}
				
//				String pay_way = req.getParameter("pay_way").trim();
//				if (pay_way == null || pay_way.trim().length() == 0) {
//					errorMsgs.add("繳費方式請勿空白");
//				}
				String adminAcct = req.getParameter("adminAcct").trim();
				
				Integer payState = new Integer(req.getParameter("payState").trim());
				try {
					payState = new Integer(req.getParameter("payState").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					errorMsgs.add("繳費狀態請填入數字");
				}
				
				ManagementPaymentVO payVO = new ManagementPaymentVO();
				payVO.setMemAcct(memAcct);
				payVO.setAddrNo(addrNo);
//				payVO.setPay_date(pay_date);
				payVO.setPayDeadline(payDeadline);
				payVO.setPayAmount(payAmount);
//				payVO.setPay_recent_call(pay_recent_call);
				payVO.setPayPeriod(payPeriod);
				payVO.setAdminAcct(adminAcct);
				payVO.setPayState(payState);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("payVO", payVO); // 含有輸入格式錯誤的packVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/payment/payment.html");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				PayService paySvc = new PayService();
				payVO = paySvc.addPay(memAcct,  addrNo, payDeadline,
						payAmount, payPeriod, adminAcct, payState);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "http://localhost:8081/Project/admin1/back-end/payment/payment.html";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listALLPay.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (ArithmeticException e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/payment/payment.html");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listALLPay.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer payNo = new Integer(req.getParameter("payNo"));
				
				/***************************2.開始刪除資料***************************************/
				PayService paySvc = new PayService();
				paySvc.deletePay(payNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "http://localhost:8081/Project/admin1/back-end/payment/payment.html";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("http://localhost:8081/Project/admin1/back-end/payment/payment.html");
				failureView.forward(req, res);
			}
		}
		if ("listPack".equals(action)) {
			PayService paySvc = new PayService();
			List<ManagementPaymentVO> payList = paySvc.getAll();
			
			String jsonStr = "";
			// 遇到日期格式資料，在創建gson物件同時也指定日期格式 (Client - Server需一致)
			Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
			jsonStr = gson.toJson(payList);
			JsonArray jsonarray = new JsonParser().parse(jsonStr).getAsJsonArray();
			System.out.println("List to JSON: " + jsonStr);
			res.getWriter().print(jsonarray.toString());
		}
	}

}
