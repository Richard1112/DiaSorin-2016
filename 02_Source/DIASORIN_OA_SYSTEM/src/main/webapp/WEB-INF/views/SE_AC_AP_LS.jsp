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

<link href="<c:url value='/css/user-common.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/cover.css' />" type="text/css" rel="stylesheet" />
<link href="<c:url value='/css/bootstrapself.css' />" type="text/css" rel="stylesheet" />

<script src="<c:url value='/bootstrap/js/jquery-2.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-3.0.3.min.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-multiselect.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/prettify.js' />" type="text/javascript"></script>
<script src="<c:url value='/bootstrap/js/bootstrap-datepicker.js' />" type="text/javascript"></script>
<script src="<c:url value='/js/user-common.js' />" type="text/javascript"></script>


<script>

var W0001 = '<fmt:message key="W0001" />';
var I0007 = '<fmt:message key="I0007" />';
var I0008 = '<fmt:message key="I0008" />';
var E0007 = '<fmt:message key="E0007" />';
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

function claimSearch(){
	if (!validateForm()) return;
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/search";
	targetForm.method = "POST";
	targetForm.submit();
}

//分页操作
function Action(action) {
	//当前页 
	var curPage=parseInt($("#curPage").val());
	//总页数 
	var totalPage=parseInt($("#totalPage").val());
	//输入页数 
	var pageNum =parseInt($("#pageNum").val());
	
	if(action == "NEXT"){
		if(curPage < totalPage){
			curPage = curPage + 1;
		}
	}
	if(action == "PREV"){
		if(curPage >= 2){				
			curPage = curPage-1;
		}
	}
	if(action == "GO"){	
		if(pageNum>=1 && pageNum <=totalPage){
			curPage = pageNum;
		}
	}
	
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/page?curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

function toApprove(str) {
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/toApprove?expenseAppNo="+str;
	targetForm.method = "POST";
	targetForm.submit();
}

function clickAll(){
	var details = document.getElementsByName("detailCheck");
	if (details.length == 0){
		document.getElementById("checkAll").checked = false;
		return;
	}
	if (document.getElementById("checkAll").checked) {
		allDetailCheckOrNot(true);
	} else {
		allDetailCheckOrNot(false);
	}
}

function clickDetail(){
	var details = document.getElementsByName("detailCheck");
	var isAllChecked = true;
	for (var i= 0; i< details.length;i++){
		if (!details[i].checked){
			isAllChecked = false;
			break;
		}
	}
	if (isAllChecked) {
		document.getElementById("checkAll").checked = true;
	} else {
		document.getElementById("checkAll").checked = false;
	}
}

function allDetailCheckOrNot(isCheck){
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		details[i].checked = isCheck;
	}
}

// 审批通过操作
var I0024 = '<fmt:message key="I0024" />';
function approved(){
	var curPage=parseInt($("#curPage").val());
	if (!hasCheck()) return;
	var rowNo = getAllCheckedRow();
	var canFinish = true;
	var approveStatus = "4";
	var finishedStatus = "5";
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			if($(details[i]).parent().parent().children("td").eq(9).find("input").val() == approveStatus
					|| $(details[i]).parent().parent().children("td").eq(9).find("input").val() == finishedStatus){
				canFinish = false;
				break;
			}
		}
	}
	if (!canFinish){
		alert(I0024);
		return;
	}
	if (!confirm(I0008)){
		return;
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/approve?expenseAppNo="+rowNo+"&curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
	

}

// 财务完成操作
var I0019 = '<fmt:message key="I0019" />';
var I0021 = '<fmt:message key="I0021" />';
function finish(){
	var curPage=parseInt($("#curPage").val());
	if (!hasCheck()) return;
	var rowNo = getAllCheckedRow();
	
	// 只有已经审批通过的记录才可以进行FINISH的操作
	var canFinish = true;
	var approveStatus = "4";
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			if($(details[i]).parent().parent().children("td").eq(9).find("input").val() != approveStatus){
				canFinish = false;
				break;
			}
		}
	}
	if (!canFinish){
		alert(I0021);
		return;
	}
	if (!confirm(I0019)){
		return;
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/approve?expenseAppNo="+rowNo+"&curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

//财务撤销完成操作
var I0020 = '<fmt:message key="I0020" />';
var I0022 = '<fmt:message key="I0022" />';
function undoFinish(){
	var curPage=parseInt($("#curPage").val());
	if (!hasCheck()) return;
	var rowNo = getAllCheckedRow();
	// 只有已经审批通过的记录才可以进行FINISH的操作
	var canFinish = true;
	var approveStatus = "5";
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			if($(details[i]).parent().parent().children("td").eq(9).find("input").val() != approveStatus){
				canFinish = false;
				break;
			}
		}
	}
	if (!canFinish){
		alert(I0022);
		return;
	}
	if (!confirm(I0020)){
		return;
	}
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/approve?expenseAppNo="+rowNo+"&curPage="+curPage+"&undoFinish=1";
	targetForm.method = "POST";
	targetForm.submit();
}

// 驳回操作
var I0025 = '<fmt:message key="I0025" />';
function reject(){
	if (!hasCheck()) return;
	
	var canFinish = true;
	var approveStatus = "4";
	var finishedStatus = "5";
	var details = document.getElementsByName("detailCheck");
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			if($(details[i]).parent().parent().children("td").eq(9).find("input").val() == approveStatus
					|| $(details[i]).parent().parent().children("td").eq(9).find("input").val() == finishedStatus){
				canFinish = false;
				break;
			}
		}
	}
	if (!canFinish){
		alert(I0025);
		return;
	}
	
	if(confirm(I0007)) {
		$('#rejectModal').modal('show');
	}
	
}

function rejectSubmit(){
	var curPage=parseInt($("#curPage").val());
	var rowNo = getAllCheckedRow();
	var targetForm = document.forms[0];
	targetForm.action = "${pageContext.request.contextPath}/SE_AC_AP_LS/reject?expenseAppNo="+rowNo+"&curPage="+curPage;
	targetForm.method = "POST";
	targetForm.submit();
}

// 获取所有选择行的值
function getAllCheckedRow(){
	var details = document.getElementsByName("detailCheck");
	var rowNo = "";
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			rowNo += details[i].value + ",";
		}
	}
	return rowNo.substring(0, rowNo.length - 1);
}

// 判断是否选中行了。
function hasCheck(){
	var details = document.getElementsByName("detailCheck");
	var hasChecked = false;
	for (var i= 0; i< details.length;i++){
		if (details[i].checked){
			hasChecked = true;
			break;
		}
	}
	if (!hasChecked){
		alert(W0001);
	}
	return hasChecked;
}

var errorImg = '<img src="${pageContext.request.contextPath}/images/error.png"/>';
function validateForm(){
	clearError();
	var notError = true;
	var claimDateFrom  = $("#claimDateFrom").val();
	var claimDateTo = $("#claimDateTo").val();
	if (claimDateFrom != "" && claimDateTo != "") {
		if (claimDateFrom > claimDateTo) {
			var message = E0007.replace("{0}", '<fmt:message key="se_ac_ap_ls_msg1" />')
			$("#claimDateError").append("<label>"+message+"</label>");
			notError = false;
		}
		
	}
	return notError;
}

function clearError(){
	$("#claimDateError").html(errorImg);
}
</script>
</head>

<body>
<form:form id="seAcApLsBean" modelAttribute="seAcApLsBean" commandName="seAcApLsBean">
	<div class="position_none_boots"><fmt:message key="se_ac_ap_ls_position" /></div>
    
	<div class="list_main_box">
   	  <div class="list_title"><fmt:message key="se_ac_ap_ls_title" /></div> 
        <div class="list_box1">
        	<table border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td class="td_font"><fmt:message key="se_ac_ap_ls_costcen" /></td>
                    	<td class="td_box">
                            <form:select path="costCenter" class="select_box" style="width:230px">
                            	<form:option value=""></form:option>
                    			<c:forEach var="bean" items="${ costCenterList }">
                    				<form:option value="${ bean.costCenterCode }">${ bean.costCenterCode }/${ bean.costCenterName }</form:option>
                    			</c:forEach>
                    		</form:select>
                        </td>
                        <td class="td_font"><fmt:message key="se_ac_ap_ls_claimby" /></td>
                        <td class="td_box">
                            <form:input id="claimBy" path="claimBy" cssClass="input_date_box"/>
                        </td>
                    </tr>
                    
                    <tr>
                    	<td class="td_font"><fmt:message key="se_ac_ap_ls_claimdate" /></td>
                    	<td class="td_box">
                             <form:input id="claimDateFrom" path="claimDateFrom" cssClass="inputDate input_date_box"/>
                            ~
                            <form:input id="claimDateTo" path="claimDateTo" cssClass="inputDate input_date_box"/>
                            <span id="claimDateError" class="error" onmouseover="makeMesDiv(this)" onmouseout="removeMesDiv()">
							<img src="<c:url value='/images/error.png'/>"/>
							</span> 
                        </td>
                        <td class="td_font"><fmt:message key="se_ac_ap_ls_status" /></td>
                        <td class="td_box">
                          	<form:select path="status" cssClass="select_box">
                          		<form:option value=""></form:option>
	                   			<c:forEach var="seList" items="${ StatusSelect }">
	                   				<form:option value="${ seList.key }">${ seList.value }</form:option>
	                   			</c:forEach><i class="icon-th"></i>
                   			</form:select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="td_box" colspan="4">
                        	<div class="add_details_center_btn">
                        		<wa:showAtag id="SE_AC_AP_LS_Search" onclick="claimSearch()" innerImg="/images/search_btn.png"></wa:showAtag>
                        	</div>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
        <div class="list_box2">
			<table border="0" cellpadding="0" cellspacing="0" id="detailTable" style="width:1245px">
				<tbody>
                
                	<tr class="td_title">
                		<td width="30px"><input type="checkbox" onclick="clickAll()" id="checkAll"></td>
                    	<td width="117px"><fmt:message key="se_ac_ap_ls_deappno" /></td>
                        <td width="202px"><fmt:message key="se_ac_ap_ls_decostcen" /></td>
                        <td width="130px"><fmt:message key="se_ac_ap_ls_declaimby" /></td>
                        <td width="100px"><fmt:message key="se_ac_ap_ls_decliamdate" /></td>
                        <td width="215px"><fmt:message key="se_ac_ap_ls_detlt" /></td>
                        <td width="100px"><fmt:message key="se_ac_ap_ls_detotal" /></td>
                        <td width="120px"><fmt:message key="se_ac_ap_ls_status" /></td>
                        <td width="130px"><fmt:message key="se_ac_ap_ls_nextStep" /></td>
                        <td width="90px">&nbsp;</td>
                    </tr>
                    <c:forEach var="beanList" items="${ seAcApLsBean.beanList }">
                    <tr>
                    	<td>
                    		<c:if test="${beanList.canBeApprove == '1'}">
                    			<input type="checkbox" name="detailCheck" value="${beanList.expenseNo}" onclick="clickDetail()">
                    		</c:if>
                    		<c:if test="${beanList.canBeApprove != '1'}">
                    			<input type="checkbox" value="" disabled="disabled">
                    		</c:if>
                    	</td>
                    	<td>${beanList.expenseNo}</td>
                        <td style="text-align:left">${beanList.costCenter}/${beanList.costCenterName}</td>
                        <td style="text-align:left">${beanList.claimBy}</td>
                        <td>${beanList.claimDate}</td>
                        <td>${beanList.travelLocationType}</td>
                        <td style="text-align:right">${beanList.total}</td>
                        <td>${beanList.statusName}</td>
                        <td>
                        <c:if test="${beanList.status == '2'}">
                        ${beanList.nextAprrovePerson}
                        </c:if>
                        </td>
                        <td style="text-align:center;padding-left:0px">
                        	<wa:showAtag id="SE_AC_AP_LS_Details" onclick="toApprove('${beanList.expenseNo}')" value="Details"></wa:showAtag>
                        	<input type="hidden" value="${beanList.status }" name="hiddenStatus"/>
                        </td>
                        
                    </tr>
                    </c:forEach>
                    <c:if test='${ listCount == "0" && searched == "1" }'>
            			<tr>
            				<td colspan="10">
            					<span class="noDateMsg"><fmt:message key="E0009"/></span>
            				</td>
            			</tr>
            		</c:if>
                </tbody>
			</table>
            <c:if test='${ listCount != "0" }'>
			<div class="list_pages_box">
					<div style="float:left">
						<wa:showAtag id="SE_AC_AP_LS_Approve" onclick="approved(); return false" innerImg="/images/approve_btn.png"></wa:showAtag>
						<wa:showAtag id="SE_AC_AP_LS_Reject" onclick="reject(); return false" innerImg="/images/reject_btn.png"></wa:showAtag>
						<c:if test="${ currentRole == 'R005' }">
							<wa:showAtag id="SE_AC_AP_LS_Finish" onclick="finish(); return false" innerImg="/images/finish.png"></wa:showAtag>
							<wa:showAtag id="SE_AC_AP_LS_UndoFinish" onclick="undoFinish(); return false" innerImg="/images/undoFinish.png"></wa:showAtag>
						</c:if>
					</div>
					<div class="pages_min" <c:if test="${ currentRole == 'R005' }">style="width:300px"</c:if>>
					<div class="pages_go_box"><a href="#" onclick="return Action('GO');" class="pages_go" ></a></div>
					<div class="pages_input_boots_box"><input class="pages_input_boots" type="text" id="pageNum" value="${ curPage }"/></div>
					<div class="pages_font">${ curPage }/${ totalPage }</div>
					</div>
				
					<input type="hidden" id="totalPage" value="${ totalPage }"/>
					<input type="hidden" id="curPage" value="${ curPage }"/>
					
					<div class="prev_btn"><a href="#" onclick="return Action('NEXT');"><img src="<c:url value='/images/next_btn.png'/>"/></a></div>
					<div class="prev_btn"><a href="#" onclick="return Action('PREV');"><img src="<c:url value='/images/prev_btn.png'/>"/></a></div>

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
</form:form>   
</body>
</html>
