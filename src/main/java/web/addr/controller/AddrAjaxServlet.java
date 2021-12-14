package web.addr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.addr.entity.AddressVO;
import web.addr.service.AddressService;


@WebServlet("/back-end/acct-addr/AddrAjaxServlet.do")
public class AddrAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}


	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		//給瀏覽器看的
		//這行有寫charset="UTF-8" 下面那行res.setCharacterEncoding("UTF-8")就可以不用寫了 通常只會寫res.setContentType(...)
		res.setContentType("text/plain; charset=UTF-8"); 
		//res.setCharacterEncoding("UTF-8");
		/* 收到的請求的內容（請求內容的編碼會是 address.html檔設定的 <meta charset="UTF-8" />） 
		 * 因為經過網路傳輸 編碼會是西歐語系 資料會變成亂碼 
		 * 所以這邊要再設定編碼(要跟傳過來的內容的編碼相同）才不會是亂碼，設好編碼好，程式再做處理
		 */
		req.setCharacterEncoding("UTF-8"); 
		String action = req.getParameter("action");
		
		//****************** 列出所有地址資訊
		if("listAll".equals(action)) {
			AddressService addrSvc = new AddressService();
			List<AddressVO> list = addrSvc.getAll();
			Gson gson = new Gson();
			String data = gson.toJson(list);
			//System.out.println(data);
			//String str = new JSONArray(list).toString();
			//System.out.println(str);
			res.getWriter().write(data); // res.getWriter() -> 取得PrintWriter物件 -> 使用PrintWriter物件的write()，輸出資料給前端
			return; // 回傳資料給前端後，要中斷程式，不然仍會繼續往下執行
			
		}
		
		//System.out.println("hello");
		
		//****************** 新增
		if("insert".equals(action)) {
			//System.out.println("hello");
			Gson gson = new Gson();
			//接收前端傳來的資料，為json格式的字串
			String addr = req.getParameter("addr");
			JsonObject addrJsonObject = gson.fromJson(addr, JsonObject.class);
			String addrBuild = addrJsonObject.get("addrBuild").getAsString();
			Integer addrFloor = addrJsonObject.get("addrFloor").getAsInt();
			Integer addrRoom = addrJsonObject.get("addrRoom").getAsInt();
			
			AddressService addrSvc = new AddressService();
			//檢查有無新增重複的棟、樓、房，若有則回傳「重複」的訊息，並結束程式
			List<AddressVO> addrVOList = addrSvc.getAll();
			for(AddressVO anAddr: addrVOList) {
				if( anAddr.getAddrBuild().equals(addrBuild) && 
				    anAddr.getAddrFloor().equals(addrFloor) &&
				    anAddr.getAddrRoom().equals(addrRoom)) {
					addrJsonObject.addProperty("msg", "overlap");
					String addrStr = addrJsonObject.toString();
					res.getWriter().write(addrStr);
					return;
				}
			}
			//****************** 檢查完成開始新增資料 ******************//
			//當資料庫新增成功，會回傳自增主鍵值
			String addrNo = addrSvc.addAddr(addrBuild, addrFloor, addrRoom); 
			
			if(addrNo != null) {
				addrJsonObject.addProperty("addrNo", addrNo);
				addrJsonObject.addProperty("msg", "success");
				String addrStr = addrJsonObject.toString();
				//System.out.println(addrStr);
				res.getWriter().write(addrStr);
				return;
			}else {
				addrJsonObject.addProperty("msg","fail");
				String addrStr = addrJsonObject.toString();
				System.out.println(addrStr);
				res.getWriter().write(addrStr);
				return;
			}
	
		
		}
		
		//****************** 修改
		if("update".equals(action)) {
			//System.out.println("hello");
			Gson gson = new Gson();
			//接收前端傳來的資料，為json格式的字串
			String addr = req.getParameter("addr");
			JsonObject addrJsonObject = gson.fromJson(addr, JsonObject.class);
			Integer addrNo = addrJsonObject.get("addrNo").getAsInt();
			String addrBuild = addrJsonObject.get("addrBuild").getAsString();
			Integer addrFloor = addrJsonObject.get("addrFloor").getAsInt();
			Integer addrRoom = addrJsonObject.get("addrRoom").getAsInt();
			
			AddressService addrSvc = new AddressService();
			//檢查有無新增重複的棟、樓、房，若有則回傳「重複」的訊息，並結束程式
			List<AddressVO> addrVOList = addrSvc.getAll();
			for(AddressVO anAddr: addrVOList) {
				if( anAddr.getAddrBuild().equals(addrBuild) && 
				    anAddr.getAddrFloor().equals(addrFloor) &&
				    anAddr.getAddrRoom().equals(addrRoom)) {
					addrJsonObject.addProperty("msg", "overlap");
					String addrStr = addrJsonObject.toString();
					res.getWriter().write(addrStr);
					return;
				}
			}
			
			//****************** 檢查完成開始修改資料 ******************//
			//當資料庫修改成功，會回傳AddressVO的物件
			AddressVO addrVO = addrSvc.updateAddr(addrBuild, addrFloor, addrRoom, addrNo);
			if(addrVO != null) {
				addrJsonObject.addProperty("msg", "success");
				String addrStr = addrJsonObject.toString();
				//System.out.println(addrStr);
				res.getWriter().write(addrStr);
				return;
			}else {
				addrJsonObject.addProperty("msg","fail");
				String addrStr = addrJsonObject.toString();
				System.out.println(addrStr);
				res.getWriter().write(addrStr);
				return;
			}
			
		}
		
		//****************** 刪除
		if("delete".equals(action)) {
			//System.out.println("hello");
			String addrNo = req.getParameter("addrNo");
			//System.out.println(addrNo);
			AddressService addrSvc = new AddressService();
			Integer affectedRows = addrSvc.deleteAddr(new Integer(addrNo));
			if( affectedRows != null ) {
				JsonObject obj = new JsonObject();
				obj.addProperty("msg", "success");
				obj.addProperty("addrNo", addrNo);
				obj.addProperty("affectedRows", affectedRows.toString());
				res.getWriter().write(obj.toString());
				return;
			}else {
				JsonObject obj = new JsonObject();
				obj.addProperty("msg", "fail");
				res.getWriter().write(obj.toString());
				return;
			}
			
		}
		
		
		
	}

}
