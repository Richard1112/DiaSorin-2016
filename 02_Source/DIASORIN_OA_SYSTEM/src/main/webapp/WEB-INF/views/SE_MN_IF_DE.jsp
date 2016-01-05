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
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />

<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-multiselect.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script type="text/javascript" src='<c:url value="/js/jquery.uploadify.min.js"/>'></script>	
<link href="<c:url value='/css/uploadify.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/validate.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script>

$(document).ready(function() {
	$('.multiselect').multiselect();
	
	$("#uploadify").uploadify({
		'uploader'         : '${pageContext.request.contextPath}/servlet/Upload?folder=personalPhoto',//+folder,
		'swf'              : '${pageContext.request.contextPath}/js/uploadify.swf',
		'folder'           : 'upload',
		'fileSizeLimit'    : '2MB',
		'queueID'          : true,
		'auto'             : true,
		'multi'            : false,
		'simUploadLimit'   : 1,
		'removeCompleted'  : false,
		'buttonText'       : 'upload',
		'buttonImage'      : '${pageContext.request.contextPath}/images/upload.png',
		'width'            : 98,
		'height'           : 30,
		'fileTypeExts'     : '*.jpg;*.jpeg;*.png',
		'onSelect': function (file) { },
		'onUploadSuccess': function (file, data, response) {
			var newName = data;
			$("#photo1").attr("src", "${pageContext.request.contextPath}/personalPhoto/"+newName);
			$("#photo1").css({cursor:'pointer'});
			$("#headPic").val(newName);
			$('#updateChange').val("1");
		},
	});
	// 初期化多选框
	initMultiselect();
    
});

function updateEmployee(){
	if (!validateForm()) return;
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_MN_IF/updateEmployee";
	targetform.method = "POST";
	targetform.submit();
}

var I0011 = '<fmt:message key="I0011" />'; 
function backToMain(){
	var form = document.getElementById("seEmPeDeAdBean");
	if (isFormEdited(form) || $('#updateChange').val() == "1"){
		if (!confirm(I0011)) {
			return;
		}
	}
	var targetform = document.forms[0];
	targetform.action = "${pageContext.request.contextPath}/SE_LG_MN_IF/init";
	targetform.method = "POST";
	targetform.submit();
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
var E0004 = '<fmt:message key="E0004" />'; 	//must enter your English Name.
var E0005 = '<fmt:message key="E0005" />';  //Your E-mail Address entered is illegal.
var E0006 = '<fmt:message key="E0006" />';	//Your CellPhone Number entered is illegal.
var E0008 = '<fmt:message key="E0008" />';	//{0} must be entered.
function validateForm(){
	clearError();
	var notError = true;
	//必须输入
	
	var employeeNameEn  = $("#employeeNameEn").val();
	if (employeeNameEn == "") {
		$("#employeeNameEnError").append("<label>"+E0004+"</label>");
		notError = false;
	}
	var email  = $("#email").val();
	if (email.length > 0 && !mailCheck(email)) {
		$("#emailError").append("<label>"+E0005+"</label>");
		notError = false;
	}
	var mobilePhone  = $("#mobilePhone").val();
	if (mobilePhone.length > 0 && !checkMobile(mobilePhone)) {
		$("#mobilePhoneError").append("<label>"+E0006+"</label>");
		notError = false;
	}

	return notError;
}

function clearError(){
	$("#employeeNameEnError").html(errorImg);
	$("#emailError").html(errorImg);
	$("#mobilePhoneError").html(errorImg);
}

function clearPic(){
	$("#photo1").attr("src", "../images/photo_size.png");
	$("#photo1").css({cursor:'default'});
	$("#headPic").val("");
	$('#updateChange').val("1");
}

</script>
</head>

<body onload="initializeSelectOne();">
<form:form id="seEmPeDeAdBean" modelAttribute="seEmPeDeAdBean" commandName="seEmPeDeAdBean">
	<div class="position"><fmt:message key="se_mn_if_de_postion" /></div>
	<input type="hidden" id="updateChange"/>
	<form:hidden path="employeeNo"/>
	<form:hidden path="headPic"/>

	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_mn_if_de_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
            		<tr>
                    	<td class="td_font" width="150px"><fmt:message key="se_em_pe_ad_emNo" /></td>
                    	<td class="td_box" width="362px">
                    		<form:input path="employeeNo" cssClass="input_date_box" disabled="true" maxlength="10"/>
                    	</td>
                        <td class="td_font" width="150px"><fmt:message key="se_em_pe_ad_serCC" /></td>
                        <td class="td_box" width="363px" colspan="2">
                        	<form:select path="serviceCostCenter" cssClass="multiselect select_box" multiple="multiple" disabled="true">
                    			<c:forEach var="bean" items="${ costCenterSelect }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                   			</form:select>
                        </td> 
                    </tr>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ad_chiN" /></td>
                    	<td class="td_box">
                    		<form:input path="employeeNameCn" cssClass="input_date_box"/>
                    	</td>
                        <td class="td_font"><fmt:message key="se_em_pe_ad_engN" /></td>
                        <td class="td_box" colspan="2">
                        	<form:input path="employeeNameEn" cssClass="input_date_box"/>
                        	<span id="employeeNameEnError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span>
                        </td>
                    </tr>
                    
                    <tr>
						<td class="td_font"><fmt:message key="se_em_pe_ad_costc" /></td>
                        <td class="td_box">
                            <form:select path="deptCode" cssClass="select_box" disabled="true">
                    			<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterSelect }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                        <td class="td_font"></td>
                        <td class="td_box" rowspan="4" colspan="2">
                          	<c:if test='${ seEmPeDeAdBean.headPic == "" or seEmPeDeAdBean.headPic == null }'>
                        		<img alt="" src="../images/photo_size.png" id='photo1' width="140px" height="140px"/>
                        	</c:if>
                        	<c:if test='${ seEmPeDeAdBean.headPic != "" and seEmPeDeAdBean.headPic != null }'>
								<img src="${pageContext.request.contextPath}/personalPhoto/${seEmPeDeAdBean.headPic }" width="140px" height="140px" id='photo1' />
							</c:if>
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ad_email" /></td>
                        <td class="td_box">
                        	<form:input path="email" cssClass="input_date_box"/>
                        	<span id="emailError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span>
                        </td>
                        <td class="td_font"></td>
                        <td class="td_box" colspan="2">
                        	
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ad_phone" /></td>
                    	<td class="td_box">
                    		<form:input path="mobilePhone" cssClass="input_date_box"/>
                    		<span id="mobilePhoneError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span>
                    	</td>
                        <td class="td_font"></td>
                        <td class="td_box" colspan="2">
                    		
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ad_worlLoc" /></td>
                        <td class="td_box">
                    		<form:input path="liveLocation" cssClass="input_date_box"/>
                        </td>
                        <td class="td_font"></td>
                        <td class="td_box" colspan="2">
                    		
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_font"><fmt:message key="se_em_pe_ad_conAdd" /></td>
                    	<td class="td_box">
                    		<form:input path="contactAddress" cssClass="input_date_box"/>
                    	</td>
                        <td class="td_font"></td>
                        <td class="td_box" style="width:100px">
                    		<input type="file" name="uploadify" id="uploadify"/>
                    		
                        </td>
                        <td class="td_box">
                        	<div>
                        	<a href="#" onclick="clearPic()">
                        		<img src="<c:url value='/images/cancelPic.png'/>"/>
                        	</a>
                        	</div>
                        </td>
                    </tr>
                    <tr>
                   	  <td colspan="2">
                   	  	<div class="add_details_left_btn"><a href="#" onclick="backToMain()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
                   	  </td>
                   	  <td></td>
                        <td class="td_box" colspan="2">
                        	<div class="add_details_right_btn"><a href="#" onclick="updateEmployee()"><img src="<c:url value='/images/save_btn.png'/>"/></a></div>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
    </div>
    <form:hidden path="hiddenserviceCostCenter"/>
</form:form>
</body>
</html>
