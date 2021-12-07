package web.addr.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import web.addr.*;

import web.addr.entity.AddressVO;
import web.addr.service.AddressService;

public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//新增
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String addrBuild = req.getParameter("addrBuild");
				String addrBuildReg = "^[(A-Z)]{1}$";
				if(addrBuild == null || addrBuild.trim().length() ==0 ) {
					addrBuild = "0";
					errorMsgs.add("棟號: 請勿空白");
				} else if(!addrBuild.trim().matches(addrBuildReg)) {
					addrBuild = "0";
					errorMsgs.add("棟號: 只能是英文字母A~Z, 且長度必需為1");
				}
				
				String addrFloor = req.getParameter("addrFloor");
				String addrFloorReg = "^[(1-5)]{1}$";
				if(addrFloor == null || addrFloor.trim().length() == 0) {
					addrFloor = "0";
					errorMsgs.add("樓號: 請勿空白");
					
				} else if(!addrFloor.trim().matches(addrFloorReg)) {
					addrFloor = "0";
					errorMsgs.add("樓號: 只能是數字1~5, 且長度必需為1");
				}
				
				String addrRoom = req.getParameter("addrRoom");
				String addrRoomReg = "^[(1-5)]{1}$";
				if(addrRoom == null || addrRoom.trim().length() == 0) {
					errorMsgs.add("房號: 請勿空白");
				} else if(!addrRoom.trim().matches(addrRoomReg)) {
					errorMsgs.add("房號: 只能是數字1~5, 且長度必需為1");
				}
				
				AddressVO addressVO = new AddressVO();
				addressVO.setAddrBuild(addrBuild);
				addressVO.setAddrFloor(Integer.parseInt(addrFloor));
				addressVO.setAddrRoom(Integer.parseInt(addrRoom));
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("addressVO", addressVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/addr/addAddr.jsp"); 
					failureView.forward(req, res);
					return;			
				}
				
				AddressService addrSvc = new AddressService();
				addrSvc.addAddr(addrBuild, Integer.parseInt(addrFloor), Integer.parseInt(addrRoom));
				
				String url = "/addr/listAllAddress.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/addr/addAddr.jsp");
				failureView.forward(req, res);
			}
			
			
			
		}
		
	}

}
