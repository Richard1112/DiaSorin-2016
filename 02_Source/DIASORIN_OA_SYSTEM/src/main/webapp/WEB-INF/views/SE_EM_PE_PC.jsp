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

function updatePassword(){
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_EM_PE_PC/updatePassword";
	targetform.method = "POST";
	targetform.submit();
}



</script>
</head>
<body>
<form:form id="seEmPePcBean" modelAttribute="seEmPePcBean" commandName="seEmPePcBean">
<table>
	<tr><td>
	<form:input path="nowPassword"/> 旧密码
	</td></tr>
	<tr><td>
<form:input path="newPassword"/> 新密码
	</td></tr>
	
	<tr><td>
<form:input path="confirmPassword"/> 重复新密码
	</td></tr>
	

</table>

	<input type="button" onclick="updatePassword()" value="更新密码"/>

</form:form>
</body>
</html>
