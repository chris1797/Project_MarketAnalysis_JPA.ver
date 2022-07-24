<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

#login {
	font-size: 4px;
}

#add {
	font-size: 4px;
}

#menu {
	color: black;
	text-decoration: none;
	background-color: #f44366;
	padding: 15px; /* 패딩: 안쪽여백, 마진: 밖여백 */
	/* 글자가 깨졌으니 밑의 display 추가하자 */
	display: inline-block; /* 인라인모드/블럭모드 이해 필요  */
	width: 200px;
	text-align: center;
	margin: 5px;
}

a:hover { /* a태그에 마우스가 올라갈 때 라는 의미*/
	color: white;
	text-decoration: underline;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
</head>
<body>

	<header>
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
							<li><a href="/board/notice" class="text-white">지역별
									현황(지도)</a></li>
							<li><a href="/board/notice" class="text-white">상권현황
									상세데이터</a></li>
							<li><a href="/board/notice" class="text-white">정부 지원사업
									정보</a></li>
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
	</header>
	<main>
		<section class="text-left container">
			<div class="row py-lg-5">
				<div class="col-lg-8 col-md-8">
					<h1 class="fw-light">데이터 출처</h1>
				</div>
			</div>
		</section>

		<section class="py-2 text-center container">
			<div class="row py-lg-5">
				<div class="col-lg-8 col-md-2">
					<h3>데이터 출처 설명</h3>
				</div>
			</div>
		</section>
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