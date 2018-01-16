<%--
  Created by IntelliJ IDEA.
  User: leimiaomiao
  Date: 2018/1/15
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试文件上传</title>
</head>
<body>
<form action="<%=basePath%>video/testUpload" method="post" enctype="multipart/form-data">
    最简单的文件上传：<input type="file" name="fileupload"/>
    描述：<input type="text" name="desc"/>
    <input type="submit" value="submit"/>
</form>
</body>
</html>