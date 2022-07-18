<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>자료실 상세보기</title>
<style type="text/css">
	main { width:fit-content; padding:1em; margin:0 auto; }
	main>h3 { width:fit-content; margin:0.5em auto; }
	table { width:fit-content; padding:1em; border:1px solid black; }
	th {width:8em; text-align:right; padding-right:1em; border-right:1px solid black;}
	td { padding-left:1em; text-align:left; }
	a { text-decoration:none; }
	nav {width:fit-content; padding-top:1em; margin:0 auto; }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function deleteIt(num)
	{
		if(!confirm('현재 게시물을 삭제하시겠어요?')) return;
		$.ajax({
			url: '/filesupload/delete3/'+num,
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res){
				alert(res.deleted ? '삭제 성공':'Failed');
				location.href='/filesupload/detail/${vo.num}';
			},
			error:function(xhr,status,err){
				alert('Error:'+err);
			}
		});
	}
</script>
<script type="text/javascript">
function upload(pnum) {
	//$('#btnUpload').on('click', function(event) {
	//event.preventDefault();
	
	var form = $('#uploadForm')[0]
	var data = new FormData(form);
	
	$('#btnUpload').prop('disabled', true);
	
	$.ajax({
	    type: "POST",
	    enctype: 'multipart/form-data',
	    url: "/filesupload/upload",
	    data: data,
	    dataType: 'json',     /* text, json, script, html, xml 등 */
	    processData: false,   /* 디폴트인 Query String 변환 하지 않도록 설정 */
	    contentType: false,   /* 디폴트 "application/x-www-form-urlencoded; charset=UTF-8" */
	    cache: false,
	    timeout: 600000,      /* 시간 제한 없음 */
	    success: function (res) {
	    	alert(res.inserted ? '업로드 성공':'Failed');
	    	location.href = '/filesupload/detail/'+pnum;
	    },
	    error: function (e) {
	        $('#btnUpload').prop('disabled', false);
	        alert('fail');
	    }
	});
};
</script>
</head>
<body>
<main>
<h3>게시물 상세보기</h3>
<table>
	<tr><th>게시물 번호</th><td>${vo.num}</td></tr>
	<tr><th>작성자</th><td>${vo.writer}</td></tr>
	<tr><th>작성일</th><td>${vo.udate}</td></tr>
	<tr><th>설명</th><td>${vo.comments}</td></tr>
	<tr><th>첨부파일</th>
		<td>
		<c:forEach var="f" items="${vo.attach}">
			<span><a href="/filesupload/download/${f.num}">${f.fname}</a></span>
			[<a href="javascript:deleteIt(${f.num});">삭제</a>]<br>
		</c:forEach>
		</td>
	</tr>
	<tr><th> </th>
		<td>
		<form id="uploadForm" action="/filesupload/upload" method="post" enctype="multipart/form-data">
			<input type="hidden" name="pnum" value="${vo.num}">
			<div style="border-top:3px double black; padding-top:0.5em;">
				<input type="file" name="files" multiple>
			</div>
			<div style="margin-top:0.5em;">
				<button id="btnUpload" type="button" onclick="upload(${vo.num});">업로드</button>
			</div>
		</form>
		</td>
	</tr>
</table>
<nav>
	<button type="button" onclick="deleteIt(${vo.num});">게시물 삭제</button>
	[<a href="/filesupload/list">목록보기</a>]
</nav>
</main>

</body>
</html>