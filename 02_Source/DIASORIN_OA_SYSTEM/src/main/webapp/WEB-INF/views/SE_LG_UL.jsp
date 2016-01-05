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
<link href="<c:url value='/css/user-common-login.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script>

var I0004 = '<fmt:message key="I0004" />';
var loginError = '<div id="loginIdError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()"></div>';
var passError =  '<div id="passwordError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()"></div>';

function submitLogin(){
	if (!validateForm()) return;
	if (document.getElementById("formcheckbox").checked){
		addCookie("userId", $("#login_user").val());
	}else{
		delCookie("userId");
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_UL/login";
	targetform.method = "POST";
	targetform.submit();
}

function forgotPassword(){
	alert(I0004);
}

// 添加COOKIE
function addCookie(objName,objValue){
    var infostr = objName + '=' + escape(objValue);
    var date = new Date();
    date.setTime(date.getTime()+30*24*3600*1000);
    infostr += ';expires =' + date.toGMTString();
    document.cookie = infostr; //添加
}

// 删除COOKIE
// 删除cookie
function delCookie(objName){
    var date = new Date(); 
    date.setTime(date.getTime() - 10000); 
    document.cookie = objName + "=a; expires=" + date.toGMTString(); 
}

function getCookie(name){ 
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split(";"); 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		if(arr[0]==name){
			return unescape(arr[1]); 
		}
	} 
	return ""; 
} 

var E0003 = '<fmt:message key="E0003" />';
function init(){
	if(top.location!=self.location){
		top.location = self.location;
	}
	var cookUserId = getCookie("userId");
	if (cookUserId != null && cookUserId.length > 0) {
		$("#login_user").val(cookUserId);
		$("#formcheckbox").attr("checked",true);
	}
	$("#loginIdError").remove();
	$("#passwordError").remove();
	
	var checkCanLogin = $("#checkCanLogin").val();
	if (checkCanLogin == "1") {
		// 说明登录出错。
		$("#login_user_box").append(loginError);
		$("#loginIdError").append(errorImg);
		$("#loginIdError").append("<label>"+E0003+"</label>");
	}
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
var E0008 = '<fmt:message key="E0008" />';	//{0} must be entered.
function validateForm(){
	clearError();
	var notError = true;
	//必须输入
	var loginId  = $("#login_user").val();
	if (loginId == "") {
		$("#login_user_box").append(loginError);
		var message = E0008.replace("{0}", '<fmt:message key="se_lg_ul_loginid" />')
		$("#loginIdError").append(errorImg);
		$("#loginIdError").append("<label>"+message+"</label>");
		notError = false;
	}
	var password  = $("#login_password").val();
	if (password == "") {
		$("#login_password_box").append(passError);
		var message = E0008.replace("{0}", '<fmt:message key="se_lg_ul_password" />')
		$("#passwordError").append(errorImg);
		$("#passwordError").append("<label>"+message+"</label>");
		notError = false;
	}
	return notError;
}

function clearError(){
	$("#loginIdError").remove();
	$("#passwordError").remove();
}
</script>
</head>

<body style="background:#003b79; width:100%; height:100%;" onload="init();">
<form:form id="seLgUlBean" modelAttribute="seLgUlBean" commandName="seLgUlBean">
	<input type="hidden" value="${cannotLogin}"  id="checkCanLogin"/>
     <div id="login">
        <div id="login_left"></div>
        <div id="login_right">
        	<div id="login_title"><span style="color:#ff9c00; font-family:Tahoma, Geneva, sans-serif; font-size:16px;"><fmt:message key="se_lg_ul_welcome" /></span><fmt:message key="se_lg_ul_log" /></div>
            <div id="login_user_box">
            	<form:input path="loginId" id="login_user"/>
				<div id="loginIdError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
					<img src="<c:url value='/images/error.png'/>"/>
				</div>
            </div>
            <div id="login_password_box">
            	<form:input type="password" path="password" id="login_password"/>
				<div id="passwordError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
					<img src="<c:url value='/images/error.png'/>"/>
				</div>
            </div>
             
            <div id="font_box">
            	<div id="remember_box"><input type="checkbox" name="checkbox" id="formcheckbox" />&nbsp;<label for="formcheckbox"><fmt:message key="se_lg_ul_remeber" /></label></div>
                <div id="forgot_box"><a href="#" onclick="forgotPassword()"><fmt:message key="se_lg_ul_forgetpassword" /></a></div>
                <div class="clear"></div>
            </div>
            
            <div id="sign_btn">
            <a href="#" onclick="submitLogin()"><img src="<c:url value='/images/sign_btn.png'/>"/></a>
            </div>
            
        </div>
        <div class="clear"></div>
    </div>
</form:form> 
</body>
</html>
