<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<fmt:setBundle basename="message" var="messageBundle" />
<link href="<c:url value='/css/list.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/jquery.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/validate.js' />" type="text/javascript"></script>

<script>
var I0018 = '<fmt:message key="I0018" />';
var E0012 = '<fmt:message key="E0012" />';
function save(saveDivision){
	var tabInfo = $("#hdTabInfo").val();
	if(validateForm()){
		if(saveDivision == "add"){
			var code= "";
			var isCodeExit = false;
			if (tabInfo == "4") {
				code = $("#costCenterCode").val();
				jQuery.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${pageContext.request.contextPath}/SE_SM_PA_MA/codeCheck?code='+code+"&tabInfo="+tabInfo,
					cache : false,
					async : false,
					dataType : 'json',
					success : function(data) {
						if(data != null){
							if(data.codeCheck){
								var message = E0012.replace("{0}", '<fmt:message key="se_sm_pa_de_msg3" />')
								$("#costCenterCodeError").append("<label>"+message+"</label>");
								isCodeExit = true;
							}
						}
					},
					error : function(data) {
						
					}
				});	
			} 
			if (!isCodeExit) {
				if (!confirm(I0018)){
					return;
				}
				var targetform = document.forms[0];
				targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/save?tabInfo=" + tabInfo;
				targetform.method = "POST";
				targetform.submit();
			}
			
		} else {
			if (!confirm(I0018)){
				return;
			}
			var targetform = document.forms[0];
			targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/update?tabInfo=" + tabInfo;
			targetform.method = "POST";
			targetform.submit();
		}
	}
}
var I0009 = '<fmt:message key="I0009" />';
var I0016 = '<fmt:message key="I0016" />';
function init(){
	var tabInfo  = $("#hdTabInfo").val();
	if (tabInfo == "1") {
		document.getElementById("trDiv").style.display = "block";
	} else if (tabInfo == "2") {
		document.getElementById("elDiv").style.display = "block";
	} else if (tabInfo == "3") {
		document.getElementById("eiDiv").style.display = "block";
	} else if (tabInfo == "4") {
		document.getElementById("ccDiv").style.display = "block";
	}
	if ($("#hdHasFinish").val() == "1") {
		if ($("#hdSaveDivision").val() == "add") {
			alert(I0016);
		} else {
			alert(I0009);
		}
		var targetform = document.forms[0];
		targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/init?tabInfo=" + tabInfo;
		targetform.method = "POST";
		targetform.submit();
	}
	
}

var I0011 = '<fmt:message key="I0011" />';
function back() {
	var form = document.getElementById("seSmPaMaUpBean");
	if (isFormEdited(form)){
		if (!confirm(I0011)) {
			return;
		}
	}
	var targetform = document.forms[0];
	var tabInfo = $("#hdTabInfo").val();
	targetform.action = "${pageContext.request.contextPath}/SE_SM_PA_MA/init?tabInfo="+tabInfo;
	targetform.method = "POST";
	targetform.submit();
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
var E0008 = '<fmt:message key="E0008" />';
var E0011 = '<fmt:message key="E0011" />';
var E0014 = '<fmt:message key="E0014" />';
function validateForm(){
	clearError();
	var notError = true;
	var hdTabInfo = $("#hdTabInfo").val();
	
	// 级别
	var levelName  = $("#levelName").val();
	if (levelName == "" && hdTabInfo == "2") {
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg1" />')
		$("#levelNameError").append("<label>"+message+"</label>");
		notError = false;
	}
	// 报销项目
	var expenseName = $("#expenseName").val();
	if (expenseName == "" && hdTabInfo == "3") {
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg2" />')
		$("#expenseNameError").append("<label>"+message+"</label>");
		notError = false;
	}
	// 计算单价
	var extendsFileCo = $("#extendsFileCo").val();
	if (extendsFileCo != "" && hdTabInfo == "3" && !isInteger(extendsFileCo)) {
		var message = E0014.replace("{0}", '<fmt:message key="se_sm_pa_de_msg8" />')
		$("#extendsFileCoError").append("<label>"+message+"</label>");
		notError = false;
	}
	var showOrderNo = $("#showOrderNo").val();
	if (hdTabInfo == "3") {
		if (showOrderNo == "") {
			var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg5" />')
			$("#showOrderNoError").append("<label>"+message+"</label>");
			notError = false;
		}
		if (showOrderNo.length > 3 || isNaN(showOrderNo)) {
			$("#showOrderNoError").append("<label>"+E0011+"</label>");
			notError = false;
		}
	}
	
	// 计费方式
	if (hdTabInfo == "3") {
		if (document.getElementById("timeMethod1").checked == false
				&& document.getElementById("timeMethod2").checked == false
				&& document.getElementById("timeMethod3").checked == false) {
			var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg6" />')
			$("#timeMethodError").append("<label>"+message+"</label>");
			notError = false;
		}
	}
	
	// 成本中心
	var costCenterCode = $("#costCenterCode").val();
	if (costCenterCode == "" && hdTabInfo == "4") {
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg3" />')
		$("#costCenterCodeError").append("<label>"+message+"</label>");
		notError = false;
	}
	var costCenterName = $("#costCenterName").val();
	if (costCenterName == "" && hdTabInfo == "4") {
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg4" />')
		$("#costCenterNameError").append("<label>"+message+"</label>");
		notError = false;
	}
	var costCenterName = $("#costCenterDisplayName").val();
	if (costCenterName == "" && hdTabInfo == "4") {
		var message = E0008.replace("{0}", '<fmt:message key="se_sm_pa_de_msg7" />')
		$("#costCenterDisplayNameError").append("<label>"+message+"</label>");
		notError = false;
	}


	return notError;
}

function clearError(){
	
	$("#showOrderNoError").html(errorImg);
	$("#levelNameError").html(errorImg);
	$("#expenseNameError").html(errorImg);
	$("#costCenterCodeError").html(errorImg);
	$("#costCenterNameError").html(errorImg);
	$("#timeMethodError").html(errorImg);
	$("#costCenterDisplayNameError").html(errorImg);
	$("#extendsFileCoError").html(errorImg);
	
}
</script>
</head>
<body onload="init();initializeSelectOne();">
	<form:form id="seSmPaMaUpBean" modelAttribute="seSmPaMaUpBean" commandName="seSmPaMaUpBean">
	<input type="hidden" id="hdHasFinish" value="${ hdHasFinish }"/>
	<input type="hidden" id="hdSaveDivision" value="${ saveDivision }"/>
	<input type="hidden" id="hdTabInfo" value="${ tabInfo }"/>
	<c:if test='${saveDivision == "add"}'>
   		<div class="position"><fmt:message key="se_sm_pa_de_position1" /></div>
   	</c:if>
   	<c:if test='${saveDivision == "update"}'>
   		<div class="position"><fmt:message key="se_sm_pa_de_position2" /></div>
   	</c:if>
	
    <div class="list_main_box" id="elDiv" style="display:none">
    <c:if test='${saveDivision == "add"}'>
   	<div class="list_title"><fmt:message key="se_sm_pa_de_level_title1" /></div> 
   	</c:if>
   	<c:if test='${saveDivision == "update"}'>
   	<div class="list_title"><fmt:message key="se_sm_pa_de_level_title2" /></div> 
   	</c:if>
   	
	<div class="list_box1">
       	<table border="0" cellpadding="0" cellspacing="0">
           	<tbody>                   
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_levelname" /></td>
                   	<td class="td_box">
                   		<form:input path="levelName" cssClass="input_box" />
                   		<span id="levelNameError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   <tr>
                  	  <td>
                  	  	<div class="add_details_left_btn"><a href="#" onclick="back()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
                  	  	</td>
                     <td class="td_box">
                     	<div class="btn_left_padding">
                     		<wa:showAtag id="SE_SM_PA_DE_LevelSave" onclick="save('${ saveDivision }')" innerImg="/images/save_btn.png"></wa:showAtag>
                     	</div>
                  	 </td>
                   </tr>
                   
               </tbody>
           </table>
           <form:hidden path="levelCode"/>
    </div>
    </div>
    <div class="list_main_box" id="eiDiv" style="display:none">
    <c:if test='${saveDivision == "add"}'>
    	<div class="list_title"><fmt:message key="se_sm_pa_de_expense_title1" /></div> 
    </c:if>
    <c:if test='${saveDivision == "update"}'>
    	<div class="list_title"><fmt:message key="se_sm_pa_de_expense_title2" /></div> 
    </c:if>
	<div class="list_box1">
       	<table border="0" cellpadding="0" cellspacing="0">
           	<tbody>
                   <tr>
                   	<td class="td_font" style="width:200px"><fmt:message key="se_sm_pa_de_expense_name" /></td>
                   	<td class="td_box">
                   		<form:input path="expenseName" cssClass="input_box" />
                   		<span id="expenseNameError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_expense_fname" /></td>
                   	<td class="td_box">
                   		<form:select path="fatherExpenseCode" cssClass="select_box">
                   			<form:option value="S000">&nbsp;</form:option>
                    		<c:forEach var="seList" items="${ ExpenseItemSelect }">
                    			<form:option value="${ seList.expenseCode }">${ seList.expenseName }</form:option>
                    		</c:forEach>
                    	</form:select>
                   	</td>
                   </tr>
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_expense_timeMethod" /></td>
                   	<td class="td_box">
                   		<span style="display:inline-block;width:183px;line-height:30px">
                   		<form:radiobutton path="timeMethod" value="1" id="timeMethod1"/>&nbsp;<label for="timeMethod1"><fmt:message key="se_sm_pa_de_expense_day" /></label>
                   		<form:radiobutton path="timeMethod" value="2" id="timeMethod2"/>&nbsp;<label for="timeMethod2"><fmt:message key="se_sm_pa_de_expense_interval" /></label>
                   		<form:radiobutton path="timeMethod" value="3" id="timeMethod3"/>&nbsp;<label for="timeMethod3"><fmt:message key="se_sm_pa_de_expense_month" /></label>
                   		</span>
                   		<span id="timeMethodError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_expense_fieldNm" /></td>
                   	<td class="td_box">
                   		<form:input path="extendsFileCo" cssClass="input_box" />
                   		<span id="extendsFileCoError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_expense_financeNo" /></td>
                   	<td class="td_box"><form:input path="financeNo" cssClass="input_box" /></td>
                   </tr>
                   
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_expense_orderNo" /></td>
                   	<td class="td_box">
                   		<form:input path="showOrderNo" cssClass="input_box" />
                   		<span id="showOrderNoError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   <tr>
                  	  <td>
                  	  	<div class="add_details_left_btn"><a href="#" onclick="back()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
                     </td>
                     <td class="td_box">
                     	<div class="btn_left_padding">
                     		<wa:showAtag id="SE_SM_PA_DE_ExpenseSave" onclick="save('${ saveDivision }')" innerImg="/images/save_btn.png"></wa:showAtag>
                     	</div>
                     </td>
                   </tr>
                   
               </tbody>
           </table>
           <form:hidden path="expenseCode"/>
    </div>
    </div>
    <div class="list_main_box" id="ccDiv" style="display:none">
    <c:if test='${saveDivision == "add"}'>
    	<div class="list_title"><fmt:message key="se_sm_pa_de_cost_title1" /></div> 
    </c:if>
    <c:if test='${saveDivision == "update"}'>
    	<div class="list_title"><fmt:message key="se_sm_pa_de_cost_title2" /></div> 
    </c:if>
   	
	<div class="list_box1">
       	<table border="0" cellpadding="0" cellspacing="0">
           	<tbody>
               	<tr>
                   	<td class="td_font" style="width:200px"><fmt:message key="se_sm_pa_de_costcode" /></td>
                   	<td class="td_box">
                    	<c:if test='${saveDivision == "add"}'>
							<form:input id="costCenterCode" path="costCenterCode" cssClass="input_box"/>
						</c:if>
						<c:if test='${saveDivision == "update"}'>
							<form:input id="costCenterCode" path="costCenterCode" cssClass="input_box" readonly="true"/>
						</c:if>
						<span id="costCenterCodeError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
					</td>
                   </tr>
                   
                   <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_costname" /></td>
                   	<td class="td_box">
                   		<form:input path="costCenterName" cssClass="input_box" />
                   		<span id="costCenterNameError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   
                    <tr>
                   	<td class="td_font"><fmt:message key="se_sm_pa_de_costdisplayname" /></td>
                   	<td class="td_box">
                   		<form:input path="costCenterDisplayName" cssClass="input_box" />
                   		<span id="costCenterDisplayNameError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
						</span> 
                   	</td>
                   </tr>
                   
                   <tr>
                  	  <td>
                  	  <div class="add_details_left_btn"><a href="#" onclick="back()"><img src="<c:url value='/images/return_btn.png'/>"/></a></div>
                     
                  	  	</td>
                     <td class="td_box">
                     	<div class="btn_left_padding">
                     		<wa:showAtag id="SE_SM_PA_DE_CostCenterSave" onclick="save('${ saveDivision }')" innerImg="/images/save_btn.png"></wa:showAtag>
                     	</div>
                  	  
                     </td>
                   </tr>
                   
               </tbody>
           </table>
    </div>
	</div>
	
	</form:form>
</body>
</html>