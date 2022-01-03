package web.mapReport.controller;

import web.mapReport.dao.Map_ReportDAO;
import web.mapReport.dao.Impl.Map_ReportDAOImpl;
import web.mapReport.entity.Map_ReportVO;
import web.mapReport.service.Map_ReportService;
import web.mapStoreInfo.entity.Map_Store_InfoVO;
import web.mapStoreInfo.service.Map_Store_InfoService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import web.mapMessage.entity.Map_MessageVO;
import web.mapMessage.service.Map_MessageService;

import web.mapReport.entity.Map_ReportVO;
import web.mapReport.service.Map_ReportService;

@WebServlet("/controller/reptServletController.do")
public class reptServletController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	int reptNO;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		System.out.println("送出指令:" + action);
		if("reportComments".equals(action))
		{
			String storeNo= req.getParameter("storeNo");
			if(storeNo.trim().isEmpty())
			{
				RequestDispatcher failure = req
						.getRequestDispatcher("/front-end/map/map_message/foodmap.jsp");
				failure.forward(req, res);
				return ;
			}
			
			int report=1;//0.不創造檢舉物件 1.創造檢舉物件
			String memAcct=req.getParameter("mem");//檢舉者帳號
			System.out.println("檢舉者帳號:" +memAcct);
//			String adminAcct="gary1";//管理者帳號
			String adminAcct=req.getParameter("admit");//管理者帳號
			System.out.println("管理者帳號:" +adminAcct);
			Integer messageNO =Integer.valueOf(req.getParameter("messageNO"));//被檢舉的留言編號
			System.out.println("被檢舉的留言編號:" +messageNO);
			String reptMessage= req.getParameter("reptMessage");//被檢舉原因
			System.out.println("被檢舉原因:" +reptMessage);
			Map_ReportService dao= new Map_ReportService();
			List<Map_ReportVO> allMes=dao.getAll();
			Iterator<Map_ReportVO> it =allMes.iterator();
			while(it.hasNext())
			{

				Map_ReportVO rept=it.next();
				if((rept.getMAP_MSG_NO().equals(messageNO)))
				{
					
					if(rept.getMAP_REPT_STATE()==0)
					{
						report=0;
						System.out.println("該留言有被檢舉的紀錄過且正在處理中");
						break;
						
					}
					if(rept.getMAP_REPT_STATE()==1)
					{
						report=1;
						System.out.println("該留言檢舉成功");
						break;
					}
					
					
				}
				
			}
			if(report==0)
			{
				RequestDispatcher failure = req
						.getRequestDispatcher("/front-end/map/map_message/foodmap.jsp");
				failure.forward(req, res);
				System.out.println("該留言正在處理");
				req.setAttribute("reportState", 0);
				return ;
			}
			
			if(report==1)
			{
				Map_ReportService serviceDao=new Map_ReportService();
				
				
				serviceDao.addMap_ReportDAO(reptNO, reptMessage,messageNO, memAcct, 0, adminAcct);
				RequestDispatcher success = req
						.getRequestDispatcher("/front-end/map/map_message/foodmap.jsp");
				success.forward(req, res);
				System.out.println("該留言檢舉成功");
				req.setAttribute("reportState", 1);
				return ;
			}
			
		}
		if("keepComment".equals(action))
		{
			int branch=0;
			Integer mapMassageNo = new Integer(req.getParameter("mapMassageNo"));
			String memAcct = req.getParameter("memAcct");
			String mapMessageContent = req.getParameter("mapMessageContent");
			Timestamp mapMessageTime=  Timestamp.valueOf(req.getParameter("mapMessageTime"));
			String mapStoreNo = req.getParameter("mapStoreNo");
			Map_MessageService Dao_Map_MessageService = new Map_MessageService();
			Dao_Map_MessageService.updateMap_MessageVO(mapMassageNo, mapStoreNo, memAcct, mapMessageContent, mapMessageTime, 0);
			Integer mapReptNo=null;
			String mapReptContent="";
			String admitAcct=""; 
			Map_ReportService Dao_Map_ReportService = new Map_ReportService();
			List<Map_ReportVO> allRept=Dao_Map_ReportService.getAll();
			Iterator It =allRept.iterator();
			while(It.hasNext())
			{
				Map_ReportVO rept=(Map_ReportVO) It.next();
				if(rept.getMAP_MSG_NO().equals(mapMassageNo)&&rept.getMAP_REPT_STATE()==0)
				{
					
						mapReptNo=rept.getMAP_REPT_NO();
						mapReptContent=rept.getMAP_REPT_CONTENT();
						admitAcct=rept.getADMIN_ACCT();
						branch=1;
						break;
					
				}
			}
			if(branch==1)
			{
				Dao_Map_ReportService.updateMap_ReportDAO(mapReptNo, mapReptContent, mapMassageNo, memAcct, 1, admitAcct);
				RequestDispatcher success = req.getRequestDispatcher("/back-end/map/mapRept.jsp");
				success.forward(req, res);
				return;
			}
			else
			{
				RequestDispatcher failure = req.getRequestDispatcher("/back-end/map/mapRept.jsp");
				failure.forward(req, res);
			}
		}
		if("downComment".equals(action))
		{
			int branch=0;
			Integer mapMassageNo = new Integer(req.getParameter("mapMassageNo"));
			String memAcct = req.getParameter("memAcct");
			String mapMessageContent = req.getParameter("mapMessageContent");
			Timestamp mapMessageTime=  Timestamp.valueOf(req.getParameter("mapMessageTime"));
			String mapStoreNo = req.getParameter("mapStoreNo");
			Map_MessageService Dao_Map_MessageService = new Map_MessageService();
			Dao_Map_MessageService.updateMap_MessageVO(mapMassageNo, mapStoreNo, memAcct, mapMessageContent, mapMessageTime, 1);
			Integer mapReptNo=null;
			String mapReptContent="";
			String admitAcct=""; 
			Map_ReportService Dao_Map_ReportService = new Map_ReportService();
			List<Map_ReportVO> allRept=Dao_Map_ReportService.getAll();
			Iterator It =allRept.iterator();
			while(It.hasNext())
			{
				Map_ReportVO rept=(Map_ReportVO) It.next();
				if(rept.getMAP_MSG_NO().equals(mapMassageNo)&&rept.getMAP_REPT_STATE()==0)
				{
				
						mapReptNo=rept.getMAP_REPT_NO();
						mapReptContent=rept.getMAP_REPT_CONTENT();
						admitAcct=rept.getADMIN_ACCT();
						branch=1;
						break;
					
				}
			}
			if(branch==1)
			{
				Dao_Map_ReportService.updateMap_ReportDAO(mapReptNo, mapReptContent, mapMassageNo, memAcct, 1, admitAcct);
				RequestDispatcher success = req.getRequestDispatcher("/back-end/map/mapRept.jsp");
				success.forward(req, res);
				return;
			}
			else
			{
				RequestDispatcher failure = req.getRequestDispatcher("/back-end/map/mapRept.jsp");
				failure.forward(req, res);
			}
		}		

}

	

}