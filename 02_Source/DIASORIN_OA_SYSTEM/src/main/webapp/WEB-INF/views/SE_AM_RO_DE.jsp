<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>

<script>
var I0018 = '<fmt:message key="I0018" />';
function save(curPage,saveDivision){
	if(validateForm()){
		if (!confirm(I0018)){
			return;
		}
		if(saveDivision == "add"){
			var targetform = document.forms[0];
			targetform.action = "${pageContext.request.contextPath}/SE_AM_RO_DE/save";
			targetform.method = "POST";
			targetform.submit();
		} else {
			var targetform = document.forms[0];
			targetform.action = "${pageContext.request.contextPath}/SE_AM_RO_DE/update?curPage=" + curPage;
			targetform.method = "POST";
			targetform.submit();
		}
	}
}

var I0011 = '<fmt:message key="I0011" />';
function back(){
	var form = document.getElementById("seAmRoDeBean");
	if (isFormEdited(form)){
		if (!confirm(I0011)) {
			return;
		}
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/init";
	targetform.method = "POST";
	targetform.submit();
}

var E0008 = '<fmt:message key="E0008" />';
var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
function validateForm(){
	clearError();
	var notError = true;
	var roleName  = $("#roleName").val();
	if (roleName == "" ) {
		var message = E0008.replace("{0}", '<fmt:message key="se_am_ro_de_msg1" />')
		$("#roleNameError").append("<label>"+message+"</label>");
		notError = false;		
	}
	return notError;
}

function clearError(){
	
	$("#roleNameError").html(errorImg);
}
var I0009 = '<fmt:message key="I0009" />';
var I0016 = '<fmt:message key="I0016" />';
function init(){
	if ($("#hiddenHasSaved").val() == "1") {
		if ($("#hdSaveDivision").val() == "add") {
			alert(I0016);
		} else {
			alert(I0009);
		}
		var targetForm = document.forms[0];
		targetForm.action = "${pageContext.request.contextPath}/SE_AM_RO_LS/init";
		targetForm.method = "POST";
		targetForm.submit();
	}
}
</script>
</head>

<body onload="init();">
<form:form id="seAmRoDeBean" modelAttribute="seAmRoDeBean" commandName="seAmRoDeBean">
	<input type="hidden" id="hdSaveDivision" value="${ saveDivision }"/>
	<input type="hidden" value="${hasSaved}" id="hiddenHasSaved"/>
	<c:if test='${saveDivision == "add"}'>
	  	<div class="position"><fmt:message key="se_am_ro_de_position1" /></div> 
	  </c:if>
	  <c:if test='${saveDivision == "update"}'>
	  	<div class="position"><fmt:message key="se_am_ro_de_position2" /></div> 
	  </c:if>

	<div class="list_main_box">
	  <c:if test='${saveDivision == "add"}'>
	  	<div class="list_title"><fmt:message key="se_am_ro_de_title1" /></div> 
	  </c:if>
	  <c:if test='${saveDivision == "update"}'>
	  	<div class="list_title"><fmt:message key="se_am_ro_de_title2" /></div> 
	  </c:if>
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>

                    <tr>
                    	<td class="td_font"><fmt:message key="se_am_ro_de_roleName" /></td>
                    	<td class="td_box">
                    		<form:input path="roleName" cssClass="input_box" />
                    		<span id="roleNameError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                    	
                    	</td>
                    </tr>
                    
                    
                    <tr>
                   	  <td>
                   	  	<div class="add_details_left_btn"><a href="#" onclick="back()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
                      </td>
                      <td class="td_box">
                      	<div class="btn_left_padding"><a href="#" onclick="save('${ curPage }','${ saveDivision }')"><img src="<c:url value='/images/save_btn.png'/>"/></a></div>
                      </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
    </div>
    <form:hidden path="roleCode"/>
</form:form>
</body>
</html>
