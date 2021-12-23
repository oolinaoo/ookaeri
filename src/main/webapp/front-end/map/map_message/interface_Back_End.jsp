<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="web.mapMessage.service.*"%>
<%@ page import="web.mapMessage.entity.*"%>
<%@ page import="java.util.*"%>


<%
	Map_MessageService dao =new Map_MessageService();
	List<Map_MessageVO> msgList =dao.getAll();
	request.setAttribute("msgList", msgList);
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>地圖留言管理</title>
<style>
table {
	width: 1000px;
	border: 1px solid #888888;
	border-collapse: collapse;
}

td {
	border: 1px solid #888888;
	padding: 20px;
}

th {
	border: 1px solid #888888;
	padding: 20px;
}

tr:nth-child(even) {
	background-color: #7788aa;
	color: white;
}

thead {
	background-color: #7788aa;
	color: white;
}
</style>


</head>
<body>
	<center>
		<table style="min-width: 845px">
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
					<th>留言上下架</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${requestScope.msgList}">
					<tr>
						<td>${msg.MAP_MSG_NO}</td>
						<td>${msg.MEM_ACCT}</td>
						<td>${msg.MAP_MSG_CONTENT}</td>
						<td>${msg.MAP_MSG_TIME}</td>
						<td>${msg.MAP_STORE_NO}</td>
						<td>${msg.MAP_MSG_STATE==0}</td>
						<td>已處理</td>
						<td>${msg.MAP_MSG_CONTENT}</td>
						<td><FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/controller/ServletController.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="上架/下架"> 
						 <input type="hidden"
							name="empno" value="${msg.MAP_MSG_STATE}"> <input type="hidden"
							name="action" value="getOne_For_UpdateState">
					</FORM></td>
					</tr>
				</c:forEach>


			</tbody>

		</table>
		
	
		
		
	</center>


</body>

</html>