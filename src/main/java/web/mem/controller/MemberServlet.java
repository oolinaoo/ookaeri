package web.mem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.JedisUtil;
import web.addr.entity.AddressVO;
import web.addr.service.AddressService;
import web.fam.entity.FamilyMemberVO;
import web.fam.service.FamilyMemberService;
import web.mem.entity.MemberVO;
import web.mem.service.MemberService;

@WebServlet("/mem/MemberServlet.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static JedisPool pool = null;
	static {
		pool = JedisUtil.getJedisPool();
	 }
	
	public void destroy() {
		JedisUtil.shutdownJedisPool();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); 
		
		
		// *********** 住戶資料管理(前台)，一開始頁面載入時，列出該住戶的個人資料 ***********
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
				Gson gson = new Gson();
				String famMems = gson.toJson(famNameList);
				jsonObject.addProperty("famMems", famMems);
	
				String data = jsonObject.toString();
				//System.out.println(data);
				res.getWriter().write(data);
				return;
				
			}
			
		}
		
		// *********** 住戶資料管理(前台)，一開始頁面載入時，傳給前端 資料庫的圖片 ***********
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
		
		//************************住戶資料管理(前台) - 修改資料************************
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
		
		//************************ 住戶資料管理(前台) - 修改密碼************************
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
			
			/*************************** 開始修改密碼 ***************************/
			Integer affectedRows = memberSvc.updatePwd(memAcct, newPwdOne);
			if(affectedRows != null) {
				returnMsgs.addProperty("msg", "success");
				res.getWriter().write(returnMsgs.toString());
				return;
				
			}
			
		}
		
		//************************ 住戶帳號管理(後台)：列出所有資料 ************************
		if("listAllForBackEnd".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8"); 
			MemberService memSvc = new MemberService();
			List<MemberVO> memberList = memSvc.getAll();			
			JsonArray jArray = new JsonArray();
			
			for(MemberVO aMem: memberList) {
				JsonObject jObject = new JsonObject();
				jObject.addProperty("memAcct", aMem.getMemAcct());
				jObject.addProperty("memPwd", aMem.getMemPwd());
				jObject.addProperty("memName", aMem.getMemName());
				jObject.addProperty("memId", aMem.getMemId());
				jObject.addProperty("memPhone", aMem.getMemPhone());
				jObject.addProperty("memEmail", aMem.getMemEmail());
				
				AddressService addrSvc = new AddressService();
				AddressVO addrVO = addrSvc.getOneAddr(aMem.getAddrNo());
				JsonObject addrObject = new JsonObject();
				addrObject.addProperty("addrBuild", addrVO.getAddrBuild());
				addrObject.addProperty("addrFloor", addrVO.getAddrFloor());
				addrObject.addProperty("addrRoom", addrVO.getAddrRoom());
				jObject.add("memAddr", addrObject);
				
				jObject.addProperty("memState", aMem.getMemState());
				
				jArray.add(jObject);				
				
			}

			res.getWriter().write(jArray.toString());
			
			return;
			
		}
		
		//************************ 住戶帳號管理(後台) or 註冊：地址資料的動態的下拉式選單 ************************
		/*** 按下新增 or 編輯，傳送地址資訊給前端，製作動態的下拉式選單 ***/
		if("getAddrData".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8"); 
			AddressService addrSvc = new AddressService();
			List<AddressVO> addrVOlist = addrSvc.getAll();
			List<String> buildList = new ArrayList<String>();
			JsonObject addrDataObj = new JsonObject();
			//去除重複值：https://iter01.com/573181.html
			//取得棟號，並且不重複
			for(AddressVO anAddr : addrVOlist) {
				if(!buildList.contains(anAddr.getAddrBuild())) {
					buildList.add(anAddr.getAddrBuild());
				}
			}
			
			Gson gson = new Gson();
			String addrBuild = gson.toJson(buildList); //將List物件轉為Json格式的字串
			addrDataObj.addProperty("addrBuild", addrBuild); // "addrBuild":"[\"A\",\"B\",\"C\",\"D\"]"
			
			for(String buildNo : buildList) {  //A
				JsonObject buildObj = new JsonObject();
				
				List<Integer> floorList = new ArrayList<Integer>();	
				for(AddressVO anAddr : addrVOlist) {
					if(buildNo.equals(anAddr.getAddrBuild())) {
						if(!floorList.contains(anAddr.getAddrFloor())) { //取得該棟樓的樓號，不重複 -> [1,2,3,4,5]
							floorList.add(anAddr.getAddrFloor());
						}
					}
				}
				buildObj.addProperty("addrFloor", gson.toJson(floorList)); // {"addrFloor":"[1,2,3,4,5]"}
				
				//取得 該棟樓 每層樓 的房號
				JsonObject roomObj = new JsonObject();
				for(Integer floorNo : floorList) {
					List<Integer> roomList = new ArrayList<Integer>();
					for(AddressVO anAddr : addrVOlist) {
						if( buildNo.equals(anAddr.getAddrBuild()) && floorNo == anAddr.getAddrFloor()) {
							roomList.add(anAddr.getAddrRoom());
						}
					}
					String floor = String.valueOf(floorNo);
					roomObj.addProperty(floor, gson.toJson(roomList)); // {"1":"[1,2,3,4,5]", "2":"[1,2,3,4,5]"}
				}
				// "addrRoom":{"1":"[1,2,3,4,5]","2":"[1,2,3,4,5]"}
				buildObj.add("addrRoom", roomObj); 
				/* {"A":{"addrFloor":"[1,2,3,4,5]","addrRoom":{"1":"[1,2,3,4,5]","2":"[1,2,3,4,5]"}},
					"B:{......},
					}
				*/
				addrDataObj.add(buildNo, buildObj); 
			}
			/*
			  {"addrBuild":"[\"A\",\"B\",\"C\",\"D\"]",
				  	"A":{"addrFloor":"[1,2]",
				  		 "addrRoom":{"1":"[1,2,3,4,5]",
				  					 "2":"[1,2,3,4,5]"}
				  		},
				  	"B":{.....},
			  }
			 */
			//System.out.println(addrDataObj.toString()); (印出的樣子如上面的註解所示)
			res.getWriter().write(addrDataObj.toString());
		}
		
		
		//************************ 住戶帳號管理(後台) or 註冊：新增住戶資料 ************************
		if("addMemForBackEnd".equals(action) || "register".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8");
			JsonObject returnJobject = new JsonObject();
			
			String memAcct = req.getParameter("memAcct");
			String acctReg = "^[a-zA-Z0-9]{6,25}$";
			if(memAcct.length() == 0 ) {
				returnJobject.addProperty("errAcct", "帳號欄位請勿空白");
			}else if(!memAcct.matches(acctReg)) {
				returnJobject.addProperty("errAcct", "帳號只能是英文字母、數字，且長度必須在6到25之間");
			}else {
				MemberService memSvs = new MemberService();
				for(MemberVO aMem: memSvs.getAll()) {
					if( memAcct.equals(aMem.getMemAcct())) {
						returnJobject.addProperty("errAcct", "已有人使用此住戶帳號");
					}
				}
			}
			
			String memPwd = req.getParameter("memPwd");
			String pwdReg = "^[a-zA-Z0-9]{8,25}$";
			if( memPwd.length() == 0 ) { 
				returnJobject.addProperty("errPwd", "密碼欄位請勿空白");
			}else if(!memPwd.trim().matches(pwdReg)) {
				returnJobject.addProperty("errPwd", "密碼只能是英文字母、數字，且長度必須在8到25之間");
			}
			
			String memName = req.getParameter("memName");
			String nameReg = "^[\u4e00-\u9fa5]{2,50}$";
			if( memName.length() == 0 ) {
				returnJobject.addProperty("errName", "姓名欄位請勿空白");
			} else if(!memName.matches(nameReg)) {
				returnJobject.addProperty("errName", "姓名只能是中文，且長度必需在2到50之間");
			}

			java.sql.Date memBirthday = null;
			try {
				memBirthday = java.sql.Date.valueOf(req.getParameter("memBirthday"));
			} catch (IllegalArgumentException e) {  //如果memBirthday為空字串，就會拋出此例外
				//memBirthday=new java.sql.Date(System.currentTimeMillis()); 
				returnJobject.addProperty("errBirthday","請選擇生日日期!");
			}
			
			String memId = req.getParameter("memId");
			String idReg = "^[a-zA-Z][12]\\d{8}$";
			if( memId == null || memId.trim().length() == 0 ) {
				returnJobject.addProperty("errId", "身分證欄位請勿空白");
			}else if(!memId.trim().matches(idReg)) {
				returnJobject.addProperty("errId", "不符合身分證字號格式");
			}
			
			String memSex = req.getParameter("memSex");
			//System.out.println("memSex: "+memSex);
			if( "undefined".equals(memSex) ) {  // radio單選，若沒選擇，會是"undefined"字串
				returnJobject.addProperty("errSex", "請選擇性別");
			} 
			
			String memPhone = req.getParameter("memPhone");
			String phoneReg = "^(09){1}\\d{8}$";
			if( memPhone.length() == 0 ) {
				returnJobject.addProperty("errPhone", "聯絡電話欄位請勿空白");
				
			}else if( !memPhone.matches(phoneReg) ) {
				returnJobject.addProperty("errPhone", "聯絡電話不符合格式，格式如：0912345678");
				
			}
			
			
			String memEmail = req.getParameter("memEmail");
			// \p{Alpha} -> [\p{Lower}\p{Upper}]
			// \w -> [a-zA-Z_0-9]
			String emailReg = "^\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}$";
			if(memEmail == null || memEmail.trim().length() == 0 ) {
				returnJobject.addProperty("errEmail", "信箱欄位請勿空白");
			}else if( !memEmail.trim().matches(emailReg) ){
				returnJobject.addProperty("errEmail", "信箱不符合格式");
			}
			
			String memBuild = req.getParameter("memBuild");
			String memFloor = req.getParameter("memFloor");
			String memRoom = req.getParameter("memRoom");
			Integer addrNo = null;
			if( "".equals(memBuild) || "".equals(memFloor) || memFloor == null || "".equals(memRoom) || memRoom == null) {
				returnJobject.addProperty("errAddr", "棟號、樓號、房號皆要選擇");
				
			}else {
				AddressService AddrSvc = new AddressService();
				List<AddressVO> addrList = AddrSvc.getAll();
				for(AddressVO oneAddr: addrList) {
					if( memBuild.equals(oneAddr.getAddrBuild()) && 
					    memFloor.equals( String.valueOf(oneAddr.getAddrFloor()) ) &&
					    memRoom.equals( String.valueOf(oneAddr.getAddrRoom()) ) ) 
					{ 
						addrNo = oneAddr.getAddrNo(); //取得使用者選擇的 棟、樓、房 的 地址編號
					}
				}
				MemberService memSvs = new MemberService();
				for(MemberVO aMem: memSvs.getAll()) {
					if( addrNo.equals(aMem.getAddrNo()) && aMem.getMemState() == 0) {
						returnJobject.addProperty("errAddr", "錯誤！此房號已有住戶");
					}
				}
				
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
				for(int i = 0; i < famMemAryList.size(); i++) {
					if(!famMemAryList.get(i).matches(famNameReg)) {
						returnJobject.addProperty("errFams", "同住家人姓名只能是中文，且長度必需在2到50之間");
					}
				}
			}
			
			//住戶狀態
			//String memState = req.getParameter("memState");
			
			//帳號創建時間
			Timestamp acctCreatetime = Timestamp.valueOf(LocalDateTime.now());
			
			Part part = req.getPart("memPhoto");
			InputStream is = null;
			byte[] memPhoto = null;
			String filename = getFileNameFromPart(part);
			System.out.println(filename);
			//如果沒有上傳圖片filename會是memPhoto，印出的header資訊為：header=form-data; name="memPhoto"
			//若有上傳圖片，印出的header資訊為：header=form-data; name="memPhoto"; filename="alert.png"
			if( "memPhoto".equals(filename)) {
				//returnJobject.addProperty("errPhoto", "需上傳照片");
				System.out.println("無照片，使用預設圖片存入資料庫");
				is = getServletContext().getResourceAsStream("/front-end/mem/images/user.png");
				memPhoto = new byte[is.available()]; //準備好跟該圖片剛好大小的水桶
				is.read(memPhoto); //將讀入的位元資料放入水桶
				is.close();
			}else {
				System.out.println("有照片");
				is = part.getInputStream();
				memPhoto = new byte[is.available()]; //準備好跟該圖片剛好大小的水桶
				is.read(memPhoto); //將讀入的位元資料放入水桶
				is.close();
			}
			
			System.out.println("returnJobject(是否有驗證錯誤): " + returnJobject.toString());
			//原本老師的gson-2.2.4.jar沒有這個方法（ size() ），後來我換版本2.8.6就有了～
			//以上有任何一個錯，returnMsgs的長度就不等於0
			if(returnJobject.size() != 0) {
				returnJobject.addProperty("msg","errorMsgs");
				res.getWriter().write(returnJobject.toString());
				return; //回傳錯誤訊息，並結束程式
			}
			
			/*************************** 驗證完成，都沒有錯誤，開始新增住戶資料 ***************************/
			//沒有寫同住家人者，家人列表為空
			if(famMemAryList.isEmpty()) {
				MemberService memSvc = new MemberService();
				MemberVO memberVO = memSvc.addMem(memAcct, memPwd, memName, memId, memSex, memEmail, 
						memBirthday, addrNo, memPhoto, memPhone, acctCreatetime);
				if(memberVO == null) {
					returnJobject.addProperty("msg","fail");
					res.getWriter().write(returnJobject.toString());
					return; //回傳錯誤訊息，並結束程式
					
				}else {
					returnJobject.addProperty("msg","success");
					res.getWriter().write(returnJobject.toString());
					return;
				}
				
			}else {
				MemberService memSvc = new MemberService();
				MemberVO memberVO =  memSvc.addMemWithFamMems(memAcct, memPwd, memName, 
						memId, memSex, memEmail,
						memBirthday, addrNo, memPhoto, 
						memPhone, acctCreatetime, famMemAryList);
				if(memberVO == null) {
					returnJobject.addProperty("msg","fail");
					res.getWriter().write(returnJobject.toString());
					return; //回傳錯誤訊息，並結束程式
					
				}else {
					returnJobject.addProperty("msg","success");
					res.getWriter().write(returnJobject.toString());
					return;
				}
			}
			
		}
		
		//****** 住戶帳號管理(後台)：編輯住戶資料，按下「編輯」，列出該住戶資料在燈箱上 *******
		if("getOneMemFroBackEnd".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8");
			JsonObject returnObject = new JsonObject();
			String memAcct = req.getParameter("memAcct");
			
			MemberService memSvc = new MemberService();
			MemberVO memberVO = memSvc.getOneMem(memAcct);
			returnObject.addProperty("memAcct", memAcct);
			returnObject.addProperty("memPwd", memberVO.getMemPwd());
			returnObject.addProperty("memName", memberVO.getMemName());
			
			java.sql.Date memBirthday = memberVO.getMemBirthday();
			returnObject.addProperty("memBirthday", memBirthday.toString());
			
			returnObject.addProperty("memId", memberVO.getMemId());
			returnObject.addProperty("memSex", memberVO.getMemSex());
			returnObject.addProperty("memPhone", memberVO.getMemPhone());
			returnObject.addProperty("memEmail", memberVO.getMemEmail());
			
			AddressService addrSvc = new AddressService();
			AddressVO addrVO = addrSvc.getOneAddr(memberVO.getAddrNo());
			returnObject.addProperty("addrBuild", addrVO.getAddrBuild());
			returnObject.addProperty("addrFloor", addrVO.getAddrFloor());
			returnObject.addProperty("addrRoom", addrVO.getAddrRoom());
			
			FamilyMemberService famMemSvc = new FamilyMemberService();
			List<FamilyMemberVO> famMemList = famMemSvc.getAll();
			List<String> famNameList = new ArrayList<String>();
			for(FamilyMemberVO aFam : famMemList) {
				if( memAcct.equals(aFam.getMemAcct()) ) {
					famNameList.add(aFam.getFamMemName());
				}
			}
			Gson gson = new Gson();
			String famMems = gson.toJson(famNameList);
			returnObject.addProperty("famMems", famMems);
			
			Integer memState = memberVO.getMemState();
			returnObject.addProperty("memState", memState.toString());
			
			Timestamp ts = memberVO.getAcctCreatetime();
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String acctCreatTime = df.format(ts);
			returnObject.addProperty("acctCreatTime", acctCreatTime);
			
			res.getWriter().write(returnObject.toString());
			
		}
		
		
		//****** 住戶帳號管理(後台)：編輯住戶資料，按下「確定修改」，驗證資料是否有誤 *******
		if("updateMemForBackEnd".equals(action)) {
			res.setContentType("text/plain; charset=UTF-8");
			JsonObject returnJobject = new JsonObject();
			String memAcct = req.getParameter("memAcct");
			
			String memPwd = req.getParameter("memPwd");
			String pwdReg = "^[a-zA-Z0-9]{8,25}$";
			if( memPwd.length() == 0 ) { 
				returnJobject.addProperty("errPwd", "密碼欄位請勿空白");
			}else if(!memPwd.trim().matches(pwdReg)) {
				returnJobject.addProperty("errPwd", "密碼只能是英文字母、數字，且長度必須在8到25之間");
			}
			
			String memName = req.getParameter("memName");
			String nameReg = "^[\u4e00-\u9fa5]{2,50}$";
			if( memName.length() == 0 ) {
				returnJobject.addProperty("errName", "姓名欄位請勿空白");
			} else if(!memName.matches(nameReg)) {
				returnJobject.addProperty("errName", "姓名只能是中文，且長度必需在2到50之間");
			}

			java.sql.Date memBirthday = null;
			try {
				memBirthday = java.sql.Date.valueOf(req.getParameter("memBirthday"));
			} catch (IllegalArgumentException e) {  //如果memBirthday為空字串，就會拋出此例外
				//memBirthday=new java.sql.Date(System.currentTimeMillis()); 
				returnJobject.addProperty("errBirthday","請選擇生日日期!");
			}
			
			String memId = req.getParameter("memId");
			String idReg = "^[a-zA-Z][12]\\d{8}$";
			if( memId == null || memId.trim().length() == 0 ) {
				returnJobject.addProperty("errId", "身分證欄位請勿空白");
			}else if(!memId.trim().matches(idReg)) {
				returnJobject.addProperty("errId", "不符合身分證格式");
			}
			
			String memSex = req.getParameter("memSex");
			
			String memPhone = req.getParameter("memPhone");
			String phoneReg = "^(09){1}\\d{8}$";
			if( memPhone.length() == 0 ) {
				returnJobject.addProperty("errPhone", "聯絡電話欄位請勿空白");
				
			}else if( !memPhone.matches(phoneReg) ) {
				returnJobject.addProperty("errPhone", "聯絡電話不符合格式，格式如：0912345678");
				
			}
			
			String memEmail = req.getParameter("memEmail");
			// \p{Alpha} -> [\p{Lower}\p{Upper}]
			// \w -> [a-zA-Z_0-9]
			String emailReg = "^\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}$";
			if(memEmail == null || memEmail.trim().length() == 0 ) {
				returnJobject.addProperty("errEmail", "信箱欄位請勿空白");
			}else if( !memEmail.trim().matches(emailReg) ){
				returnJobject.addProperty("errEmail", "信箱不符合格式");
			}
			
			String memBuild = req.getParameter("memBuild");
			String memFloor = req.getParameter("memFloor");
			String memRoom = req.getParameter("memRoom");
			System.out.println(memBuild);
			System.out.println(memFloor);
			System.out.println(memRoom);
			Integer addrNo = null;
			if( "".equals(memBuild) || "".equals(memFloor) || memFloor == null || "".equals(memRoom) || memRoom == null) {
				returnJobject.addProperty("errAddr", "棟號、樓號、房號皆要選擇");
				
			}else {
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMem(memAcct);
				Integer originalAddrNo = memVO.getAddrNo(); 
	
				AddressService AddrSvc = new AddressService();
				List<AddressVO> addrList = AddrSvc.getAll();
				for(AddressVO oneAddr: addrList) {
					if( memBuild.equals(oneAddr.getAddrBuild()) && 
					    memFloor.equals( String.valueOf(oneAddr.getAddrFloor()) ) &&
					    memRoom.equals( String.valueOf(oneAddr.getAddrRoom()) ) ) 
					{ 
						addrNo = oneAddr.getAddrNo(); //取得使用者選擇的 棟、樓、房 的 地址編號
					}
				}
				
				/* 如果 原來的地址編號 跟 修改後的地址編號不相同 要檢查該地址編號是否有住戶。
				 * 如果 原來的地址編號 跟 修改後的地址編號相同 則維持原來的送到資料庫。
				 */
				if(!originalAddrNo.equals(addrNo)) {
					MemberService memSvs = new MemberService();
					for(MemberVO aMem: memSvs.getAll()) {
						if( addrNo.equals(aMem.getAddrNo()) && aMem.getMemState() == 0) {
							returnJobject.addProperty("errAddr", "錯誤！此房號已有住戶");
						}
					}
					
				}
				
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
				for(int i = 0; i < famMemAryList.size(); i++) {
					if(!famMemAryList.get(i).matches(famNameReg)) {
						returnJobject.addProperty("errFams", "同住家人姓名只能是中文，且長度必需在2到50之間");
					}
				}
			}
			
			//住戶狀態
			String memState = req.getParameter("memState");
			
			//照片上傳
			Part part = req.getPart("memPhoto");
			InputStream is = null;
			byte[] memPhoto = null;
			String filename = getFileNameFromPart(part);
			System.out.println(filename);
			//如果沒有上傳圖片filename會是memPhoto，印出的header資訊為：header=form-data; name="memPhoto"
			//若有上傳圖片，印出的header資訊為：header=form-data; name="memPhoto"; filename="alert.png"
			if( "memPhoto".equals(filename)) {
				System.out.println("無照片");
				MemberService memSvc = new MemberService();
				MemberVO memberVO = memSvc.getOneMem(memAcct);
				memPhoto = memberVO.getMemPhoto(); //無上傳照片，就存原本的照片進資料庫

			}else {
				System.out.println("有照片");
				is = part.getInputStream();
				memPhoto = new byte[is.available()]; //準備好跟該圖片剛好大小的水桶
				is.read(memPhoto); //將讀入的位元資料放入水桶
				is.close();
			}
			
			System.out.println("returnJobject(是否有驗證錯誤): " + returnJobject.toString());
			//以上有任何一個錯，returnMsgs的長度就不等於0
			if(returnJobject.size() != 0) {
				returnJobject.addProperty("msg","errorMsgs");
				res.getWriter().write(returnJobject.toString());
				return; //回傳錯誤訊息，並結束程式
			}
			
			/*************************** 驗證完成，都沒有錯誤，開始修改住戶資料 ***************************/
			MemberService memberSvc = new MemberService();
			MemberVO returnMember = memberSvc.updateMemWithFamMems(memPwd, memName, memId, memSex, memEmail, memBirthday, 
					addrNo, memPhoto, memPhone, Integer.parseInt(memState), memAcct, famMemAryList);	
			if(returnMember != null) {
				returnJobject.addProperty("msg","success");
				res.getWriter().write(returnJobject.toString());
				return; 
			}else {
				returnJobject.addProperty("msg","fail");
				res.getWriter().write(returnJobject.toString());
				return; 
			}
			
		}
		
//		//****** 住戶帳號管理(後台)：刪除住戶資料 *******
//		// 不能刪除住戶資料，因為很多其他表會關聯到住戶帳號(FK)！！！
//		if("deleteMemForBackEnd".equals(action)) {
//			res.setContentType("text/plain; charset=UTF-8");
//			String memAcct = req.getParameter("memAcct");
//			
//			//刪除住戶帳號時，如果該住戶帳號有家人，要先刪除家人，才能刪除住戶帳號
//			FamilyMemberService famMemSvc = new FamilyMemberService();
//			Integer affectedRows = famMemSvc.deleteFamsWithMemAcct(memAcct);
//			
//			if( affectedRows!=null ) {
//				JsonObject obj = new JsonObject();
//				obj.addProperty("msg", "success");
//				obj.addProperty("memAcct", memAcct);
//				res.getWriter().write(obj.toString());
//				return;
//				
//			}else {
//				JsonObject obj = new JsonObject();
//				obj.addProperty("msg", "fail");
//				res.getWriter().write(obj.toString());
//				return;
//			}
//			
//		
//			
//		}
		
		//************ 住戶中心首頁(前台)：待辦事項，列出所有待辦事項 *************
		if("getAllToDoList".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				//jedis = new Jedis("localhost", 6379);
				jedis = pool.getResource();
				String user_id = req.getParameter("user_id");
				String jArrayStr = jedis.get("user_id:"+ user_id);
				//如果Redis資料庫沒有該帳號的資料，就幫該用戶創一個，並放入空的JsonArray
				if(jArrayStr == null) { 
					JsonArray ja = new JsonArray();
					jedis.set("user_id:"+ user_id, ja.toString());
					res.getWriter().write(ja.toString());
					return;
				}
				
				res.getWriter().write(jArrayStr);
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：新增待辦事項 *******************
		if("addToDoList".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				Gson gson = new Gson();
				
				String user_id = req.getParameter("user_id"); 
				String name = req.getParameter("name"); //待辦事項的文字
				JsonObject jObj = new JsonObject(); //裝新增的待辦事項
				
				// 生成不重複的item_id給新增的待辦事項
				String jArrayStr = jedis.get("user_id:"+ user_id);
				JsonArray jsonArray = gson.fromJson(jArrayStr, JsonArray.class);
			    List<Integer> idList = new ArrayList<Integer>();
				for(JsonElement element : jsonArray) {
					JsonObject jObject = element.getAsJsonObject();
					Integer item_id = jObject.get("item_id").getAsInt();
					idList.add(item_id); //蒐集 新增前資料庫中的所有待辦事項的item_id
				}			
				Integer num = null;
				while(true){
					num = (int)(Math.random()*1000) + 1;
					//如果該數字無包含在idList的集合中，就將該數字當作新增的待辦事項的item_id
					if(!idList.contains(num)) {  
						jObj.addProperty("item_id", num);
						break;
					}
				}
				
				jObj.addProperty("name", name);
				jObj.addProperty("star", 0);
				jObj.addProperty("sort", 1);
				jsonArray.add(jObj); //將新增的待辦事項加入裝所有待辦事項的json陣列
				jedis.set("user_id:"+ user_id, jsonArray.toString()); //存入Redis
				res.getWriter().write(jObj.toString());
				
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：待辦事項排序 *******************
		if("toDoListReload".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				
				String user_id = req.getParameter("user_id");
				String sort_item = req.getParameter("data"); //為欲更新的item_id 與 排序數值
				
				Gson gson = new Gson();
				JsonArray sortItemjArray = gson.fromJson(sort_item, JsonArray.class);
				
				String jArrayStr = jedis.get("user_id:"+ user_id); //從Redis取出所有的待辦事項
				JsonArray jArrayFromRedis = gson.fromJson(jArrayStr, JsonArray.class);				
				
				JsonObject retrunJobj = new JsonObject();
				for(JsonElement ele1 : sortItemjArray) {
					JsonObject jObj1 = ele1.getAsJsonObject();
					Integer jObj1_item_id = jObj1.get("item_id").getAsInt();  //要更新的該筆事項的id
					Integer jObj1_sort = jObj1.get("sort").getAsInt(); //該筆事項的 新的排序
					for(JsonElement ele2 : jArrayFromRedis) {
						JsonObject jObj2 = ele2.getAsJsonObject(); 
						Integer jObj2_item_id = jObj2.get("item_id").getAsInt();
						if(jObj1_item_id.equals(jObj2_item_id)) {
							jObj2.addProperty("sort", jObj1_sort); //覆蓋原本的sort(排序值)
							break;
						}
						
					}
				}
				//System.out.println(jArrayFromRedis.toString());
				
				// 將sort由小到大排序
				List<JsonObject> jObjList = new ArrayList<>();
				for(JsonElement aJobj: jArrayFromRedis) {
					jObjList.add( aJobj.getAsJsonObject() );
				}
				ToDoListComparator c = new ToDoListComparator();
				jObjList.sort(c); //使用比較器進行排序
				//System.out.println(jObjList.toString());
				
				JsonArray jAry = gson.fromJson(jObjList.toString(), JsonArray.class);
				
				jedis.set("user_id:"+ user_id, jObjList.toString()); //將排序好的結果寫入Redis資料庫
				retrunJobj.addProperty("msg", "update sort success");
				retrunJobj.add("data", jAry);
				res.getWriter().write(retrunJobj.toString());
				
			} finally {
				if(jedis!=null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：刪除單筆待辦事項 *******************
		if("delOneToDo".equals(action)) {		
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				res.setContentType("text/plain; charset=UTF-8");
				String user_id = req.getParameter("user_id");
				String item_id = req.getParameter("item_id"); //欲刪除的待辦事項的item_id
				Gson gson = new Gson();
				JsonObject returnJobj = new JsonObject();
				
				// 從所有待辦事項(jArrayFromRedis)中，移除欲刪除的待辦事項
				int flag = 0;
				String jArrayStr = jedis.get("user_id:"+ user_id);  //從Redis取出所有的待辦事項
				//System.out.println(jArrayStr);
				JsonArray jArrayFromRedis = gson.fromJson(jArrayStr, JsonArray.class);	
				for(int i = 0; i < jArrayFromRedis.size(); i++) {
					JsonObject jObj = jArrayFromRedis.get(i).getAsJsonObject();
					String itemId = jObj.get("item_id").getAsString();
					if(itemId.equals(item_id)) {
						//System.out.println("found");
						jArrayFromRedis.remove(i); //移除欲刪除的待辦事項
						flag = 1;
					}
				}
				
				if(flag == 1) {
					jedis.set("user_id:"+ user_id, jArrayFromRedis.toString()); //將刪除後的結果寫入Redis資料庫
					returnJobj.addProperty("msg", "delete success");
					returnJobj.add("data", jArrayFromRedis); //回傳剩下的待辦事項
					//System.out.println(jArrayFromRedis.toString());
					res.getWriter().write(returnJobj.toString());
				}else {
					returnJobj.addProperty("msg", "item id not found");
					res.getWriter().write(returnJobj.toString());
				}
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：清空所有待辦事項 *******************
		if("delAllToDoList".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				String user_id = req.getParameter("user_id");
				JsonArray jArray = new JsonArray();
				//用空的JsonArray覆蓋原來在資料庫中所有的待辦事項
				jedis.set("user_id:" + user_id, jArray.toString()); 
				
				JsonObject returnObj = new JsonObject();
				returnObj.addProperty("msg", "delete all success");
				returnObj.add("data", jArray);
				res.getWriter().write(returnObj.toString());
				
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：更新單筆待辦事項的文字內容 *******************
		if("updateToDoList".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				Gson gson = new Gson();
				
				String user_id = req.getParameter("user_id");
				String item_id = req.getParameter("item_id");
				String name = req.getParameter("name"); //欲更新的待辦事項文字內容
				String star = req.getParameter("star");
				String sort = req.getParameter("sort");
				
				String jArrayStr = jedis.get("user_id:"+ user_id);  //從Redis取出所有的待辦事項
				JsonArray jArrayFromRedis = gson.fromJson(jArrayStr, JsonArray.class);	
				for(int i = 0; i < jArrayFromRedis.size(); i++) {
					JsonObject jObj = jArrayFromRedis.get(i).getAsJsonObject();
					String itemId = jObj.get("item_id").getAsString();
					if(itemId.equals(item_id)) {
						jObj.addProperty("name", name);  //用新的文字內容覆蓋原本的待辦事項文字內容
					}
				}
				//將更新後的內容寫入Redis資料庫（會覆蓋原本的資料）
				String str = jedis.set("user_id:"+ user_id, jArrayFromRedis.toString()); 
				if(str != null) {
					JsonObject returnObj = new JsonObject();
					returnObj.addProperty("msg", "item update success");
					returnObj.addProperty("item_id", Integer.valueOf(item_id));
					returnObj.addProperty("name", name);
					returnObj.addProperty("star", Integer.valueOf(star));
					returnObj.addProperty("sort", Integer.valueOf(sort));
					res.getWriter().write(returnObj.toString());
				}

				
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：更新單筆待辦事項的星號 *******************
		if("updateStar".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				Gson gson = new Gson();
				String user_id = req.getParameter("user_id");
				String item_id = req.getParameter("item_id");
				String star = req.getParameter("star"); //新的星號重要性
				
				String jArrayStr = jedis.get("user_id:"+ user_id);  //從Redis取出所有的待辦事項
				JsonArray jArrayFromRedis = gson.fromJson(jArrayStr, JsonArray.class);	
				for(int i = 0; i < jArrayFromRedis.size(); i++) {
					JsonObject jObj = jArrayFromRedis.get(i).getAsJsonObject();
					String itemId = jObj.get("item_id").getAsString();
					if(itemId.equals(item_id)) {
						jObj.addProperty("star", Integer.valueOf(star)); //用新的星號重要性覆蓋舊的
					}
				}
				//將更新後的內容寫入Redis資料庫（會覆蓋原本的資料）
				String str = jedis.set("user_id:"+ user_id, jArrayFromRedis.toString()); 
				if( str!=null ) {
					JsonObject returnJobj = new JsonObject();
					returnJobj.addProperty("msg", "star update success");
					returnJobj.addProperty("item_id", Integer.valueOf(item_id));
					returnJobj.addProperty("star", Integer.valueOf(star));
					res.getWriter().write(returnJobj.toString());
				}
				
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
		}
		
		//****************** 住戶中心首頁(前台)：取得天氣資料 *******************
		if("getWeatherData".equals(action)) {
			Jedis jedis = null;
			try {
				res.setContentType("text/plain; charset=UTF-8");
				jedis = pool.getResource();
				Gson gson = new Gson();
				
				String tStr = jedis.get("weather:t"); //溫度
				JsonObject tObj1 = gson.fromJson(tStr, JsonObject.class);
				JsonArray tElementValue = tObj1.get("elementValue").getAsJsonArray();
				JsonObject tObj2 = tElementValue.get(0).getAsJsonObject();
				String temperature = tObj2.get("value").getAsString(); //溫度，例如：14
				//System.out.println("temperature: " + temperature);
				
				String wxStr = jedis.get("weather:wx"); //天氣現象
				JsonObject wxObj1 = gson.fromJson(wxStr, JsonObject.class);
				JsonArray wxElementValue = wxObj1.get("elementValue").getAsJsonArray();
				JsonObject wxObj2 = wxElementValue.get(0).getAsJsonObject();
				String phenomenon = wxObj2.get("value").getAsString(); //天氣現象，例如：陰
				//System.out.println("phenomenon: " + phenomenon);
				
				String popStr = jedis.get("weather:pop"); //6小時降雨機率
				JsonObject popObj1 = gson.fromJson(popStr, JsonObject.class);
				JsonArray popElementValue = popObj1.get("elementValue").getAsJsonArray();
				JsonObject popObj2 = popElementValue.get(0).getAsJsonObject();
				String rain = popObj2.get("value").getAsString(); //降雨機率，例如：20
				//System.out.println("rain: " + rain);
				
				JsonObject returnObj = new JsonObject();
				returnObj.addProperty("temperature", temperature);
				returnObj.addProperty("phenomenon", phenomenon);
				returnObj.addProperty("rain", rain);
				
				res.getWriter().write(returnObj.toString());
				
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
			return;
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
	
	// 用於前台的註冊、後台的新增住戶資料 的 預設圖片上傳 
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];  //fis.available()：獲得源頭的資料量大小
		fis.read(buffer);
		fis.close();
		return buffer;
	}

}

class ToDoListComparator implements Comparator<JsonObject>{
	public int compare(JsonObject j1, JsonObject j2) {
		//(比較大會回傳)正值*-1變負值，負值*-1變正值，0*-1還是0
		Integer val1 = j1.get("sort").getAsInt();
		Integer val2 = j2.get("sort").getAsInt();
		return val1.compareTo(val2); // Compares two Integer objects numerically.
	}
}
