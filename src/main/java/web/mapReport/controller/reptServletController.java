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
			Integer keyMessageNO = null;
			int report=1;//0.不創造檢舉物件 1.創造檢舉物件
			String reptAcct=req.getParameter("reptAcct");
			Integer messageNO =Integer.valueOf(req.getParameter("messageNO"));
			String reptMessage= req.getParameter("reptMessage");
			Map_ReportService dao= new Map_ReportService();
			List<Map_ReportVO> allMes=dao.getAll();
			Iterator<Map_ReportVO> it =allMes.iterator();
			while(it.hasNext())
			{

				Map_ReportVO rept=it.next();
				System.out.println("被檢舉的留言編號: "+messageNO);
				System.out.println("被檢舉的留言帳號: "+reptAcct);
				System.out.println(rept.getMAP_MSG_NO());
				System.out.println(rept.getMEM_ACCT());
				String MEM_ACCT=rept.getMEM_ACCT();
				Integer MAP_MSG_NO=rept.getMAP_MSG_NO();
				System.out.println(rept.getMAP_MSG_NO().getClass());
				System.out.println(messageNO.getClass());
				if((MAP_MSG_NO.equals(messageNO))&&(rept.getMEM_ACCT().equals(reptAcct)))
				
				{
					System.out.println("該留言被檢舉過");
					if(rept.getMAP_REPT_STATE()==0)
					{
						report=0;
						System.out.println("該留言被檢舉過且正在處理中");
						break;
						
					}
					else
					{
						report=1;
						System.out.println("該留言被檢舉過且已處理");
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
				
				String name="gary1";
				serviceDao.addMap_ReportDAO(reptNO, reptMessage,messageNO, reptAcct, 0, name);
				RequestDispatcher success = req
						.getRequestDispatcher("/front-end/map/map_message/foodmap.jsp");
				success.forward(req, res);
				System.out.println("該留言檢舉成功");
				req.setAttribute("reportState", 1);
				return ;
			}
			
		}

		

}

	private Integer Integer(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}