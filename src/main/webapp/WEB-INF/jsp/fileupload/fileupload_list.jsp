<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 목록</title>
<style type="text/css">
	main { width:fit-content; margin:0 auto; }
	main>h3 { width:fit-content; margin:10px auto; }
	table { border:1px solid black; width:800px; border-spacing:0; border-collapse: collapse;}
	th,td { width:fit-content; border-right: 1px dashed black; text-align:center; height:30px;}
	td {width:fit-content; border-right: 1px dashed black; text-align:left;}
	th:last-child(), td:last-child() { border-right:none; }
	th {background-color: #7bcabe; border-bottom:3px double black; }
	tr:nth-child(odd) {background-color:#eee;}
	tr:hover { background-color: highlight; }
	footer { width:fit-content; margin:5px auto; }
	a { text-decoration: none;  }
</style>
</head>
<body>
<main>
<h3>파일목록</h3>
<table>
<tr><th>No.</th><th>글쓴이</th><th>작성일</th><th>설명</th><th>첨부</th></tr>
<c:forEach var="f" items="${list}">
<tr>
<td>
	<a href="detail/${f.num}">${f.num}</a>
</td>
<td>${f.writer}</td>
<td>${f.udate}</td>
<td>${f.comments}</td>
<td>
	<c:forEach var="aVO" items="${f.attach}">
		<a href="download/${aVO.num}">${aVO.fname}</a>
	</c:forEach>
</td>
</tr>
</c:forEach>
</table>
</main>
</body>
</html>