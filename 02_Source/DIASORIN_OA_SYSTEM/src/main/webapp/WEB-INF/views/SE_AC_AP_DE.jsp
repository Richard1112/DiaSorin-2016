<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tagLib.tld" prefix="wa" %>
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
<link href="<c:url value='/css/style.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-3.0.3.min.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-multiselect.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/prettify.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/bootstrap/css/bootstrap-datepicker.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>

<link href="<c:url value='/css/bootstrapself.css' />" type="text/css" rel="stylesheet" />

<script>

var I0001 = '<fmt:message key="I0001" />';
var I0002 = '<fmt:message key="I0002" />';
function back(){
	var mode = $("#mode").val();
	var targetForm = document.forms[0];
	if (mode == "0") {
		targetForm.action = "${pageContext.request.contextPath}/SE_EC_EP_DE/detailBackToClaim";
		targetForm.method = "POST";
		targetForm.submit();
	} else if (mode == "1") {
		targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/page?curPage=1";
		targetForm.method = "POST";
		targetForm.submit();
	} else if (mode == "2") {
		targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/page?curPage=1";
		targetForm.method = "POST";
		targetForm.submit();
	}
}

function reject(){
	if(confirm(I0001)) {
		$('#rejectModal').modal('show');
	}
}

var I0009 = '<fmt:message key="I0009" />';
var I0010 = '<fmt:message key="I0010" />';
function approved(){
	var expenseAppNo = $("#expenseAppNo").val();
	if (confirm(I0002)) {
		$.ajax({
			type : "GET",
			contentType:'application/json',
			url : '${pageContext.request.contextPath}/SE_AC_AP_DE/approve?expenseAppNo='+expenseAppNo,
			dataType : "json",
			data : "", 
			success : function(data) {
				if(!data.isException) {
					alert(I0009);
					var targetForm = document.forms[0];
					targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/init";
					targetForm.method = "POST";
					targetForm.submit();
				}
			},
			error : function(data) {
				alert(I0010);
			}
		});
	}
}
var I0019 = '<fmt:message key="I0019" />';
// 财务完成按钮
function finish(){
	var expenseAppNo = $("#expenseAppNo").val();
	if (confirm(I0019)) {
		$.ajax({
			type : "GET",
			contentType:'application/json',
			url : '${pageContext.request.contextPath}/SE_AC_AP_DE/approve?expenseAppNo='+expenseAppNo,
			dataType : "json",
			data : "", 
			success : function(data) {
				if(!data.isException) {
					alert(I0009);
					var targetForm = document.forms[0];
					targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/init";
					targetForm.method = "POST";
					targetForm.submit();
				}
			},
			error : function(data) {
				alert(I0010);
			}
		});
	}
}

//财务撤销完成按钮
var I0020 = '<fmt:message key="I0020" />';
function undoFinish(){
	var expenseAppNo = $("#expenseAppNo").val();
	if (confirm(I0020)) {
		$.ajax({
			type : "GET",
			contentType:'application/json',
			url : '${pageContext.request.contextPath}/SE_AC_AP_DE/undoFinished?expenseAppNo='+expenseAppNo,
			dataType : "json",
			data : "", 
			success : function(data) {
				if(!data.isException) {
					alert(I0009);
					var targetForm = document.forms[0];
					targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/init";
					targetForm.method = "POST";
					targetForm.submit();
				}
			},
			error : function(data) {
				alert(I0010);
			}
		});
	}
}

var E0008 = '<fmt:message key="E0008" />';
function rejectSubmit(){
	// 判断驳回理由是不是已经录入
	var rejectReason = $("#rejectReason").val();
	if (rejectReason == "") {
		var message = E0008.replace("{0}", '<fmt:message key="common_reject_content" />')
		alert(message)
		return;
	}
	// 得到被标记为错误明细的记录
	var detailNo = "";
	var allCheck = $("#detailTable input[type=checkbox]");
	if (allCheck != null && allCheck.length > 0) {
		for (var i = 0; i < allCheck.length ; i++) {
			if (allCheck[i].checked == true) {
				detailNo += allCheck[i].value + ",";
			}
		}
	}
	$("#errorDetailNo").val(detailNo);
	var jsonMap = {
			errorDetailNo : detailNo,
			applicationNo : $("#expenseAppNo").val(),
			rejectReason : $("#rejectReason").val()
	};
	
	// 发送AJAX
	$.ajax({
		type : "POST",
		contentType:'application/json',
		url : '${pageContext.request.contextPath}/SE_AC_AP_DE/reject',
		dataType : "json",
		data : JSON.stringify(jsonMap), 
		success : function(data) {
			if(!data.isException) {
				alert(I0009);
				var targetForm = document.forms[0];
				targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/init";
				targetForm.method = "POST";
				targetForm.submit();
			} 
		},
		error : function(data) {
			alert(I0010);
		}
	});
	
}

function print(){
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_DE/print";
	targetForm.method = "POST";
	targetForm.submit();
}
</script>
</head>

<body style="background:#f2f2f2;">
<form:form id="seAcApDeBean" modelAttribute="seAcApDeBean" commandName="seAcApDeBean">
<div class="list_box">
	<div class="list_top">
    	<div class="list_logo fl"><img src="<c:url value='/images/list_logo.jpg'/>"/></div>
        <div class="list_font fl"><fmt:message key="se_ac_ap_de_title" /></div>
        <div class="list_lb">
        	<ul>
              <li style="width:200px;text-align:left">SB1 Code：${seAcApDeBean.employee}</li>
              <li style="width:350px;text-align:left">Cost Center: ${seAcApDeBean.costCenter}/${seAcApDeBean.costCenterName}</li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
    
    <div class="list_table3">
    	<table border="0" cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<td class="l_title"><fmt:message key="se_ac_ap_de_date" /></td>
                    <td class="l_left">${seAcApDeBean.claimDateFrom } ~ ${seAcApDeBean.claimDateTo }</td>
                    <td class="l_title"><fmt:message key="se_ac_ap_de_tly" /></td>
                    <td class="l_left">${seAcApDeBean.travelLocalType }</td>
                </tr>
                <tr>
                	<td class="l_title"><fmt:message key="se_ac_ap_de_tr" /></td>
                    <td class="l_left" colspan="3"> ${seAcApDeBean.travelReason }</td>
                </tr>
            </tbody>
        </table>
        
  	</div>
    <div class="t_left">
    	<h4><span><fmt:message key="se_ac_ap_summary" /></span></h4>
    </div>
    <div class="list_tableScroll" style="height:155px;width:657px;overflow-y:auto;">
            <table border="0" cellpadding="0" cellspacing="0" style="width:640px">
        	<tbody>
        		<tr class="tr_title">
	                <td class="l_no"><fmt:message key="se_ac_ap_no" /></td>
	                <td width="450px"><fmt:message key="se_ac_ap_purpose" /></td>
	                <td width="150px"><fmt:message key="se_ac_ap_totalamount" /></td>
            	</tr>
            	<c:forEach var="purpose" items="${ seAcApDeBean.purposeList }" varStatus="status">
                <tr>
                    <td class="l_no">${status.index + 1 }</td>
                    <td style="text-align: left;">${purpose.purpose }</td>
                    <td style="text-align: right;">
                    	<fmt:formatNumber pattern="#,#00.00#" value="${purpose.amount }"></fmt:formatNumber>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
    </div>
    
    <div class="t_left">
        	<h4><span><fmt:message key="se_ac_ap_details" /></span></h4>
        </div>
    <div class="list_tableScroll" style="height:276px; width:1167px;overflow-y:auto;">
    
        <table border="0" cellpadding="0" cellspacing="0" style="width:1150px" id="detailTable">
        	<tbody>
            	<tr class="tr_title">
                    <td class="l_no"><fmt:message key="se_ac_ap_no" /></td>
                    <c:if test="${seAcApDeBean.mode == '1' }">
                    <td style="width:50px">Error</td>
                    </c:if>
                    <td style="width:180px"><fmt:message key="se_ac_ap_day" /></td>
                    <td style="width:130px"><fmt:message key="se_ac_ap_location" /></td>
                    <td style="width:190px"><fmt:message key="se_ac_ap_expenseType" /></td>
                    <td style="width:130px"><fmt:message key="se_ac_ap_amount" /></td>
                    <td style="width:370px"><fmt:message key="se_ac_ap_comments" /></td>
                </tr>
                <c:forEach var="detail" items="${ seAcApDeBean.detailList }" varStatus="status">
                <tr>
                    <td class="l_no">${status.index + 1 }</td>
                    <c:if test="${seAcApDeBean.mode == '1' }">
                    	<td style="width:50px">
                    		<input type="checkbox" value="${ detail.no }" />
                    	</td>
                    </c:if>
                    <c:if test="${detail.timeMethod == 1}">
                    	<td style="text-align:left">${ detail.dayTo}</td>
                    </c:if>
                    <c:if test="${ detail.timeMethod == 2}">
                    	<td style="text-align:left">${ detail.dayFrom} ~ ${ detail.dayTo} </td>
                    </c:if>
                    <c:if test="${ detail.timeMethod == 3}">
                    	<td style="text-align:left">${ detail.dayTo.substring(0,7)} </td>
                    </c:if>
                    <td style="text-align:center">${ detail.location}</td>
                    <td>${ detail.expenseType}</td>
                    <td style="text-align:right">${ detail.expenseAmount}</td>
                    <td style="text-align:left">${ detail.comments}</td>
                </tr>
				</c:forEach>
                
            </tbody>
        </table>
        
		</div>
        <table class="totalDiv">
            	<tr>
            		<td style="width:600px;text-align:right">
            			<label>Total:</label>&nbsp;
            		</td>
            		<td style="width:300px;text-align:right">
            			<label style="padding-right:165px"><fmt:formatNumber pattern="#,#00.00#" value="${seAcApDeBean.totalAmount }"></fmt:formatNumber></label>
            		</td>
            	</tr>
      	</table>
        
	    <div class="t_left">
	    <c:if test="${seAcApDeBean.mode == '1' }">
        	<h4><span><fmt:message key="se_ac_ap_reviewedby" /></span></h4>
        </c:if>
        <c:if test='${!empty authorityHisList}'>
            <h4><span><fmt:message key="se_ac_ap_authorizedby" /></span></h4>
	        <div class="list_table3">
	        <table style="width:510px;table-layout:fixed">
	         	<tbody>
	         		<tr class="tr_title">
	                  	<td width="150px"><fmt:message key="se_ac_ap_de_auBy" /></td>
	                  	<td width="120px"><fmt:message key="se_ac_ap_de_auDate" /></td>
	                  	<td width="120px"><fmt:message key="se_ac_ap_de_auStatus" /></td>
	                  	<td width="360px"><fmt:message key="se_ac_ap_de_auComm" /></td>
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
        </div>
        
        <div class="t_qz">
        	<span><fmt:message key="se_ac_ap_employee" />${seAcApDeBean.employee}</span>
        	<span><fmt:message key="se_ac_ap_claimdate" />${seAcApDeBean.applicationDate}</span>
        </div>
        
        <div class="list_btn_box">
        	<div class="left_btn">
            	<a href="#" onclick="back()"><img src="<c:url value='/images/back_btn.png'/>"/></a>
            	<wa:showAtag id="SE_AC_AP_DE_Export" onclick="print(); return false;" innerImg="/images/export.png"></wa:showAtag>
            </div>
            <c:if test="${seAcApDeBean.mode == '1' }">
            <div class="right_btn">
            	<c:if test="${seAcApDeBean.approveFlg == null ||  seAcApDeBean.approveFlg == ''}">
            	<wa:showAtag id="SE_AC_AP_DE_Approve" onclick="approved(); return false" innerImg="/images/approve_btn.png"></wa:showAtag>
                </c:if>
                
                <c:if test="${seAcApDeBean.appStatus != '4' && seAcApDeBean.appStatus != '5'}">
                <wa:showAtag id="SE_AC_AP_DE_Reject" onclick="reject(); return false" innerImg="/images/reject_btn.png"></wa:showAtag>
                </c:if>
                
                <c:if test="${seAcApDeBean.approveFlg == '1' }">
                	<wa:showAtag id="SE_AC_AP_DE_Finish" onclick="finish(); return false" innerImg="/images/finish.png"></wa:showAtag>
                </c:if>
                <c:if test="${seAcApDeBean.approveFlg == '2' }">
                	<wa:showAtag id="SE_AC_AP_DE_UndoFinish" onclick="undoFinish(); return false" innerImg="/images/undoFinish.png"></wa:showAtag>	
                </c:if>
                
            </div>
            </c:if>
        </div>
        
        <div class="modal fade" id="rejectModal" tabindex="-1" role="dialog" aria-hidden="true">
      		<div class="modal-dialog" id="RejectDialog">
      		<div class="modal-content">

	      		<div class='modal-header'>

					<a class='close' data-dismiss='modal'><fmt:message key="common_x" /></a>
					<h4><fmt:message key="common_reject_content" /></h4>
				</div>
				
				<div class='modal-body'>
				
					<form:textarea path="rejectReason" style="width:560px" rows="5"/>
				
				</div>
				
				<div class='modal-footer'>
				
					<a href='#' onclick="rejectSubmit(); return false;"><img src="<c:url value='/images/reject_btn.png'/>"/></a>
									
				</div>
	      	</div>
	      	</div>
    	</div>
  
    
</div>
<form:hidden path="mode"/>
<form:hidden path="expenseAppNo"/>
<form:hidden path="errorDetailNo"/>
</form:form>
</body>
</html>
