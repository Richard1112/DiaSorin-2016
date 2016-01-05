<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title></title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0，maximum-scale=1.0, user-scalable=no">

<script type="text/javascript">
	
</script>

<decorator:head></decorator:head>
</head>
<body id="object"
	<decorator:getProperty property="body.onload" writeEntireProperty="true" />>

	
</body>
</html>
