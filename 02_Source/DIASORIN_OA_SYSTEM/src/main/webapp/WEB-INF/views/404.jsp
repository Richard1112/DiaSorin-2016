<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<c:url value='/css/login.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script>

if(top.location!=self.location){
	top.location = self.location;
}

function relogin(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_UL/init";
	targetform.method = "POST";
	targetform.submit();
}
</script>
</head>

<body style="background:#003b79; width:100%; height:100%;">
<form:form>
    <div id="errorFrame">
    	<img src="<c:url value='/images/404.png'/>"/>
        <div id="errorImg ">
			<a href="#" onclick="relogin()">Login Again</a>
        </div>
        <div class="clear"></div>
    </div>
</form:form> 
</body>
</html>
