<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Upload Form</title>
</head>
<body>
<main>
<h3>Spring boot 파일 업로드 테스트</h3>
<form action="/filesupload/upload" method="post" enctype="multipart/form-data">
   작성자 : <input type="text" name="writer" value="Chris" readonly><br>
   설명 : <input type="text" name="comments"><br>
   File <input type="file" name="files" multiple="multiple"><br>
   <button type="submit">업로드</button>
</form>
</main>
</body>
</html>