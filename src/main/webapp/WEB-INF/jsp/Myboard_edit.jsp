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
	function edit() {
		$.ajax({
			url:'/mybatis/board/update/${board.num}',
			method:'post',
			cache:false,
			data:$('#edit_form').serialize(),
			dataType:'json',
			success:function(res){ //res는 object
					alert(res.updated ? '수정 성공' : 'Failed'); //res object 안에있는 num
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
<form id="edit_form" onsubmit="return edit();">

<tr><th>번호</th><td>${board.num}</td>
	<th>제목</th><td><input type="text" name="title" value="${board.title}"></td></tr>
	<tr><th>작성자</th><td>${board.author}</td><th>작성일</th><td>${board.wdate}</td></tr>
	<tr><th>내용</th><td colspan="3"><input type="text" name="contents" value="${board.contents}"></td></tr>
</table>
	<input type="submit" value="저장">
</form>
<br>
<footer>
	[<a href="/mybatis/board">글목록</a>] &nbsp;&nbsp;
</footer>
</main>
</body>
</html>