<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border=1>
		<c:forEach var="rule" items="${ruleList}">
			<tr>
				<td>${rule.ruleNo}</td>
				<td>${rule.ruleTitle}</td>
				<td>${rule.ruleContent}</td>
				<td>${rule.rulePosttime}</td>
				<td>${rule.adminAcct}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>