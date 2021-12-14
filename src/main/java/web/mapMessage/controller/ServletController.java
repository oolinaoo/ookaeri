package web.mapMessage.controller;

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

import web.mapFavorite.dao.Map_My_FavoriteDAO;
import web.mapFavorite.dao.impl.Map_My_FavoriteDAOImpl;
import web.mapFavorite.entity.Map_My_FavoriteVO;
import web.mapFavorite.service.Map_My_FavoriteService;
import web.mapMessage.service.Map_MessageService;
import web.mapStoreInfo.entity.Map_Store_InfoVO;
import web.mapStoreInfo.service.Map_Store_InfoService;

@WebServlet("/controller/ServletController.do")
public class ServletController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	int messageId;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		System.out.println("送出指令:" + action);

		if ("inputToFav".equals(action)) {
			String no = req.getParameter("storeNo");
//			String mem = req.getParameter("mem");
			String mem = "gina2";
			
			if (no.trim().isEmpty()) {
				System.out.println("請選擇店家");
				RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
				failure.forward(req, res);
				return;
			} else {
				String branch = "r";// r代表資料沒有重複，f代表資料有重複。
				System.out.println("我的Branch: " + branch);
				Map_My_FavoriteService dao = new Map_My_FavoriteService();
				List<Map_My_FavoriteVO> allMapFav = dao.getAll();
				Iterator<Map_My_FavoriteVO> it = allMapFav.iterator();
				String storeNo = "";
				String memAcct = "";
				while (it.hasNext()) {

					String str = "有進入迴圈";
					System.out.println(str);
					Map_My_FavoriteVO mapFav = it.next();
					storeNo = mapFav.getMAP_STORE_NO();
					memAcct = mapFav.getMEM_ACCT();
					System.out.println(storeNo);
					System.out.println(memAcct);
					System.out.println(no);
					System.out.println(mem);

					if (storeNo.equals(no) && memAcct.equals(mem)) {
						System.out.println("資料庫裡的商家編號: " + no);
						System.out.println("資料庫裡的會員帳號:  " + mem);
						branch = "f";
						break;
					}

				}
				if (branch.equals("f")) {
					System.out.println("資料有重複");
					RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
					failure.forward(req, res);
					return;
				} else {
					System.out.println("資料沒有重複");
					dao.addMap_My_FavoriteVO(no, mem);
					RequestDispatcher success = req.getRequestDispatcher("/map_message/foodmap.jsp");
					success.forward(req, res);
					return;
				}

			}

		}
		if("deleteFromFav".equals(action))
		{
			String no = req.getParameter("storeNo");
//			String mem = req.getParameter("mem");
			String mem = "gina2";
			if(no.trim().isEmpty())
			{
				System.out.println("請選擇店家");
				RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
				failure.forward(req, res);
				return;
			}
			else
			{
				try
				{
					
			
					Map_My_FavoriteDAO dao = new Map_My_FavoriteDAOImpl();
					dao.delete(no,mem);
					RequestDispatcher success = req
							.getRequestDispatcher("/map_message/foodmap.jsp");
					success.forward(req, res);
				}
				catch(Exception e)
				{
					System.out.println("該筆資料已刪除");
					RequestDispatcher failure = req
							.getRequestDispatcher("/map_message/foodmap.jsp");
					failure.forward(req, res);
				}
			}
		}

		if ("confirm".equals(action)) {
			String str = req.getParameter("storeInfo");
			System.out.println("從前端送來的商家資料: " + str);
			String storeNo;
			Map_Store_InfoService dao = new Map_Store_InfoService();
			List<Map_Store_InfoVO> allStoreInfo = dao.getAll();
			Iterator<Map_Store_InfoVO> it = allStoreInfo.iterator();

			while (it.hasNext()) {
				String storeName = "";
				String storeAddr = "";
				String completeStoreInfo = "";
				Map_Store_InfoVO storeInfo = it.next();
				storeName = storeInfo.getMAP_STORE_NAME();
				storeAddr = storeInfo.getMAP_STORE_ADDR();
				completeStoreInfo = storeName + storeAddr;
				System.out.println("迭代器裡的商家資料: " + completeStoreInfo);

				if (str.equals(completeStoreInfo)) {

					System.out.println("前端送來的商家資料和資料庫抓取的商家資料Match成功");
					HttpSession session = req.getSession();
					session.setAttribute("storeINFO", str);// 將商家資訊存在setAttribute並藉由forward回傳給JSP
					storeNo = storeInfo.getMAP_STORE_NO();
					session.setAttribute("storeNoUsedInCommentArea", storeNo);// 將商家編號存在setAttribute並藉由forward回傳給JSP

					break;
				}
			}
			RequestDispatcher success = req.getRequestDispatcher("/map_message/foodmap.jsp");
			success.forward(req, res);

		}
		if ("inputComment".equals(action)) {
			String storeNoUsedInInputComment = req.getParameter("storeNoUsedInInputComment");
			String commentContent = req.getParameter("comment");
			if (storeNoUsedInInputComment.trim().isEmpty()) {
				RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
				failure.forward(req, res);
				System.out.println("沒有選擇店家");
				return;
			}
			if (commentContent.trim().isEmpty()) {
				RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
				failure.forward(req, res);
				System.out.println("沒有留言");
				return;

			} else {
				Map_MessageService dao = new Map_MessageService();
				Timestamp MAP_MSG_TIME = new Timestamp(123456565);
				String name = "gina1";
				dao.addMap_MessageVO(messageId, storeNoUsedInInputComment, name, commentContent, MAP_MSG_TIME, 0);
				RequestDispatcher good = req.getRequestDispatcher("/map_message/foodmap.jsp");
				good.forward(req, res);
				System.out.println(storeNoUsedInInputComment);
				System.out.println("成功輸入");
				return;
			}
		}

		if ("searchStore".equals(action)) {
			String str = req.getParameter("search");
			int branch = 0;//
			String storeNo;
			Map_Store_InfoService dao = new Map_Store_InfoService();
			List<Map_Store_InfoVO> allStoreInfo = dao.getAll();
			Iterator<Map_Store_InfoVO> it = allStoreInfo.iterator();

			if (str.trim().isEmpty()) {

				String errorStoreName = "請輸入商家名稱";
				req.setAttribute("errorMessage", errorStoreName);
				RequestDispatcher backToJsp = req.getRequestDispatcher("/map_message/foodmap.jsp");
				backToJsp.forward(req, res);
				return;

			} else {

				while (it.hasNext()) {
					String storeName = "";
					String storeAddr = "";
					String completeStoreInfo = "";
					Map_Store_InfoVO storeInfo = it.next();
					storeName = storeInfo.getMAP_STORE_NAME();
					storeAddr = storeInfo.getMAP_STORE_ADDR();
					System.out.println("迭代器裡的商家名稱: " + storeName);

					if (str.equals(storeName)) {

						System.out.println("前端送來的商家名稱和資料庫抓取的商家名稱Match成功");
						HttpSession session = req.getSession();
						completeStoreInfo = storeName + storeAddr;
						session.setAttribute("storeINFO", completeStoreInfo);// 將商家資訊存在setAttribute並藉由forward回傳給JSP
						storeNo = storeInfo.getMAP_STORE_NO();
						session.setAttribute("storeNoUsedInCommentArea", storeNo);// 將商家編號存在setAttribute並藉由forward回傳給JSP
						branch = 1;
						break;
					}

				}
				if (branch == 1) {
					RequestDispatcher success = req.getRequestDispatcher("/map_message/foodmap.jsp");
					success.forward(req, res);
				} else {

					String errorStoreName = "請輸入正確商家名稱";
					req.setAttribute("errorMessage", errorStoreName);
					RequestDispatcher failure = req.getRequestDispatcher("/map_message/foodmap.jsp");
					failure.forward(req, res);

				}

			}
		}

	}

}
