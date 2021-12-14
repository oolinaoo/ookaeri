package web.mem.controller;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import web.addr.entity.AddressVO;
import web.addr.service.AddressService;
import web.fam.entity.FamilyMemberVO;
import web.fam.service.FamilyMemberService;
import web.mem.entity.MemberVO;
import web.mem.service.MemberService;

@WebServlet("/front-end/mem/MemberServlet.do")
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
		
		
		// 住戶資料管理(前台)，一開始頁面載入時，列出該住戶的個人資料
		if("get_one_mem".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8"); 
			String memAcct = req.getParameter("memAcct");
			MemberService memSvc = new MemberService();
			MemberVO memberVO = memSvc.getOneMem(memAcct);
			JsonObject jsonObject = new JsonObject();
			if( memberVO != null ) {
				jsonObject.addProperty("memAcct",memberVO.getMemAcct());
				jsonObject.addProperty("memName", memberVO.getMemName());
				jsonObject.addProperty("memSex", memberVO.getMemSex());
				jsonObject.addProperty("memBirthday", memberVO.getMemBirthday().toString());
				//System.out.println(memberVO.getMemBirthday().toString());
				jsonObject.addProperty("memEmail", memberVO.getMemEmail());
				jsonObject.addProperty("memPhone", memberVO.getMemPhone());
				
				AddressService addrSvc = new AddressService();
				AddressVO addressVO = addrSvc.getOneAddr(memberVO.getAddrNo());
				jsonObject.addProperty("addrBuild", addressVO.getAddrBuild());
				jsonObject.addProperty("addrFloor", addressVO.getAddrFloor());
				jsonObject.addProperty("addrRoom", addressVO.getAddrRoom());
				
				
				FamilyMemberService famMemSvc = new FamilyMemberService();
				List<FamilyMemberVO> famMemList = famMemSvc.getAll();
				List<String> famNameList = new ArrayList<String>();
				for(FamilyMemberVO aFam : famMemList) {
					if( memAcct.equals(aFam.getMemAcct()) ) {
						famNameList.add(aFam.getFamMemName());
					}
				}
				if(famNameList.size() != 0) {
					Gson gson = new Gson();
					String famMems = gson.toJson(famNameList);
					jsonObject.addProperty("famMems", famMems);
				}
				String data = jsonObject.toString();
				//System.out.println(data);
				res.getWriter().write(data);
				return;
				
			}
			
		}
		
		// 住戶資料管理(前台)，一開始頁面載入時，傳給前端 資料庫的圖片
		if("getImage".equals(action)) {
			String memAcct = req.getParameter("memAcct");
			MemberService memSvc = new MemberService();
			MemberVO memberVO = memSvc.getOneMem(memAcct);
			byte[] image = memberVO.getMemPhoto();
			ServletOutputStream os = res.getOutputStream();
			res.setContentType("image/jpeg");
			res.setContentLength(image.length);
			os.write(image);
		}
		
		//住戶資料管理(前台) - 修改資料
		if("profile_update".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8"); 
			String memAcct = req.getParameter("memAcct"); //住戶帳號，不可更改
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMem(memAcct);
			JsonObject returnMsgs = new JsonObject();  //蒐集錯誤訊息
			
			Part part = req.getPart("memPhoto");
			InputStream is = null;
			byte[] memPhoto = null;
			String filename = getFileNameFromPart(part);
			//System.out.println(filename);
			//如果沒有上傳圖片filename會是memPhoto，印出的header資訊為：header=form-data; name="memPhoto"
			//若有上傳圖片，印出的header資訊為：header=form-data; name="memPhoto"; filename="alert.png"
			if( "memPhoto".equals(filename)) {
				System.out.println("無照片");
				memPhoto = memberVO.getMemPhoto(); //無上傳照片，就存原本的照片進資料庫

			}else {
				System.out.println("有照片");
				is = part.getInputStream();
				memPhoto = new byte[is.available()]; //準備好跟該圖片剛好大小的水桶
				is.read(memPhoto); //將讀入的位元資料放入水桶
				is.close();
			}
			//不需用到base64了！
			//String base64 = req.getParameter("memPhoto");
			//將傳過來的字串切割成base64
			//String memPhoto = base64.substring(base64.indexOf(",")+1);  
			
			String memName = req.getParameter("memName");
			String nameReg = "^[\u4e00-\u9fa5]{2,50}$";
			if( memName.length() == 0 ) {
				returnMsgs.addProperty("errName", "姓名欄位請勿空白");
			} else if(!memName.matches(nameReg)) {
				returnMsgs.addProperty("errName", "姓名只能是中文，且長度必需在2到50之間");
			}
			
			
			String memSex = req.getParameter("memSex");
			
			
			java.sql.Date memBirthday = null;
			try {
				memBirthday = java.sql.Date.valueOf(req.getParameter("memBirthday"));
			} catch (IllegalArgumentException e) {  //如果memBirthday為空字串，就會拋出此例外
				//memBirthday=new java.sql.Date(System.currentTimeMillis()); 
				returnMsgs.addProperty("errBirth","請選擇生日日期!");
			}
			
			String memEmail = req.getParameter("memEmail");
			String emailReg = "^\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}$";
			if( memEmail.length() == 0 ) {
				returnMsgs.addProperty("errEmail", "信箱欄位請勿空白");
			}else if( !memEmail.matches(emailReg) ){
				returnMsgs.addProperty("errEmail", "信箱不符合格式");
			}
			
			String memPhone = req.getParameter("memPhone");
			String phoneReg = "^(09){1}\\d{8}$";
			if( memPhone.length() == 0 ) {
				returnMsgs.addProperty("errPhone", "聯絡電話欄位請勿空白");
			}else if( !memPhone.matches(phoneReg) ) {
				returnMsgs.addProperty("errPhone", "聯絡電話不符合格式");
			}
			
			//住戶資料修改跟家人資料修改要一起成功更新
			Gson gson = new Gson();
			// ["王小美","居大居","王小豬"] Json陣列的字串
			String famMems = req.getParameter("famMems"); 
			// ["王小美","居大居","王小豬"] 將Json陣列的字串 轉為 JsonArray
			JsonArray famMemsJsonArray = gson.fromJson(famMems, JsonArray.class);
			// 將JsonArray轉為字串陣列
			String[] arrFams = gson.fromJson(famMemsJsonArray, String[].class);
			// 將字串陣列 轉為 List物件
			List<String> famMemAryList = new ArrayList<>();
			famMemAryList = Arrays.asList(arrFams); // [王小美, 居大居, 王小豬]
			
			//如果沒有家人資料，就會是空陣列
			if(!famMemAryList.isEmpty()) {
				String famNameReg = "^[\u4e00-\u9fa5]{2,50}$";
				for(int i = 0; i< famMemAryList.size(); i++) {
					if(!famMemAryList.get(i).matches(famNameReg)) {
						returnMsgs.addProperty("errFams", "同住家人姓名只能是中文，且長度必需在2到50之間");
					}
				}
			}
			
			System.out.println("returnMsgs(是否有驗證錯誤): " + returnMsgs.toString());
			//原本老師的gson-2.2.4.jar沒有這個方法，後來我換版本2.8.6就有了～
			//以上有任何一個錯，returnMsgs的長度就不等於0
			if(returnMsgs.size() != 0) {
				returnMsgs.addProperty("msg","errorMsgs");
				res.getWriter().write(returnMsgs.toString());
				return; //回傳錯誤訊息，並結束程式
			}
			
			
			/*************************** 開始更改資料 ***************************************/
			String memPwd = req.getParameter("memPwd"); //住戶修改個人資料，密碼無法更改
			if ( memPwd.equals(memberVO.getMemPwd()) ){
				String memId = memberVO.getMemId(); //身分證字號，無法更改
				Integer addrNo = memberVO.getAddrNo(); //地址編號，無法更改
				Integer memState = memberVO.getMemState(); //住戶狀態，無法更改
				MemberVO returnMember = memberSvc.updateMemWithFamMems(memPwd, memName, memId, memSex, memEmail, memBirthday, 
						addrNo, memPhoto, memPhone, memState, memAcct, famMemAryList);	
				if(returnMember != null) {
					returnMsgs.addProperty("msg","success");
					res.getWriter().write(returnMsgs.toString());
					return;
				}else {
					returnMsgs.addProperty("msg","fail");
					res.getWriter().write(returnMsgs.toString());
					return;
				}

			} else {
				returnMsgs.addProperty("msg","wrongPwd");
				res.getWriter().write(returnMsgs.toString());
				return;
			}
			
		}
		
		//住戶資料管理(前台) - 修改密碼
		if("update_pwd".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8"); 
			String memAcct = req.getParameter("memAcct");
			String oldPwd = req.getParameter("oldPwd");
			String newPwdOne = req.getParameter("newPwdOne");
			String newPwdTwo = req.getParameter("newPwdTwo");
			JsonObject returnMsgs = new JsonObject();  //蒐集錯誤訊息
			
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMem(memAcct);
			
			if( oldPwd.length() == 0 ) {
				returnMsgs.addProperty("errOldPwd", "請輸入舊密碼");
			}
			
			if( newPwdOne.length() == 0 ) {
				returnMsgs.addProperty("errNewPwdOne", "請輸入新密碼");
			}
			
			if( newPwdTwo.length() == 0 ) {
				returnMsgs.addProperty("errNewPwdTwo", "請輸入確認密碼");
			}
			
			//以上有任何一個錯，就回傳錯誤訊息，結束程式
			if(returnMsgs.size() != 0) {
				returnMsgs.addProperty("msg","fail");
				res.getWriter().write(returnMsgs.toString());
				return;
			}
			
			String pwdReg = "^[a-zA-Z0-9]{8,25}$";
			if(!newPwdOne.matches(pwdReg)) {
				if(!newPwdOne.equals(newPwdTwo)) {
					returnMsgs.addProperty("errNewPwdOne", "請輸入符合規定的密碼");
					returnMsgs.addProperty("errNewPwdTwo", "新密碼與確認密碼不相符");
					returnMsgs.addProperty("msg","fail");
					res.getWriter().write(returnMsgs.toString());
					return;
				
				}else{
					returnMsgs.addProperty("errNewPwdOne", "請輸入符合規定的密碼");
					returnMsgs.addProperty("errNewPwdTwo", "請輸入符合規定的密碼");
					returnMsgs.addProperty("msg","fail");
					res.getWriter().write(returnMsgs.toString());
					return;
				}
			}else {
				if(!newPwdOne.equals(newPwdTwo)) {
					returnMsgs.addProperty("errNewPwdTwo", "新密碼與確認密碼不相符 ");
					returnMsgs.addProperty("msg","fail");
					res.getWriter().write(returnMsgs.toString());
					return;
				}else {
					if(!oldPwd.equals( memberVO.getMemPwd() )) {
						returnMsgs.addProperty("msg", "wrongOldPwd");
						res.getWriter().write(returnMsgs.toString());
						return;
					}
				}
			}
			
			/*************************** 開始修改密碼 ***************************************/
			Integer affectedRows = memberSvc.updatePwd(memAcct, newPwdOne);
			if(affectedRows != null) {
				returnMsgs.addProperty("msg", "success");
				res.getWriter().write(returnMsgs.toString());
				return;
				
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
