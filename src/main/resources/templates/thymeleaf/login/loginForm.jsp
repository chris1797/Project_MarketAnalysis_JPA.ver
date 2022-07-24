<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 폼</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<style>
#login {
	font-size: 4px;
}

#add {
	font-size: 4px;
}

main {
	width: fit-content;
	padding: 15px;
	margin: 0 auto;
}

#loginForm {
	width: fit-content;
	padding: 15px;
	border: 1px solid black;
	margin: 0 auto;
	border-radius: 5px;
	margin-top: 5px;
}

main>h3 {
	width: fit-content;
	margin: 0 auto;
}

label {
	display: inline-block;
	width: 3em;
	text-align: right;
	margin-right: 1em;
}

form div:last-child {
	width: fit-content;
	margin: 0 auto;
	margin-top: 1em;
}

input {
	width: 10em;
}

a {
	text-decoration: none;
	color: blue;
}

.link_user {
	display: inline-block;
	width: fit-content;
	font-size: 0.8em;
	magrin: 10 auto;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>

<script type="text/javascript">
	function login() {
		var serData = $('#loginForm').serialize();
		$.ajax({
			url : '/login/loginForm',
			method : 'post',
			cache : false,
			data : serData,
			dataType : 'json',
			success : function(res) {
				alert(res.login ? '로그인 성공' : '로그인 실패');
			},
			error : function(xhr, status, err) {
				alert(err);
			}
		});
		return false;
	}

	function logout() {
		if (!confirm('정말로 로그아웃하시겠어요?'))
			return;
		$.ajax({
			url : '/login/logout',
			method : 'get',
			cache : false,
			dataType : 'json',
			success : function(res) {
				alert(res.logout ? '로그아웃 성공' : '로그아웃 실패');
			},
			error : function(xhr, status, err) {
				alert(err);
			}
		});
	}
</script>
</head>
<body>
	<div class="navbar navbar-dark bg-dark shadow-sm">
		<div class="container">
			<a href="/home" class="navbar-brand d-flex align-items-center"> 
				<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
					fill="none" stroke="currentColor" stroke-linecap="round"
					stroke-linejoin="round" stroke-width="2" aria-hidden="true"
					class="me-2" viewBox="0 0 24 24">
					<path
						d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
					<circle cx="12" cy="13" r="4" /></svg> <strong>상권현황제공 창업커뮤니티</strong>
			</a>
		</div>
		<div>
			<a id="login" href="/login/loginForm"> <strong>로그인</strong>
			</a>/ <a id="add" href="/user/add"> <strong>회원가입</strong>
			</a>
		</div>
		<div>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarHeader"
				aria-controls="navbarHeader" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>
	</div>
	<div class="collapse bg-dark" id="navbarHeader">
		<div class="container">
			<div class="row">
				<div class="col-sm-8 col-md-5 py-4">
					<h4 class="text-white">커뮤니티</h4>
					<!--                     <p class="text-muted">공지사항</p> -->
					<ul class="list-unstyled">
						<li><a href="/board/main" class="text-white">커뮤니티 메인화면</a></li>
						<li><a href="/board/notice" class="text-white">공지사항</a></li>
						<li><a href="/board/list" class="text-white">자유게시판</a></li>
						<li><a href="/board/ads" class="text-white">광고게시판</a></li>
					</ul>
				</div>
				<div class="col-sm-4 col-md-5 py-4">
					<h4 class="text-white">정보</h4>
					<ul class="list-unstyled">
						<li><a href="/board/notice" class="text-white">지역별 현황(지도)</a></li>
						<li><a href="/board/notice" class="text-white">상권현황 상세데이터</a></li>
						<li><a href="/board/notice" class="text-white">정부 지원사업 정보</a></li>
					</ul>
				</div>
				<div class="col-sm-4 col-md-2 py-4">
					<h4 class="text-white">이용안내</h4>
					<ul class="list-unstyled">
						<li><a href="/home/dataSource" class="text-white">데이터 출처</a></li>
						<li><a href="/home/siteIntroduction" class="text-white">사이트
								소개</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<main>
		<h3>로그인</h3>
		<div class="link_user">
			<a href="/user/add">회원등록</a> | <a href="">아이디/암호 찾기</a> | <a
				href="javascript:logout();">로그아웃</a>
		</div>
		<form id="loginForm" action="user" method="post"
			onsubmit="return login();">
			<div>
				<label for="uid">아이디</label><input type="text" id="uid" name="uid"
					placeholder="아이디">
			</div>
			<div>
				<label for="upw">암 호</label><input type="password" id="upw"
					name="upw" placeholder="비밀번호">
			</div>
			<div>
				<button type="reset">취소</button>
				<button type="submit">로그인</button>
			</div>
		</form>
		<div class="link_user">
			[<a href="/user/update">회원정보 변경</a>]
		</div>
	</main>
	<footer class="text-muted py-5">
		<div class="container">
			<p class="float-end mb-1">
				<a href="#">Back to top</a>
			</p>
			<p class="mb-1">Album example is &copy; Bootstrap, but please
				download and customize it for yourself!</p>
			<p class="mb-0">
				New to Bootstrap? <a href="/">Visit the homepage</a> or read our <a
					href="../getting-started/introduction/">getting started guide</a>.
			</p>
		</div>
	</footer>
</body>
</html>




