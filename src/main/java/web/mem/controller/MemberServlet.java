package web.mem.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import web.addr.entity.AddressVO;
import web.addr.service.AddressService;
import web.mem.entity.MemberVO;
import web.mem.service.MemberService;

@WebServlet("/front-end/register/MemberServlet.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); 
		
		if("register".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				
				MemberService memSvc = new MemberService();
				List<MemberVO> memList = memSvc.getAll(); //住戶帳號、地址資訊有用到
				
				/* 使用者名稱規定，參考於Google的使用者名稱規定：
				 * https://support.google.com/mail/answer/9211434?hl=zh-Hant#
				 */
				String memAcct = req.getParameter("memAcct");
				String acctReg = "^[a-zA-Z0-9]{6,25}$";
				if( memAcct == null || memAcct.trim().length() == 0 ) {
					errorMsgs.put("memAcct", "帳號欄位請勿空白");
				}else if(!memAcct.trim().matches(acctReg)) {
					errorMsgs.put("memAcct", "會員帳號只能是英文字母、數字，且長度必須在6到25之間");
					
				}else {
					for(MemberVO aMem: memList) {
						if( memAcct.equals(aMem.getMemAcct())) {
							errorMsgs.put("memAcct", "已有人使用此住戶帳號");
						}
					}
				}
				
				
				String memPwd = req.getParameter("memPwd");
				String pwdReg = "^[a-zA-Z0-9]{8,25}$";
				if( memPwd == null || memPwd.trim().length() == 0 ) {
					errorMsgs.put("memPwd", "密碼欄位請勿空白");
				}else if(!memPwd.trim().matches(pwdReg)) {
					errorMsgs.put("memPwd", "密碼只能是英文字母、數字，且長度必須在8到25之間");
				}
				
				
				String memName = req.getParameter("memName");
				String nameReg = "^[\u4e00-\u9fa5]{2,50}$";
				if( memName == null || memName.trim().length() == 0 ) {
					errorMsgs.put("memName", "姓名欄位請勿空白");
				} else if(!memName.trim().matches(nameReg)) {
					errorMsgs.put("memName", "姓名只能是中文，且長度必需在2到50之間");
				}
				
				java.sql.Date memBirthday = null;
				try {
					memBirthday = java.sql.Date.valueOf(req.getParameter("memBirthday").trim());
				} catch (IllegalArgumentException e) {  //如果memBirthday為空字串，就會拋出此例外
					memBirthday=new java.sql.Date(System.currentTimeMillis()); 
					errorMsgs.put("memBirthday","請選擇生日日期!");
				}
				
				String memId = req.getParameter("memId");
				String idReg = "^[a-zA-Z][12]\\d{8}$";
				if( memId == null || memId.trim().length() == 0 ) {
					errorMsgs.put("memId", "身分證欄位請勿空白");
				}else if(!memId.trim().matches(idReg)) {
					errorMsgs.put("memId", "不符合身分證格式");
				}
				
				String memSex = req.getParameter("memSex");
				if( memSex == null ) {
					errorMsgs.put("memSex", "請選擇性別");
				} 
				
				String memPhone = req.getParameter("memPhone");
				String phoneReg = "^(09){1}\\d{8}$";
				if( memPhone == null || memPhone.trim().length() == 0 ) {
					errorMsgs.put("memPhone", "聯絡電話欄位請勿空白");
					
				}else if( !memPhone.trim().matches(phoneReg) ) {
					errorMsgs.put("memphone", "聯絡電話不符合格式");
					
				}
				
				/* Email驗證格式參考於：
				 * https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/320404/
				 */
				String memEmail = req.getParameter("memEmail");
				/*  \p{Alpha} -> [\p{Lower}\p{Upper}]
				 *  參考於：https://stackoverflow.com/questions/34480452/difference-between-palpha-and-pl-in-java
				 */
				// \w -> [a-zA-Z_0-9]
				String emailReg = "^\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}$";
				if(memEmail == null || memEmail.trim().length() == 0 ) {
					errorMsgs.put("memEmail", "信箱欄位請勿空白");
				}else if( !memEmail.trim().matches(emailReg) ){
					errorMsgs.put("memEmail", "信箱不符合格式");
				}
				
				String memBuild = req.getParameter("memBuild");
				String memFloor = req.getParameter("memFloor");
				String memRoom = req.getParameter("memRoom");
				List<String> addrChoose = new ArrayList<String>();
				addrChoose.add(memBuild);
				addrChoose.add(memFloor);
				addrChoose.add(memRoom);
				
				Integer addrNo = null;
				if("--".equals(memBuild) || "--".equals(memFloor) || "--".equals(memRoom)) {
					errorMsgs.put("memAddr", "地址資訊：棟/樓/房皆要選擇");
				}else {
					AddressService AddrSvc = new AddressService();
					List<AddressVO> addrList = AddrSvc.getAll();
					for(AddressVO oneAddr: addrList) {
						if( memBuild.equals(oneAddr.getAddrBuild()) && 
						    memFloor.equals( String.valueOf(oneAddr.getAddrFloor()) ) &&
						    memRoom.equals( String.valueOf(oneAddr.getAddrRoom()) ) ) 
						{ 
							addrNo = oneAddr.getAddrNo();
						}
					}
					
					for(MemberVO aMem: memList) {
						if( addrNo.equals(aMem.getAddrNo()) && aMem.getMemState() == 0) {
							errorMsgs.put("memAddr", "錯誤！此房號已有住戶");
						}
					}
					
				}
				
				//參考於大吳老師的 WebApp_ch04_FileUpload 專案，以及小吳老師的 模組15 資料庫圖片處理
				Part part = req.getPart("memPhoto");
				InputStream is = null;
				byte[] memPhoto = null;
				String filename = getFileNameFromPart(part);
				if( filename == null ) {
					errorMsgs.put("memPhoto", "需上傳照片");
				}else {
					is = part.getInputStream();
					memPhoto = new byte[is.available()];
					is.read(memPhoto);
					is.close();
				}
				
				//帳號創建時間
				Timestamp acctCreatetime = Timestamp.valueOf(LocalDateTime.now());
				
				//同住家人姓名
				/* convert string-array to arraylist
				 * https://stackoverflow.com/questions/10530353/convert-string-array-to-arraylist
				 */
				String[] famMemList = req.getParameterValues("famMem");
				String famNameReg = "^[\u4e00-\u9fa5]{2,50}$";
				List<String> famMemStrList = Arrays.asList(famMemList);
				List<String> famMemAryList = new ArrayList<String>();
				for(int i = 0; i < famMemStrList.size(); i++){
					if( famMemStrList.get(i).trim().equals("") ) {
						continue;
					}else {
						famMemAryList.add(famMemStrList.get(i).trim());
					}
				}
				if(!famMemAryList.isEmpty()) {
					for(int i = 0; i< famMemAryList.size(); i++) {
						if(!famMemAryList.get(i).trim().matches(famNameReg)) {
							errorMsgs.put("famName", "同住家人姓名只能是中文，且長度必需在2到50之間");
						}
					}
				}

				
				//以下至少會有一個資料是錯誤的
				MemberVO memberVO = new MemberVO();
				memberVO.setMemAcct(memAcct);
				memberVO.setMemPwd(memPwd);
				memberVO.setMemName(memName);
				memberVO.setMemId(memId);
				memberVO.setMemSex(memSex);
				memberVO.setMemEmail(memEmail);
				memberVO.setMemBirthday(memBirthday);
				memberVO.setAddrNo(addrNo);
				memberVO.setMemPhoto(memPhoto);
				memberVO.setMemPhone(memPhone);
				memberVO.setAcctCreatetime(acctCreatetime);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("addrChoose", addrChoose);
					req.setAttribute("famMemAryList", famMemAryList);
					req.setAttribute("memberVO", memberVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/register/mem-register.jsp"); //將打過的資料包裝好後，送回請求的網頁
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				//沒有寫同住家人者，家人列表為空
				if(famMemAryList.isEmpty()) {
					memSvc.addMem(memAcct, memPwd, memName, memId, memSex, memEmail, 
							memBirthday, addrNo, memPhoto, memPhone, acctCreatetime);
					
				}else {
					memSvc.addMemWithFamMems(memAcct, memPwd, memName, 
							memId, memSex, memEmail,
							memBirthday, addrNo, memPhoto, 
							memPhone, acctCreatetime, famMemAryList);
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/register/registerSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);	
			}catch (Exception e) {
				System.out.println("===============================");
				System.out.println("hello2");
				errorMsgs.put("other", e.getMessage());
				e.printStackTrace();
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/register/mem-register.jsp");
				failureView.forward(req, res);
				
			}
			
			
		}
		
	}
	
	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		//System.out.println("header=" + header); // 測試用 
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName(); //Chrome和IE都可以上傳成功
		//System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
