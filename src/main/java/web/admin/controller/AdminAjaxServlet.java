package web.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.admin.entity.AdminVO;
import web.admin.service.AdminService;


@WebServlet("/acct-addr/AdminAjaxServlet.do")
public class AdminAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//****************** 列出所有管理員資料
		if("listAll".equals(action)) {
			AdminService adminSvs = new AdminService();
			List<AdminVO> list = adminSvs.getAll();
			Gson gson = new Gson();
			String data = gson.toJson(list);
			//System.out.println(data);
			PrintWriter out = res.getWriter();
			out.write(data);
			out.close();
			return;
		}
		
		//****************** 新增
		if("insert".equals(action)) {
			Gson gson = new Gson();
			String admin = req.getParameter("admin");
			JsonObject adminJsonObject = gson.fromJson(admin, JsonObject.class);
			String adminAcct = adminJsonObject.get("adminAcct").getAsString();
			String adminPwd = adminJsonObject.get("adminPwd").getAsString();
			String adminName = adminJsonObject.get("adminName").getAsString();
			String adminPhone = adminJsonObject.get("adminPhone").getAsString();
			Integer adminPos = adminJsonObject.get("adminPos").getAsInt();
			Integer adminState = adminJsonObject.get("adminState").getAsInt();
			
			AdminService adminSvc = new AdminService();
			//檢查有無新增重複的後台帳號，若有則回傳「重複」的訊息，並結束程式
			List<AdminVO> adminVOList = adminSvc.getAll();
			for(AdminVO anAdmin: adminVOList) {
				if( adminAcct.equals(anAdmin.getAdminAcct()) ) {
					adminJsonObject.addProperty("msg", "overlap");
					String adminStr = adminJsonObject.toString();
					PrintWriter out = res.getWriter();
					out.write(adminStr);
					out.close();
					return;
				}
			}
			//****************** 檢查完成開始新增資料 ******************//
			//當資料庫新增成功，會回傳AdminVO物件
			AdminVO adminVO = adminSvc.addAdmin(adminAcct, adminPwd, adminName, adminPos, adminState, adminPhone);
			if(adminVO != null) {
				adminJsonObject.addProperty("msg", "success");
				String adminStr = adminJsonObject.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			} else {
				adminJsonObject.addProperty("msg","fail");
				String adminStr = adminJsonObject.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			}
			
		}
		
		
		//****************** 修改
		if("update".equals(action)) {
			Gson gson = new Gson();
			String admin = req.getParameter("admin");
			JsonObject adminJsonObject = gson.fromJson(admin, JsonObject.class);
			String adminAcct = adminJsonObject.get("adminAcct").getAsString();
			String adminPwd = adminJsonObject.get("adminPwd").getAsString();
			String adminName = adminJsonObject.get("adminName").getAsString();
			String adminPhone = adminJsonObject.get("adminPhone").getAsString();
			Integer adminPos = adminJsonObject.get("adminPos").getAsInt();
			Integer adminState = adminJsonObject.get("adminState").getAsInt();
			
			AdminService adminSvc = new AdminService();
			//當資料庫修改成功，會回傳AdminVO物件
			AdminVO adminVO = adminSvc.updateAdmin(adminPwd, adminName, adminPos, adminState, adminPhone, adminAcct);
			if(adminVO != null) {
				adminJsonObject.addProperty("msg", "success");
				String adminStr = adminJsonObject.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			} else {
				adminJsonObject.addProperty("msg","fail");
				String adminStr = adminJsonObject.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			}
			
		}
		
		// ****************** 刪除
		if("delete".equals(action)) {
			String adminAcct = req.getParameter("adminAcct");
			AdminService adminSvc = new AdminService();
			Integer affectedRows = adminSvc.deleteAdmin(adminAcct);
			if( affectedRows != null ) {
				JsonObject obj = new JsonObject();
				obj.addProperty("msg", "success");
				obj.addProperty("adminAcct", adminAcct);
				obj.addProperty("affectedRows", affectedRows.toString());
				String adminStr = obj.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			}else {
				JsonObject obj = new JsonObject();
				obj.addProperty("msg", "fail");
				String adminStr = obj.toString();
				PrintWriter out = res.getWriter();
				out.write(adminStr);
				out.close();
				return;
			}
		}
		
		
	}

}
