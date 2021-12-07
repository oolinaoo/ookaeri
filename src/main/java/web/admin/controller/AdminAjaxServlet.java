package web.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/back-end/acct-addr/AdminAjaxServlet.do")
public class AdminAjaxServlet extends HttpServlet {
       
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//****************** 列出所有地址資訊
		if("listAll".equals(action)) {
			
			
		}
		
	}

}
