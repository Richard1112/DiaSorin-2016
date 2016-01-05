<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script>

function roleChanged(str){
	var listCount = $("#listCount").val();
	var newVal = $(str).val();
	var newTxt = $("#roleId option[value="+newVal+"]").text();
	for(var i=0;i<listCount;i++){
		if(newVal != ''){
			$("#roleId"+i).val(newVal);
			$("#roleName"+i).html(newTxt);
		}else{
			$("#roleId"+i).val('');
			$("#roleName"+i).html('');
		}
	} 
}

function deptChanged(str){
	var listCount = $("#listCount").val();
	var newVal = $(str).val();
	var newTxt = $("#deptId option[value="+newVal+"]").text();
	for(var i=0;i<listCount;i++){
		if(newVal != ''){
			$("#deptId"+i).val(newVal);
			$("#deptName"+i).html(newTxt);
		}else{
			$("#deptId"+i).val('');
			$("#deptName"+i).html('');
		}
	} 
}

var I0018 = '<fmt:message key="I0018" />';
function authoritySubmit(){
	var saveDivision = $("#hiddenSaveDivision").val();
	var curPage = $("#hiddenCurPage").val();
	if(saveDivision == "add"){
		if(validateForm()){
			if (!confirm(I0018)){
				return;
			}
			var targetform = document.forms[0];
			targetform.action = "${pageContext.request.contextPath}/SE_SY_AM_AU/save?curPage=" + curPage;
			targetform.method = "POST";
			targetform.submit();
		}
	} else {
		if (!confirm(I0018)){
			return;
		}
		var targetform = document.forms[0];
		targetform.action = "${pageContext.request.contextPath}/SE_SY_AM_AU/save?curPage=" + curPage;
		targetform.method = "POST";
		targetform.submit();
	}
}

var I0011 = '<fmt:message key="I0011" />';
function backToList(){
	var form = document.getElementById("seSyAmAUBean");
	if (isFormEdited(form)){
		if (!confirm(I0011)) {
			return;
		}
	}
	var curPage = $("#hiddenCurPage").val();
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_SY_AM_AL/page?curPage=" + curPage;
	targetform.method = "POST";
	targetform.submit();
}

var E0008 = '<fmt:message key="E0008" />';
var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
function validateForm(){
	clearError();
	var notError = true;
	var roleId  = $("#roleId").val();
	if (roleId == "" ) {
		var message = E0008.replace("{0}", '<fmt:message key="se_sy_am_au_msg2" />')
		$("#roleIdError").append("<label>"+message+"</label>");
		notError = false;		
	}
	var deptId  = $("#deptId").val();
	if (deptId == "" ) {
		var message = E0008.replace("{0}", '<fmt:message key="se_sy_am_au_msg1" />')
		$("#deptIdError").append("<label>"+message+"</label>");
		notError = false;		
	}
	return notError;
}

function clearError(){
	$("#roleIdError").html(errorImg);
	$("#deptIdError").html(errorImg);
}

function allClick(str){
	var isChecked = str.checked;
	$("table input[type=checkbox]").each(function(){
        this.checked = isChecked;
    }); 
}

function sigleClick(){
	var isAllcheck = true;
	$("table input[type=checkbox]").each(function(){
		if (this.id != "allCheckFlg") {
			if (!this.checked){
	        	isAllcheck = false;
	        }
		}
        
    });
	document.getElementById("allCheckFlg").checked = isAllcheck;
}
var I0009 = '<fmt:message key="I0009" />';
function init(){
	if ($("#hiddenHasSaved").val() == "1") {
		var curPage = $("#hiddenCurPage").val();
		alert(I0009);
		var targetForm = document.forms[0];
		targetForm.action = "${pageContext.request.contextPath}/SE_SY_AM_AL/page?curPage="+curPage;
		targetForm.method = "POST";
		targetForm.submit();
	}
}
</script>
</head>

<body onload="init();initializeSelectOne();">
<form:form id="seSyAmAUBean" modelAttribute="seSyAmAUBean" commandName="seSyAmAUBean">
	<input type="hidden" value="${saveDivision}" id="hiddenSaveDivision"/>
	<input type="hidden" value="${curPage}" id="hiddenCurPage"/>
	<input type="hidden" value="${hasSaved}" id="hiddenHasSaved"/>
    <c:if test='${saveDivision == "add"}'>
	  	<div class="position"><fmt:message key="se_sy_am_au_position1" /></div>
	</c:if>
	<c:if test='${saveDivision == "update"}'>
	  	<div class="position"><fmt:message key="se_sy_am_au_position2" /></div>
	</c:if>
    
	<div class="list_main_box">
	  <c:if test='${saveDivision == "add"}'>
   	  <div class="list_title"><fmt:message key="se_sy_am_au_title1" /></div>
   	  </c:if>
   	  <c:if test='${saveDivision == "update"}'>
   	  <div class="list_title"><fmt:message key="se_sy_am_au_title2" /></div>
   	  </c:if>  
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                    <tr>
                        <td class="td_font"><fmt:message key="se_sy_am_au_costcenter" /></td>
                    	<td class="td_box">
                    		<c:if test='${saveDivision == "add"}'>
                            <form:select path="deptId" class="select_box" onchange="deptChanged(this)">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterSelect }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<span id="deptIdError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                    		</c:if>
                    		<c:if test='${saveDivision == "update"}'>
                            <form:select path="deptId" class="select_box" onchange="deptChanged(this)" disabled="true">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterSelect }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<form:hidden path="deptId"/>
                    		</c:if>
                        </td>
                        <td class="td_font"><fmt:message key="se_sy_am_au_role" /></td>
                    	<td class="td_box">
                    		<c:if test='${saveDivision == "add"}'>
                            <form:select path="roleId" class="select_box" onchange="roleChanged(this)">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ roleSelect }">
                    				<form:option value="${ bean.roleCode }">${ bean.roleName }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<span id="roleIdError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                    		</c:if>
                    		<c:if test='${saveDivision == "update"}'>
                            <form:select path="roleId" class="select_box" onchange="roleChanged(this)" disabled="true">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ roleSelect }">
                    				<form:option value="${ bean.roleCode }">${ bean.roleName }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<form:hidden path="roleId"/>
                    		</c:if>
                        </td>
                    </tr>                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0" style="width:1120px">
				<tbody>
                	<tr class="td_title">
                    	<td width="305px"><fmt:message key="se_sy_am_au_decostcen" /></td>
                        <td width="285px"><fmt:message key="se_sy_am_au_derole" /></td>
                        <td width="345px"><fmt:message key="se_sy_am_au_decontrolid" /></td>
                        <td width="185px"><fmt:message key="se_sy_am_au_authority" /><input type="checkbox" onclick="allClick(this)" id="allCheckFlg"/></td>
                    </tr>
                    
                    <c:forEach var="seSyAmAuListBean" items="${ seSyAmAUBean.beanList }" varStatus="index">
						<tr>
							<td>
								<input type="hidden" id="deptId${index.count-1}" name="beanList[${index.count-1}].deptId" value="${ seSyAmAuListBean.deptId }" />
								<span id="deptName${index.count-1}">${ seSyAmAuListBean.deptName}</span>
							</td>
							<td>
								<input type="hidden" id="roleId${index.count-1}" name="beanList[${index.count-1}].roleId" value="${ seSyAmAuListBean.roleId }" />
								<span id="roleName${index.count-1}">${ seSyAmAuListBean.roleName }</span>
							</td>
							<td style="text-align:left">
								<input type="hidden" name="beanList[${index.count-1}].controlId" value="${ seSyAmAuListBean.controlId }" />
								${ seSyAmAuListBean.controlName }
							</td>
							<td>
								<c:if test='${ saveDivision == "add" }'>
									<input type="checkbox" name="beanList[${index.count-1}].authority" onclick="sigleClick()"/>
								</c:if>
								<c:if test='${ seSyAmAuListBean.authority == "1" }'>
									<input type="checkbox" name="beanList[${index.count-1}].authority" checked="checked" onclick="sigleClick()"/>
								</c:if>
								<c:if test='${ seSyAmAuListBean.authority == "0" }'>
									<input type="checkbox" name="beanList[${index.count-1}].authority" onclick="sigleClick()"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
                </tbody>
			</table>
      </div>
      <div class="detail_btn">
      	 <table>
      	 	<tr>
      	 		<td>
      	 			<div class="details_left_btn"><a href="#" onclick="backToList()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
      	 		</td>
      	 		<td>
      	 			<div class="details_right_btn"><a href="#" onclick="authoritySubmit()"><img src="<c:url value='/images/save_btn.png'/>"/></a></div>
      	 		</td>
      	 	</tr>
      	 </table>
      </div>
    </div>
    <input type="hidden" id="listCount" value="${ listCount }" >		
</form:form>
</body>
</html>
