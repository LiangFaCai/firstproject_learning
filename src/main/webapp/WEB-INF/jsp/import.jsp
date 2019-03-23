<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/user/exportExcel">导出</a>
<!-- excel文件导入 -->
<form action="${pageContext.request.contextPath}/user/importExcel" method="post" enctype="multipart/form-data">
    <input type="file" name="userExcel" />
    <input type="submit" value="导入">
</form>
   <a>${excelName}</a>
</body>
</html>