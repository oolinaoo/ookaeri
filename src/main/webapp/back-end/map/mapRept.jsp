<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="web.mapMessage.service.*"%>
<%@ page import="web.mapMessage.entity.*"%>
<%@ page import="web.v_map_message_map_report.dao.*"%>
<%@ page import="web.v_map_message_map_report.dao.impl.*"%>
<%@ page import="web.v_map_message_map_report.entity.*"%>
<%@ page import="java.util.*"%>


<%
	mapMessageMapReportDAO dao =new mapMessageMapReportDAOImpl();
	List<mapMessageMapReportVO> reportMes =dao.getAll();
	request.setAttribute("reportMes", reportMes);
%>

<!-- Back-end mapRept -->

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>地圖留言管理</title>
    <link rel="stylesheet" href="css/adminStyle.css" />
    <script
      defer
      src="https://use.fontawesome.com/releases/v5.0.10/js/all.js"
      integrity="sha384-slN8GvtUJGnv6ca26v8EzVaR9DC58QEwsIk9q1QXdCU8Yu8ck/tL/5szYlBbqmS+"
      crossorigin="anonymous"
    ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

  </head>
  <body>
    <div class="sidenav">
      <div class="sidenav-cont">
        <div class="menu">
          <span><img class="logo" src="images/logo2.png" />管理員專區<br><span></span></span>
        </div>
        <div>
          <hr />
          <button class="dropdown-btn">
            帳號管理 <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-container">
            <a href="/okaeri/back-end/acct-addr/member.html">住戶帳號管理</a> <a href="/okaeri/back-end/acct-addr/admin.html">後端帳號管理</a>
            <a href="/okaeri/back-end/acct-addr/address.html">地址資料管理</a>
          </div>
          <button class="dropdown-btn">
            社區交流 <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-container">
            <a href="/okaeri/back-end/news/adminNews.html">社區公告</a> <a href="/okaeri/back-end//rule/adminRule.html">社區公約</a>
          </div>
          <button class="dropdown-btn">
            社區論壇 <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-container">
            <a href="/okaeri/back-end/forum/adminForumArticle.html">論壇</a> <a href="/okaeri/back-end/forum/adminForumMessage.html">論壇留言</a>
            <a href="/okaeri/back-end/forum/adminForumReport.html">論壇檢舉</a>
          </div>
          <button class="dropdown-btn">
            公設管理 <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-container">
            <a href="/okaeri/back-end/facilities/facilities.html">公設列表</a> <a href="/okaeri/back-end/facilities/facilities_history.html">公設租借紀錄</a>
            <a href="/okaeri/back-end/facilities/facilities_edit.html">公設編輯</a>
          </div>
          <button class="dropdown-btn">
            美食地圖 <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-container">
            <a href="#">商家資訊</a>
            <a href="/okaeri/back-end/map/mapRept.jsp">留言檢舉</a>
          </div>
          <a href="/okaeri/back-end/payment/payment.html">管理費用</a>
          <a href="/okaeri/back-end/pack/pack.html">管理包裹</a>
          <a id="logout" href="">登出</a>
        </div>
      </div>
    </div>

    <!--  ###########  頁面主要內容區域  ###########  -->
    <div class="main">
      <h3>地圖留言管理</h3>
      
      <!-- SEARCH BAR & ADD BUTTON-->
      <div class="search-bar">
        <label>search</label>
        <input
          type="text"
          class="searchTerm light-table-filter"
          placeholder="What are you looking for?"
          data-table="order-table"
        />
      </div>

      <!-- 主頁內容 -->
      <div class="container">
        <table id="history_table" class="tableContents order-table">
          <thead>
            <tr>
             		<th>留言編號</th>
					<th>留言者帳號</th>
					<th>留言內容</th>
					<th>留言時間</th>
					<th>餐廳編號</th>
					<th>上下架</th>
					<th>檢舉狀態</th>
					<th>檢舉內容</th>
					<th>留言上架</th>
					<th>留言下架</th>
            </tr>
          </thead>
          <tbody>
				<c:forEach var="msg" items="${requestScope.reportMes}">
					<tr>
						<td>${msg.mapMassageNo}</td>
						<td>${msg.memAcct}</td>
						<td>${msg.mapMessageContent}</td>
						<td>${msg.mapMessageTime}</td>
						<td>${msg.mapStoreNo}</td>
						<td>${msg.mapMessageState==0}</td>
						<td>${msg.mapReptState}</td>
						<td>${msg.mapReptContent}</td>
						<td><FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/controller/ServletController.do"
						style="margin-bottom: 0px; width:30px">
						<input type="submit" value="上架" style="width:45px"> 
						<input type="hidden" name="mapMassageNo" value="${msg.mapMassageNo}">
						<input type="hidden" name="memAcct" value="${msg.memAcct}">
						<input type="hidden" name="mapMessageContent" value="${msg.mapMessageContent}">
						<input type="hidden" name="mapMessageTime" value="${msg.mapMessageTime}">
						<input type="hidden" name="mapStoreNo" value="${msg.mapStoreNo}">
						<input type="hidden" name="mapMessageState" value="${msg.mapMessageState}">
						<input type="hidden" name="mapReptState" value="${msg.mapReptState}">
						<input type="hidden" name="mapReptContent" value="${msg.mapReptContent}">
						<input type="hidden" name="action" value="KeepComment">
						</FORM>
						</td>
						<td><FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/controller/ServletController.do"
						style="margin-bottom: 0px; width:30px">
						<input type="submit" value="下架" style="width:45px">
						<input type="hidden" name="mapMassageNo" value="${msg.mapMassageNo}">
						<input type="hidden" name="memAcct" value="${msg.memAcct}">
						<input type="hidden" name="mapMessageContent" value="${msg.mapMessageContent}">
						<input type="hidden" name="mapMessageTime" value="${msg.mapMessageTime}">
						<input type="hidden" name="mapStoreNo" value="${msg.mapStoreNo}">
						<input type="hidden" name="mapMessageState" value="${msg.mapMessageState}">
						<input type="hidden" name="mapReptState" value="${msg.mapReptState}">
						<input type="hidden" name="mapReptContent" value="${msg.mapReptContent}"> 
						<input type="hidden" name="action" value="downComment">
						</FORM>
						</td>
					</tr>
				</c:forEach>


			</tbody>
        </table>
      </div>
    </div>
    <script src="./vendor/jquery-3.6.0.min.js"></script>
    <script src="./js/adminScript.js" charset="UTF-8"></script>
    
  </body>
</html>