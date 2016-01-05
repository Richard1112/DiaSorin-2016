<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
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
<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-multiselect.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>

<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/commonFunction.js' />" type="text/javascript"></script>


<script>

var E0002 = '<fmt:message key="E0002" />';
var I0006 = '<fmt:message key="I0006" />';
$(document).ready(function() {
    $('.multiselect').multiselect();
    
    $('.inputDate').datepicker({
    	format: "yyyy/mm/dd",
        clearBtn: true,
        orientation: "top right",
        autoclose: true,
        todayHighlight: true
    }); 
});

function toAddDetail(){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/toAddDetail";
	targetForm.method = "POST";
	targetForm.submit();
}

var I0011 = '<fmt:message key="I0011" />';
function toBack(){
	

	var form = document.getElementById("seEcEpCaBean");
	if (isFormEdited(form) || $("#hasChanged").val() == "1"){
		if (!confirm(I0011)) {
			return;
		}
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/init";
	targetForm.method = "POST";
	targetForm.submit();
}

function modify(str){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/toDetail?detailNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}

function deletePurpose(no,thisObj){
	// 这里应该先将后台的SESSION中得数据删除
	if (!confirm(I0006)) {
		return;
	}
	$.ajax({
		type : "GET",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_EC_EP_CA/deletePurpose?purposeNo='+no,
		dataType : "json",
		data : "", 
		success : function(data) {
			if(!data.isException) {
				// 没有错误发生时，将画面上得当前行的记录删除
				$(thisObj).parent().parent().remove();
				//重新变换显示NO
				var rowsObject = $("#detailRow tr:not(:first)");
				for (var i = 0; i < rowsObject.length; i++) {
					$(rowsObject[i]).children("td").eq(0).html(i+1);
				}
				calculate();
			} else {
				
			}
		},
		error : function(data) {
		}
	});
}

var I0014 = '<fmt:message key="I0014" />';
var I0015 = '<fmt:message key="I0015" />';
var E0010 = '<fmt:message key="E0010" />';
// 一时保存按钮按下
function saveData(){
	var expenseAppNo = $("#expensesAppNo").val();
	var rows = $("#detailRow tr:not(:first)");
	if (rows.length == 0 && expenseAppNo == "") {
		alert(E0010);
		return;
	}
	if (!validateForm()) return;
	if (checkUpAllow()) return;
	if (!confirm(I0014)) {
		return;
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/claimContentSave";
	targetForm.method = "POST";
	targetForm.submit();
}

// 提交
function submitData(){
	var expenseAppNo = $("#expensesAppNo").val();
	var rows = $("#detailRow tr:not(:first)");
	if (rows.length == 0) {
		alert(E0010);
		return;
	}
	if (!validateForm()) return;
	if (checkUpAllow()) return;
	if (!confirm(I0015)) {
		return;
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/claimContentSubmit";
	targetForm.method = "POST";
	targetForm.submit();
}

function checkUpAllow(){
	var travelLocalType = $("#travelLocalType").val();
	var checkOver = false;
	$.ajax({
		type : "GET",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_EC_EP_CA/checkDataIsOver?travelLocalType='+travelLocalType,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			if(!data.isException) {
				// 申请的
				if (data.isOver){
					alert(data.OverContent + " is over you authority in this travelType");
					checkOver = true;
				} else if (data.hasSameExpenseMonth){
					alert(E0002);
					checkOver = true;
				} else {
					checkOver = false;
				}
			} else {
				checkOver = true;
			}
		},
		error : function(data) {
			checkOver = true;
		}
	});
	return checkOver;
}

function review(){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_CA/review";
	targetForm.method = "POST";
	targetForm.submit();
}

var I0012 = '<fmt:message key="I0012" />';
var I0013 = '<fmt:message key="I0013" />';
function init(){
	calculate();
	var hiddenStatus = $("#hiddenStatus").val();
	if (hiddenStatus != "3" && hiddenStatus != "0") {
		$("#claimDateFrom").attr("disabled","disabled")
		$("#claimDateTo").attr("disabled","disabled")
		$("#travelLocalType").attr("disabled","disabled")
		$("#travelReason").attr("disabled","disabled")
		$("#costCenter").attr("disabled","disabled")
	}
	
	//跳转到一览画面。
	var hiddenHasSaved = $("#hiddenHasSaved").val();
	if (hiddenHasSaved == "1") {
		//SAVE
		alert(I0012);
		var targetForm = document.forms[0];
		targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/init";
		targetForm.method = "POST";
		targetForm.submit();
	} else if (hiddenHasSaved == "2") {
		//SUBMIT
		alert(I0013);
		var targetForm = document.forms[0];
		targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_LS/init";
		targetForm.method = "POST";
		targetForm.submit();
	}
	
	
}

function calculate(){
	var rows = $("#detailRow tr:not(:first)");
	var amount = 0;
	for (var i = 0; i < rows.length; i++) {
		amount += parseFloat($(rows[i]).children("td").eq(5).text().replace(",",""));
	}
	$("#totalAmount").html(fmoney(amount,2));
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
var E0008 = '<fmt:message key="E0008" />';
var E0007 = '<fmt:message key="E0007" />';
function validateForm(){
	clearError();
	var notError = true;
	var claimDateFrom  = $("#claimDateFrom").val();
	var claimDateTo = $("#claimDateTo").val();
	if (claimDateFrom != "" && claimDateTo != "") {
		if (claimDateFrom > claimDateTo) {
			var message = E0007.replace("{0}", '<fmt:message key="se_ec_ep_ls_msg1" />')
			$("#claimDateError").append("<label>"+message+"</label>");
			notError = false;
		}
	}
	// 出差地类型
	var travelLocalType = $("#travelLocalType").val();
	if (travelLocalType == ""){
		var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_ls_msg2" />')
		$("#travelLocalError").append("<label>"+message+"</label>");
		notError = false;
	}
	// 成本中心
	var costCenter = $("#costCenter").val();
	if (costCenter == ""){
		var message = E0008.replace("{0}", '<fmt:message key="se_ec_ep_ls_msg3" />')
		$("#costCenterError").append("<label>"+message+"</label>");
		notError = false;
	}
	return notError;
}

function clearError(){
	$("#claimDateError").html(errorImg);
	$("#travelLocalError").html(errorImg);
	$("#costCenterError").html(errorImg);
}

</script>
</head>

<body onload="init();initializeSelectOne();">
<form:form id="seEcEpCaBean" modelAttribute="seEcEpCaBean" commandName="seEcEpCaBean">
	<div class="position_none_boots"><fmt:message key="se_ec_ep_ca_position" /></div>
    <form:hidden path="hasChanged"/>
    <form:hidden path="expensesAppNo"/>
    <form:hidden path="appStatus"/>
  
    <input type="hidden" value="${hasSaved}" id="hiddenHasSaved"/>
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_ec_ep_ca_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_ec_ep_ca_date" /></td>
                    	<td class="td_box">
                            <form:input id="claimDateFrom" path="claimDateFrom" cssClass="inputDate input_date_box"/>
                            ~
                            <form:input id="claimDateTo" path="claimDateTo" cssClass="inputDate input_date_box"/>
                            <span id="claimDateError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                        </td>
                        <td class="td_font"><fmt:message key="se_ec_ep_ca_tlt" /></td>
                        <td class="td_box">
                          	<form:select path="travelLocalType" cssClass="select_box" style="width:230px">
                   				<form:option value=""></form:option>
                    			<c:forEach var="seList" items="${ TravelTypeSelect }">
                    				<form:option value="${ seList.key }">${ seList.value }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<span id="travelLocalError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                        </td>
                    </tr>
                    
                    <tr>
                    	<td class="td_font"><fmt:message key="se_ec_ep_ca_tr" /></td>
                    	<td  class="td_box">
                            <form:textarea path="travelReason" cssClass="reason_box"/>
                        </td>
                         <td class="td_font"><fmt:message key="se_ec_ep_ca_cc" /></td>
                    	<td class="td_box">
                            <form:select path="costCenter" class="select_box" style="width:230px">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterList }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                    		<span id="costCenterError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                        </td>
                    </tr>
                    
                    <tr>
                        <td colspan="2" class="td_box">
                        	<div class="add_details_btn">
                        		<c:if test="${ seEcEpCaBean.appStatus == 0 || seEcEpCaBean.appStatus == 3 }">
                        		<wa:showAtag id="SE_EC_EP_CA_AddDetails" onclick="toAddDetail()" innerImg="/images/add_details_btn.png"></wa:showAtag>
                        		</c:if>
                        	</div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody id="detailRow">
                
                	<tr class="td_title">
                    	<td width="45px"><fmt:message key="se_ec_ep_ca_deno" /></td>
                    	<td width="150px"><fmt:message key="se_ec_ep_ca_deEan" /></td>
                    	<td width="150px"><fmt:message key="se_ec_ep_ca_dePn" /></td>
                        <td width="490px"><fmt:message key="se_ec_ep_ca_dePur" /></td>
                        <td width="40px" style="border-right:0px"></td>
                        <td width="110px" style="text-align:left;"><fmt:message key="se_ec_ep_ca_detotal" /></td>
                        <td width="200px"></td>
                    </tr>
                    <c:forEach var="beanList" items="${ seEcEpCaBean.beanList }" varStatus="status">
                    <tr <c:if test="${beanList.rejectErrorFlg == '1'}">class="errorTr"</c:if>>
                    	<td>${beanList.no}</td>
                    	<td>${beanList.expensesAppNo}</td>
                    	<td>${beanList.purposeNo}</td>
                        <td style="text-align:left;">${beanList.purpose}</td>
                        <td style="border-right:0px">CNY</td>
                        <td style="text-align:right;">${beanList.total}</td>
                        <td>
                        	<wa:showAtag id="SE_EC_EP_CA_ModifyDetails" onclick="modify('${status.index}')" value="Details"></wa:showAtag>
                        	<c:if test="${ seEcEpCaBean.appStatus == 0 || seEcEpCaBean.appStatus == 3 }">
                        	<wa:showAtag id="SE_EC_EP_CA_DeleteDetails" onclick="deletePurpose('${beanList.detailNo}',this)" value="Delete"></wa:showAtag>
                        	</c:if>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
			</table>
            
      </div>
      <table class="totalDiv">
         	<tr>
         		<td style="width:800px;text-align:right">
         			<label class="total"><fmt:message key="se_ec_ep_ca_tc" /></label>&nbsp;
         		</td>
         		<td style="text-align:right;padding-right:192px">
         			<label id="totalAmount" class="amount">0.00</label>
         		</td>
         	</tr>
      </table>
      <c:if test='${!empty authorityHisList}'>
      <div class="list_box2">
      <table style="width:510px;table-layout:fixed">
         	<tbody>
         		<tr class="td_title">
                  	<td width="150px"><fmt:message key="se_ec_ep_ca_deaub" /></td>
                  	<td width="120px"><fmt:message key="se_ec_ep_ca_deaudate" /></td>
                  	<td width="120px"><fmt:message key="se_ec_ep_ca_deaustatus" /></td>
                  	<td width="360px"><fmt:message key="se_ec_ep_ca_decomments" /></td>
               	</tr>
               	<c:forEach var="beanList" items="${authorityHisList }">
               	<tr>
                  	<td>${beanList.authorityBy }</td>
                  	<td>${beanList.authorityDate }</td>
                  	<td>${beanList.statusView }</td>
                  	<td title="${beanList.comment }" style="overflow:hidden;white-space:nowrap;word-break:keep-all;text-align:left">${beanList.comment }</td>
               	</tr>
               	</c:forEach>
         	</tbody>
      </table>
      </div>
      </c:if>
      <div class="list_btn_box">
	            <c:if test='${ listCount != "0" }'>
	            	<div class="review_btn">
	            		<wa:showAtag id="SE_EC_EP_CA_Review" onclick="review()" innerImg="/images/review_btn.png"></wa:showAtag>
	            	</div>       
	            </c:if>
	            <c:if test='${ listCount != "0" }'>
	            	<c:if test="${ seEcEpCaBean.appStatus == 0 || seEcEpCaBean.appStatus == 3 }">
		                <div class="submit_btn">
		                	<wa:showAtag id="SE_EC_EP_CA_Submit" onclick="submitData()" innerImg="/images/submit_btn.png"></wa:showAtag>
		                </div>
		                <div class="save_btn">
		                	<wa:showAtag id="SE_EC_EP_CA_Save" onclick="saveData()" innerImg="/images/save_btn.png"></wa:showAtag>
		                </div>
                	</c:if>
                </c:if>
                <div class="back_btn"><a href="#" onclick="toBack()"><img src="<c:url value='/images/back_btn.png'/>"/></a></div>
            </div>
    </div>
    <input type="hidden" value="${ seEcEpCaBean.appStatus }" id="hiddenStatus"/>
</form:form>  
</body>
</html>
