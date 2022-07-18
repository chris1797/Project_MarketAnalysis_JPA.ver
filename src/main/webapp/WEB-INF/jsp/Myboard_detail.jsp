<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[게시글 상세보기]</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function remove(){
		$.ajax({
			url:'/mybatis/board/remove/${board.num}',
			method:'post',
			cache:false,
			dataType:'json',
			success:function(res){ //res는 object
					alert(res.removed ? '삭제성공' : 'Failed'); //res object 안에있는 num
					location.href="/mybatis/board";
			},
			error:function(xhr,status,err){
				alert(err);
			}
		});
		return false;
	}
</script>
<style type="text/css">
	main { width:fit-content; margin:10px auto; }
	main>h3 { width:fit-content; margin:10px auto; }
	table {border:1px solid black; width:600px; border-spacing:0; border-collapse: collapse;}
	th{ border-right:3px double black; background-color:#eee; width:15%;}
	tr:last-child td { width:85%; height:7em; overflow: auto;}
	th, td { border-bottom: 1px solid black; padding:5px;}
	td:nth-child(2) { width:15%;}
	footer { width:fit-content; margin:1em auto; }
	a { text-decoration: none; }
</style>
</head>
<body>
<main>
<h3>게시글 상세보기</h3>
<hr>
<table>
	<tr><th>번호</th><td>${board.num}</td><th>제목</th><td>${board.title}</td></tr>
	<tr><th>작성자</th><td>${board.author}</td><th>작성일</th><td>${board.wdate}</td></tr>
	<tr><th>내용</th><td colspan="3">${board.contents}</td></tr>
</table>
<br>
<footer>
	[<a href="/mybatis/board">글목록</a>] &nbsp;&nbsp;
	[<a href="/mybatis/board/edit/${board.num}">수정</a>] &nbsp;&nbsp;
	[<a href="javascript:remove();">삭제</a>]
	[<a href="board?cmd=inputform&pcode=${board.num}">답글쓰기</a>]
</footer>
</main>
</body>
</html>