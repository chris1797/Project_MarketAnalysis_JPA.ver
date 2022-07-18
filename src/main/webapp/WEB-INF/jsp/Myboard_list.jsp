<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
</script>
<style type="text/css">
	main { width:fit-content; margin:0 auto; }
	main>h3 { width:fit-content; margin:10px auto; }
	table { border:1px solid black; width:500px; border-spacing:0; border-collapse: collapse;}
	th,td { border-right: 1px dashed black; text-align:center;}
	td {border-right: 1px dashed black; text-align:left;}
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
<h3>[ 글목록 ]</h3>
<table>
	<tr>
		<th>No.</th><th>제목</th><th>작성자</th><th>작성일</th>
	</tr>
<c:forEach var="board" items="${list}">
	<tr>
		<td><a href="/mybatis/board/detail/${board.num}">${board.num}</a></td>
		<td>${board.title}</td>
		<td>${board.author}</td>
		<td>${board.wdate}</td>
	</tr>
</c:forEach>
</table>
<form action="MybatisBoard">
<input type="hidden" name="cmd" value="search">
<br>
	<div>
		<label>작성자로 검색</label>
		<input type="radio" id="search" name="search" value="author">
		<label>제목으로 검색 </label>
		<input type="radio" id="search" name="search" value="title">
	</div>
	<div>
		<label>검색어</label>
		 <input type="text" id="searchVal" name="searchVal" list="list">
		<button type="submit">검색</button>
		<button type="reset">취소</button>
	</div>
</form>
<footer>
[<a href="/mybatis/board/write_form">글쓰기</a>]
</footer>
</main>
</body>
</html>