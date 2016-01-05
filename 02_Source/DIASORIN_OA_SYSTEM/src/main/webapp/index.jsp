<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<script>
function toLogin(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_UL/init";
	targetform.method = "POST";
	targetform.submit();
}

</script>
</head>
<body onload="toLogin()">
<form action="">
</form>
</body>
</html>
